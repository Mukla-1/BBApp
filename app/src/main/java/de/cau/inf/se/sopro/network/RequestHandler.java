package de.cau.inf.se.sopro.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import de.cau.inf.se.sopro.model.CommentInfoItem;
import de.cau.inf.se.sopro.model.GroupBaseInfoItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.model.PhaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectInfoItem;
import de.cau.inf.se.sopro.model.SubprojectBaseInfoItem;
import de.cau.inf.se.sopro.model.SubprojectInfoItem;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class which provides the Call methods needed to get JSON objects from the Web App.
 * Especially updateLiveData is there to update a LiveData object in the ViewModel based
 * on a Call.
 */
public class RequestHandler {

    private final WebService webService;

    @Inject
    public RequestHandler(WebService webService) {
        this.webService = webService;
    }

    /**
     * Method, which updates LiveData by processing a HTTP-Request. It is defined by
     * a call object using this request handlers other methods using the API WebService.
     * @param liveData : The LiveData object from a ViewModel which shall be updated
     * @param call : Call to make
     * @param <T> : Object we want process with our UI later
     */
    public <T> void updateLiveData(MutableLiveData<T> liveData, Call call){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    //Update the given LiveData object
                    liveData.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e("SoPro", "Could not reach backend", t);
            }
        });
    }

    /**
     * Note that all objects are returned as a Call, thus the need for LiveData updating.
     */

    /**
     * Adds a user to the external Consul database, if the username is not yet in use.
     *
     * @param username the desired username
     * @param password the password of the user
     * @return {@code true} if the user was added, {@code false} if the username was already used.
     */
    public Call<Boolean> addUser(String username, String password) {
        return webService.addUser(username, password);
    }

    /**
     * Validates a login attempt from the LoginActivity.
     *
     * @param username the given username
     * @param password the given password
     * @return {@code true} if the login data was correct,
     * {@code false} if username and password didn't match.
     */
    public Call<Boolean> validateLogin(String username, String password) {
        return webService.validateLogin(username, password);
    }

    /**
     * Returns basic information about all existing projects to display in a list.
     *
     * @return an array of {@link ProjectBaseInfoItem}s, one for each existing project
     */
    public Call<List<ProjectBaseInfoItem>> getProjects() {
        return webService.getProjects();
    }

    /**
     * Returns the information needed to build a phase overview for a project.
     *
     * @param projectID the ID of the project in the database
     * @return a {@link PhaseInfoItem} for the project
     */
    public Call<PhaseInfoItem> getPhaseInfo(Long projectID) {
        return webService.getPhaseInfo(projectID);
    }


    /**
     * Returns information for the overview page of a project.
     *
     * @param projectID the ID of the project in the database
     * @return a {@link ProjectInfoItem} for the project
     */
    public Call<ProjectInfoItem> getProjectInfo(Long projectID) {
        return webService.getProjectInfo(projectID);
    }


    /**
     * Returns all groups of a project.
     *
     * @param projectID the ID of the project in the database
     * @return an array of {@link GroupBaseInfoItem}s, one for each group of the project
     */
    public Call<List<GroupBaseInfoItem>> getGroups(Long projectID) {
        return webService.getGroups(projectID);
    }


    /**
     * Returns all headings of a group.
     *
     * @param groupID the ID of the group in the database
     * @return an array of {@link HeadingBaseInfoItem}s, one for each heading of the group
     */
    public Call<List<HeadingBaseInfoItem>> getHeadings(Long groupID) {
        return webService.getHeadings(groupID);
    }


    /**
     * Returns all subprojects of a heading.
     *
     * @param headingID the ID of the heading in the database
     * @return an array of {@link SubprojectBaseInfoItem}s, one for each subproject of the heading
     */
    public Call<List<SubprojectBaseInfoItem>> getSubprojects(Long headingID) {
        return webService.getSubprojects(headingID);
    }


    /**
     * Returns information for the overview page of a subproject.
     * In the forwarded method call, the username is also propagated to potentially allow for
     * "already voted" checks in the future.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return a {@link SubprojectInfoItem} for the subproject
     */
    public Call<SubprojectInfoItem> getSubprojectInfo(Long subprojectID, String username) {
        return webService.getSubprojectInfo(subprojectID, username);
    }


    /**
     * Returns information about the comments on a subproject.
     * In the forwarded method call, the username is also propagated to check if the user has voted
     * for any of these comments before.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return an array of {@link CommentInfoItem}s, one for each comment on the subproject
     */
    public Call<List<CommentInfoItem>> getComments(Long subprojectID, String username) {
        return webService.getComments(subprojectID, username);
    }

    /**
     * Returns information about the subcomments on a comment.
     * In the forwarded method call, the username is also propagated to check if the user has voted
     * for any of these subcomments before.
     *
     * @param commentID the ID of the main comment in the database
     * @return an array of {@link CommentInfoItem}s, one for each subcomment on the comment
     */
    public Call<List<CommentInfoItem>> getSubcomments(Long commentID, String username) {
        return webService.getSubcomments(commentID, username);
    }

    //CAUTION NOT IMPLEMENTED RIGHT NOW IN WEB APP/API!!!!!!
    /**
     * Casts a vote on a subproject from the user's account.
     * In the forwarded method call, the username is propagated to save the name of the voting
     * user in the database to not allow multi-votes.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return {@code true} if the voting process worked, {@code false} if there was an error
     */
    public Call<Boolean> voteSubproject(Long subprojectID, String username) {
        return null;
    }

    /**
     * Creates a comment on a subproject OR a comment by the user.
     * In the forwarded method call, the username is propagated to associate the commenting user
     * with the comment.
     *
     * @param subprojectID the ID of the commented subproject in the database
     * @param commentID the ID of the commented comment or {@code null}
     *                  if the subproject is commented on directly
     * @param content the content of the comment
     * @return {@code true} if the commenting process worked, {@code false} if there was an error
     */
    public Call<Boolean> createComment(Long subprojectID, Long commentID, String content, String username) {
        return webService.createComment(subprojectID, commentID, content, username);
    }

    /**
     * Casts a vote on a comment from the user's account.
     * In the forwarded method call, the username is propagated to save the name of the voting
     * user in the database to not allow multi-votes.
     *
     * @param commentID the ID of the comment in the database
     * @param upvote {@code true} if the vote is an upvote, {@code false} if it is a downvote
     * @return {@code true} if the voting process worked, {@code false} if there was an error
     */
    public Call<Boolean> voteComment(Long commentID, Boolean upvote, String username) {
        return webService.voteComment(commentID, upvote, username);
    }
}