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


public class RequestHandler {

    private final WebService webService;

    @Inject
    public RequestHandler(WebService webService) {
        this.webService = webService;
    }


    public <T> void updateLiveData(MutableLiveData<T> liveData, Call call){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.e("SoPro", "Could not reach backend", t);
            }
        });
    }





    // requests from the ApiViewModel to handle further

    public Call<Boolean> addUser(String username, String password) {
        return null;
    }


    public Call<Boolean> validateLogin(String username, String password) {
        return null;
    }


    public Call<List<ProjectBaseInfoItem>> getProjects() {
        return null;
    }


    public Call<PhaseInfoItem> getPhaseInfo(Long projectID) {
        return null;
    }



    public Call<ProjectInfoItem> getProjectInfo(Long projectID) {
        return null;
    }



    public Call<List<GroupBaseInfoItem>> getGroups(Long projectID) {
        return null;
    }



    public Call<List<HeadingBaseInfoItem>> getHeadings(Long groupID) {
        return null;
    }



    public Call<List<SubprojectBaseInfoItem>> getSubprojects(Long headingID) {
        return null;
    }



    public Call<SubprojectInfoItem> getSubprojectInfo(Long subprojectID, String username) {
        return null;
    }



    public Call<List<CommentInfoItem>> getComments(Long subprojectID, String username) {
        return null;
    }


    public Call<List<CommentInfoItem>> getSubcomments(Long commentID, String username) {
        return null;
    }


    public Call<Boolean> voteSubproject(Long subprojectID, String username) {
        return null;
    }


    public Call<Boolean> createComment(Long subprojectID, Long commentID, String content, String username) {
        return null;
    }


    public Call<Boolean> voteComment(Long commentID, Boolean upvote, String username) {
        return null;
    }
}