package de.cau.inf.se.sopro.network;

import java.util.List;

import javax.inject.Inject;

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

public class RequestHandler {

    private final WebService isbnService;

    @Inject
    public RequestHandler(WebService isbnService) {
        this.isbnService = isbnService;
    }

    // requests from the ApiViewModel to handle further

    public Boolean addUser(String username, String password) {
        return null;
    }


    public Boolean validateLogin(String username, String password) {
        return null;
    }


    public ProjectBaseInfoItem[] getProjects() {
        return null;
    }


    public PhaseInfoItem getPhaseInfo(Long projectID) {
        return null;
    }



    public ProjectInfoItem getProjectInfo(Long projectID) {
        return null;
    }



    public GroupBaseInfoItem[] getGroups(Long projectID) {
        return null;
    }



    public HeadingBaseInfoItem[] getHeadings(Long groupID) {
        return null;
    }



    public SubprojectBaseInfoItem[] getSubprojects(Long headingID) {
        return null;
    }



    public SubprojectInfoItem getSubprojectInfo(Long subprojectID, String username) {
        return null;
    }



    public CommentInfoItem[] getComments(Long subprojectID, String username) {
        return null;
    }


    public CommentInfoItem[] getSubcomments(Long commentID, String username) {
        return null;
    }


    public Boolean voteSubproject(Long subprojectID, String username) {
        return null;
    }


    public Boolean createComment(Long subprojectID, Long commentID, String content, String username) {
        return null;
    }


    public Boolean voteComment(Long commentID, Boolean upvote, String username) {
        return null;
    }
}