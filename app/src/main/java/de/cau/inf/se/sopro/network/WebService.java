package de.cau.inf.se.sopro.network;

import java.util.List;

import de.cau.inf.se.sopro.model.CommentInfoItem;
import de.cau.inf.se.sopro.model.GroupBaseInfoItem;
import de.cau.inf.se.sopro.model.HeadingBaseInfoItem;
import de.cau.inf.se.sopro.model.PhaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;
import de.cau.inf.se.sopro.model.ProjectInfoItem;
import de.cau.inf.se.sopro.model.SubprojectBaseInfoItem;
import de.cau.inf.se.sopro.model.SubprojectInfoItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebService {

    /*
    HTTP-Requests for GroupBaseInfoItems.
     */
    @GET("/app/getGroupBaseInfoItem/{projectID}")
    public Call<List<GroupBaseInfoItem>> getGroups(@Path("projectID") Long projectID);

    /*
    HTTP-Requests for HeadingBaseInfoItem.
     */
    @GET("/app/getHeadingBaseInfoItem/{groupID}")
    public Call<List<HeadingBaseInfoItem>> getHeadings(@Path("groupID") Long groupID);


    /*
    HTTP-Requests for user data(validation and creation).
     */
    @POST("/app/addUser/{username}/{password}")
    public Call<Boolean> addUser(@Path("username") String username, @Path("password") String password);


    @GET("/app/validateLogin/{username}/{password}")
    public Call<Boolean> validateLogin(@Path("username") String username, @Path("password") String password);

    @GET("/app/validateAdminLogin/{username}/{password}")
    public Call<Boolean> validateAdminLogin(@Path("username") String username, @Path("password") String password);


    /*
    HTTP-Requests for Project items, thus phase info, baseinfo and rich infos.
     */
    @GET("/app/getProjectBaseInfoItem")
    public Call<List<ProjectBaseInfoItem>> getProjects();

    @GET("/app/getProjectPhaseInfoItem/{projectID}")
    public Call<PhaseInfoItem> getPhaseInfo(@Path("projectID") Long projectID);

    @GET("/app/getProjectInfoItem/{projectID}")
    public Call<ProjectInfoItem> getProjectInfo(@Path("projectID") Long projectID);


    /*
    HTTP-Requests for Subproject(Base)InfoItems.
     */
    @GET("/app/getSubprojectBaseInfoItem/{headingID}")
    public Call<List<SubprojectBaseInfoItem>> getSubprojects(@Path("headingID")Long headingID);

    @GET("/app/getSubprojectInfoItem/{subprojectID}/{userID}")
    public Call<SubprojectInfoItem> getSubprojectInfo(@Path("subprojectID") Long subprojectID,@Path("userID") String username);


    /*
    HTTP-Requests for the Comment items.
     */
    @GET("/app/getComments/{subprojectID}/{userID}")
    public Call<List<CommentInfoItem>> getComments(@Path("subprojectID") Long subprojectID,@Path("userID") String username);

    @GET("/app/getSubComments/{commentID}/{userID}")
    public Call<List<CommentInfoItem>> getSubcomments(@Path("commentID") Long commentID, @Path("userID")String username);

    @POST("/app/createComment/{subprojectID}/{commentID}/{userID}/{content}")
    public Call<Boolean> createComment(@Path("subprojectID")Long subprojectID,
                                       @Path("commentID")Long commentID, @Path("content")String content, @Path("userID")String username);

    @POST("/app/voteComment/{commentID}/{userID}/{upVote}")
    public Call<Boolean> voteComment(@Path("commentID")Long commentID, @Path("upVote")Boolean upvote, @Path("userID")String username);

}

