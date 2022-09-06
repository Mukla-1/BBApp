package de.cau.inf.se.sopro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

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

    // Mutable LiveData objects that will be observed
    private final MutableLiveData<Boolean> _userAddedSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _loginValid = new MutableLiveData<>();
    private final MutableLiveData<List<ProjectBaseInfoItem>> _projects = new MutableLiveData<>();
    private final MutableLiveData<PhaseInfoItem> _phaseInfo = new MutableLiveData<>();
    private final MutableLiveData<ProjectInfoItem> _projectInfo = new MutableLiveData<>();
    private final MutableLiveData<List<GroupBaseInfoItem>> _groups = new MutableLiveData<>();
    private final MutableLiveData<List<HeadingBaseInfoItem>> _headings = new MutableLiveData<>();
    private final MutableLiveData<List<SubprojectBaseInfoItem>> _subprojects = new MutableLiveData<>();
    private final MutableLiveData<SubprojectInfoItem> _subprojectInfo = new MutableLiveData<>();
    private final MutableLiveData<List<CommentInfoItem>> _comments = new MutableLiveData<>();
    private final MutableLiveData<List<CommentInfoItem>> _subcomments = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _subprojectVoteSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _commentCreationSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _commentVoteSuccess = new MutableLiveData<>();



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
    public void addUser(String username, String password) {
        requestHandler.updateLiveData(_userAddedSuccess, requestHandler.addUser(username, password));
    }

    /**
     * Validates a login attempt from the LoginActivity.
     *
     * @param username the given username
     * @param password the given password
     * @return {@code true} if the login data was correct,
     * {@code false} if username and password didn't match.
     */
    public void validateLogin(String username, String password) {
        requestHandler.updateLiveData(_loginValid, requestHandler.validateLogin(username, password));
    }

    /**
     * Returns basic information about all existing projects to display in a list.
     *
     * @return an array of {@link ProjectBaseInfoItem}s, one for each existing project
     */
    public void getProjects() {
        requestHandler.updateLiveData(_projects, requestHandler.getProjects());
    }

    /**
     * Returns the information needed to build a phase overview for a project.
     *
     * @param projectID the ID of the project in the database
     * @return a {@link PhaseInfoItem} for the project
     */
    public void getPhaseInfo(Long projectID) {
        requestHandler.updateLiveData(_phaseInfo, requestHandler.getPhaseInfo(projectID));
    }

    /**
     * Returns information for the overview page of a project.
     *
     * @param projectID the ID of the project in the database
     * @return a {@link ProjectInfoItem} for the project
     */
    public void getProjectInfo(Long projectID) {
        requestHandler.updateLiveData(_projectInfo, requestHandler.getProjectInfo(projectID));
    }

    /**
     * Returns all groups of a project.
     *
     * @param projectID the ID of the project in the database
     * @return an array of {@link GroupBaseInfoItem}s, one for each group of the project
     */
    public void getGroups(Long projectID) {
        requestHandler.updateLiveData(_groups, requestHandler.getGroups(projectID));
    }

    /**
     * Returns all headings of a group.
     *
     * @param groupID the ID of the group in the database
     * @return an array of {@link HeadingBaseInfoItem}s, one for each heading of the group
     */
    public void getHeadings(Long groupID) {
        requestHandler.updateLiveData(_headings, requestHandler.getHeadings(groupID));
    }

    /**
     * Returns all subprojects of a heading.
     *
     * @param headingID the ID of the heading in the database
     * @return an array of {@link SubprojectBaseInfoItem}s, one for each subproject of the heading
     */
    public void getSubprojects(Long headingID) {
        requestHandler.updateLiveData(_subprojects, requestHandler.getSubprojects(headingID));
    }

    /**
     * Returns information for the overview page of a subproject.
     * In the forwarded method call, the username is also propagated to potentially allow for
     * "already voted" checks in the future.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return a {@link SubprojectInfoItem} for the subproject
     */
    public void getSubprojectInfo(Long subprojectID) {
        requestHandler.updateLiveData(_subprojectInfo, requestHandler.getSubprojectInfo(subprojectID, username));
    }

    /**
     * Returns information about the comments on a subproject.
     * In the forwarded method call, the username is also propagated to check if the user has voted
     * for any of these comments before.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return an array of {@link CommentInfoItem}s, one for each comment on the subproject
     */
    public void getComments(Long subprojectID) {
        requestHandler.updateLiveData(_comments, requestHandler.getComments(subprojectID, username));
    }

    /**
     * Returns information about the subcomments on a comment.
     * In the forwarded method call, the username is also propagated to check if the user has voted
     * for any of these subcomments before.
     *
     * @param commentID the ID of the main comment in the database
     * @return an array of {@link CommentInfoItem}s, one for each subcomment on the comment
     */
    public void getSubcomments(Long commentID) {
        requestHandler.updateLiveData(_subcomments, requestHandler.getSubcomments(commentID, username));
    }

    /**
     * Casts a vote on a subproject from the user's account.
     * In the forwarded method call, the username is propagated to save the name of the voting
     * user in the database to not allow multi-votes.
     *
     * @param subprojectID the ID of the subproject in the database
     * @return {@code true} if the voting process worked, {@code false} if there was an error
     */
    public void voteSubproject(Long subprojectID) {
        requestHandler.updateLiveData(_subprojectVoteSuccess, requestHandler.voteSubproject(subprojectID, username));
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
    public void createComment(Long subprojectID, Long commentID, String content) {
        requestHandler.updateLiveData(_commentCreationSuccess, requestHandler.createComment(subprojectID, commentID, content, username));
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
    public void voteComment(Long commentID, Boolean upvote) {
        requestHandler.updateLiveData(_commentVoteSuccess, requestHandler.voteComment(commentID, upvote, username));
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
        // also save persistent
        saveURLPersistent(newURL);
    }

    public MutableLiveData<Boolean> get_userAddedSuccess() {
        return _userAddedSuccess;
    }

    public MutableLiveData<Boolean> get_loginValid() {
        return _loginValid;
    }

    public MutableLiveData<List<ProjectBaseInfoItem>> get_projects() {
        return _projects;
    }

    public MutableLiveData<PhaseInfoItem> get_phaseInfo() {
        return _phaseInfo;
    }

    public MutableLiveData<ProjectInfoItem> get_projectInfo() {
        return _projectInfo;
    }

    public MutableLiveData<List<GroupBaseInfoItem>> get_groups() {
        return _groups;
    }

    public MutableLiveData<List<HeadingBaseInfoItem>> get_headings() {
        return _headings;
    }

    public MutableLiveData<List<SubprojectBaseInfoItem>> get_subprojects() {
        return _subprojects;
    }

    public MutableLiveData<SubprojectInfoItem> get_subprojectInfo() {
        return _subprojectInfo;
    }

    public MutableLiveData<List<CommentInfoItem>> get_comments() {
        return _comments;
    }

    public MutableLiveData<List<CommentInfoItem>> get_subcomments() {
        return _subcomments;
    }

    public MutableLiveData<Boolean> get_subprojectVoteSuccess() {
        return _subprojectVoteSuccess;
    }

    public MutableLiveData<Boolean> get_commentCreationSuccess() {
        return _commentCreationSuccess;
    }

    public MutableLiveData<Boolean> get_commentVoteSuccess() {
        return _commentVoteSuccess;
    }
}