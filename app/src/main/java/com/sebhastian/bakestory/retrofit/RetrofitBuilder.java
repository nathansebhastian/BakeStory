package com.sebhastian.bakestory.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yonathan Sebhastian on 8/9/2017.
 */

public final class RetrofitBuilder {

    static String base_url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    static NetworkService networkService;

    public static NetworkService Retrieve(){
        Gson gson = new GsonBuilder().create();
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        networkService = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .callFactory(httpClientBuilder.build())
                .build().create(NetworkService.class);
        return networkService;
    }
}
