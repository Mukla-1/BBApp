package de.cau.inf.se.sopro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import de.cau.inf.se.sopro.model.CommentInfoItem;
import de.cau.inf.se.sopro.model.GroupBaseInfoItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.model.PhaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectInfoItem;
import de.cau.inf.se.sopro.model.SubprojectBaseInfoItem;
import de.cau.inf.se.sopro.model.SubprojectInfoItem;
import de.cau.inf.se.sopro.network.RequestHandler;

/**
 * This ViewModel handles all requests from the different activities and fragments.
 * It mainly forwards requests to the RequestHandler.
 */
@HiltViewModel
public class ApiViewModel extends ViewModel {

    private String username;
    private String currentURL;

    private SharedPreferences sharedPreferences;
    private RequestHandler requestHandler;

    /**
     * Creates a new ViewModel to handle requests.
     *
     * @param activity the activity that creates the ViewModel, used for SharedPreferences
     */
    @Inject
    public ApiViewModel(Activity activity, RequestHandler requestHandler) {
        // set context to activities context
        sharedPreferences = activity.getSharedPreferences("persistentPrefs", Context.MODE_PRIVATE);
        // set the current URL to the one that is persistently saved or the default URL
        currentURL = sharedPreferences.getString("baseURL", String.valueOf(R.string.defaultURL));
        this.requestHandler = requestHandler;
    }

    /**
     * Saves the given URL in the files of the Android app.
     *
     * @param url the URL to safe
     * @return {@code true} if the saving process worked, {@code false} else
     */
    private void saveURLPersistent(String url) {
        // create an editor to write data into sharedPreferences for persistent saving
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("baseURL", url);
        editor.apply();
    }

    /**
     * Adds a user to the external Consul database, if the username is not yet in use.
     *
     * @param username the desired username
     * @param password the password of the user
     * @return {@code true} if the user was added, {@code false} if the username was already used.
     */
    public Boolean addUser(String username, String password) {
        return requestHandler.addUser(username, password);
    }

    /**
     * Validates a login attempt from the LoginActivity.
     *
     * @param username the given username
     * @param password the given password
     * @return {@code true} if the login data was correct,
     * {@code false} if username and password didn't match.
     */
    public Boolean validateLogin(String username, String password) {
        return requestHandler.validateLogin(username, password);
    }

    /**
     * Returns basic information about all existing projects to display in a list.
     *
     * @return an array of {@link ProjectBaseInfoItem}s, one for each existing project
     */
    public ProjectBaseInfoItem[] getProjects() {
        return requestHandler.getProjects();
    }

    /**
     * Returns the information needed to build a phase overview for a project.
     *
     * @param projectID the ID of the project in the database
     * @return a {@link PhaseInfoItem} for the project
     */
    public PhaseInfoItem getPhaseInfo(Long projectID) {
        return requestHandler.getPhaseInfo(projectID);
    }

    /**
     * Returns information for the overview page of a project.
     *
     * @param projectID the ID of the project in the database
     * @return a {@link ProjectInfoItem} for the project
     */
    public ProjectInfoItem getProjectInfo(Long projectID) {
        return requestHandler.getProjectInfo(projectID);
    }

    /**
     * Returns all groups of a project.
     *
     * @param projectID the ID of the project in the database
     * @return an array of {@link GroupBaseInfoItem}s, one for each group of the project
     */
    public GroupBaseInfoItem[] getGroups(Long projectID) {
        return requestHandler.getGroups(projectID);
    }

    /**
     * Returns all headings of a group.
     *
     * @param groupID the ID of the group in the database
     * @return an array of {@link HeadingBaseInfoItem}s, one for each heading of the group
     */
    public HeadingBaseInfoItem[] getHeadings(Long groupID) {
        return requestHandler.getHeadings(groupID);
    }

    /**
     * Returns all subprojects of a heading.
     *
     * @param headingID the ID of the heading in the database
     * @return an array of {@link SubprojectBaseInfoItem}s, one for each subproject of the heading
     */
    public SubprojectBaseInfoItem[] getSubprojects(Long headingID) {
        return requestHandler.getSubprojects(headingID);
    }

    /**
     * Returns information for the overview page of a subproject.
     * In the forwarded method call, the username is also propagated to potentially allow for
     * "already voted" checks in the future.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return a {@link SubprojectInfoItem} for the subproject
     */
    public SubprojectInfoItem getSubprojectInfo(Long subprojectID) {
        return requestHandler.getSubprojectInfo(subprojectID, username);
    }

    /**
     * Returns information about the comments on a subproject.
     * In the forwarded method call, the username is also propagated to check if the user has voted
     * for any of these comments before.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return an array of {@link CommentInfoItem}s, one for each comment on the subproject
     */
    public CommentInfoItem[] getComments(Long subprojectID) {
        return requestHandler.getComments(subprojectID, username);
    }

    /**
     * Returns information about the subcomments on a comment.
     * In the forwarded method call, the username is also propagated to check if the user has voted
     * for any of these subcomments before.
     *
     * @param commentID the ID of the main comment in the database
     * @return an array of {@link CommentInfoItem}s, one for each subcomment on the comment
     */
    public CommentInfoItem[] getSubcomments(Long commentID) {
        return requestHandler.getSubcomments(commentID, username);
    }

    /**
     * Casts a vote on a subproject from the user's account.
     * In the forwarded method call, the username is propagated to save the name of the voting
     * user in the database to not allow multi-votes.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return {@code true} if the voting process worked, {@code false} if there was an error
     */
    public Boolean voteSubproject(Long subprojectID) {
        return requestHandler.voteSubproject(subprojectID, username);
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
    public Boolean createComment(Long subprojectID, Long commentID, String content) {
        return requestHandler.createComment(subprojectID, commentID, content, username);
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
    public Boolean voteComment(Long commentID, Boolean upvote) {
        return requestHandler.voteComment(commentID, upvote, username);
    }

    // Getter and Setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentURL() {
        return currentURL;
    }

    public void setCurrentURL(String newURL) {
        currentURL = newURL;
        saveURLPersistent(newURL);
    }
}
