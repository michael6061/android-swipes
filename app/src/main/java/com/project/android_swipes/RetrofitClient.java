package com.project.android_swipes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BASE_URL = "https://newsapi.org/v2/";
    private static RetrofitClient mClient;
    private static Retrofit mRetrofit;
    private RetrofitClient(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient getInstance(){
        if (mClient == null) {
            mClient = new RetrofitClient();
        }
        return mClient;
    }

    public NewsApi getApi(){
        return mRetrofit.create(NewsApi.class);
    }
}
