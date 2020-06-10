package com.project.android_swipes;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET(value = "everything" )
    Call<ResponseBody> getNews(
            @Query("q") String keyWord,
            @Query("apiKey") String apiKey
    );
}
