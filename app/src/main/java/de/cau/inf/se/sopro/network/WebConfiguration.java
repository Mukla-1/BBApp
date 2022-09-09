package de.cau.inf.se.sopro.network;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;


@Module
@InstallIn(SingletonComponent.class)
public class WebConfiguration {

    private final String BASE_URL_LOCALHOST = "http://localhost:8082";
    private final String BASE_URL_LOOPBACK_FOR_EMULATOR = "http://10.0.2.2:8082";
    //private final String BASE_URL_LOOPBACK_FOR_EMULATOR = "http://134.245.1.240:1502";

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
        }).build();

        return new Retrofit.Builder().baseUrl(BASE_URL_LOOPBACK_FOR_EMULATOR).client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create()).build()
                .create(WebService.class);
    }



}
