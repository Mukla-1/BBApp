package de.cau.inf.se.sopro.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.lang.reflect.Field;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import de.cau.inf.se.sopro.model.ProjectBaseInfoItem;
import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


@Module
@InstallIn(SingletonComponent.class)
public class WebConfiguration {


    private final String BASE_URL_LOCALHOST = "http://localhost:8080";
    private final String BASE_URL_LOOPBACK_FOR_EMULATOR ="http://134.245.1.240:1502";

    private static Retrofit retrofit;
    public static final MutableLiveData<Boolean> _validUrl = new MutableLiveData<>();


    @Singleton
    @Provides
    public WebService provideWebService() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                        Credentials.basic("admin", "12345678"));

                Request newRequest = builder.build();
                return chain.proceed(newRequest);
            }
        }).retryOnConnectionFailure(true).build();

        this.retrofit = new Retrofit.Builder().baseUrl(BASE_URL_LOOPBACK_FOR_EMULATOR).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create()).build();

        return retrofit.create(WebService.class);
    }

    /**
     * Pretty much stackoverflowed. Seems like we are using reflection(which is
     * a kind of DI) to change the base url in a rough manner.
     *
     * @param Url : the new url we provide in the settings fragment.
     */
    public static void changeUrl(String Url){
        //Try catch to do nothing if the url is bad/wrong/has a false format.
        try{
            /*
            Make a new okHttpClient to check if an Url is valid. Doesnt return if its correct,
            but doesnt matter because if it fails it would only mean it is not working and let the old
            url stand.
            If it is a success, the url changes.
             */
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                            Credentials.basic("admin@consul.dev", "12345678"));

                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                }
            }).retryOnConnectionFailure(true).build();
            Retrofit testRetrofit = new Retrofit.Builder().baseUrl(Url).client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create()).build();
            WebService webbi = testRetrofit.create(WebService.class);
            Call testCall = webbi.getProjects();

            //Boilerplate call code.
            testCall.enqueue(new Callback<ProjectBaseInfoItem>() {
                @Override
                public void onResponse(Call<ProjectBaseInfoItem> call, Response<ProjectBaseInfoItem> response) {
                    //True means that the url is valid and can be used to make calls.
                    if (response.isSuccessful()) {
                        //Rough way to change the base url of the retrofit-client.
                        Field field = null;
                        try {
                            field = Retrofit.class.getDeclaredField("baseUrl");
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        field.setAccessible(true);
                        okhttp3.HttpUrl newHttpUrl = HttpUrl.parse(Url);
                        try {
                            field.set(retrofit, newHttpUrl);
                            _validUrl.setValue(true);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                            _validUrl.setValue(false);
                        }
                    }
                    else{
                        _validUrl.setValue(false);
                    }
                }
                @Override
                public void onFailure(Call<ProjectBaseInfoItem> call, Throwable t) {
                    Log.e("BBSKiel", "Could not reach backend", t);
                    _validUrl.setValue(false);
                }
            });
        }
        catch (IllegalArgumentException e){
            _validUrl.setValue(false);
            return;
        }
    }


}
