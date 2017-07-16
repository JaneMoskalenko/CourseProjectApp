package com.example.admin.courseproject.Presenter.services.translate;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranslateRetrofitClient {

    private static final String URL = "https://translate.yandex.net/";
    private Gson gson;
    private Retrofit mRetrofit;
    private static TranslateRetrofitClient sInstance;

    private TranslateRetrofitClient(){
        gson = new GsonBuilder().create();
        mRetrofit = retrofitBuild(URL, gson);
    }

    public static void init() { sInstance = new TranslateRetrofitClient(); }

    public static TranslateRetrofitClient getsInstance(){
        if (sInstance == null){
            throw new IllegalStateException("TranslateRetrofitClient are not instantiated yet. " +
                    "Did you call instantiate() before getInstance()?");
        }
        else return sInstance;
    }

    private static Retrofit retrofitBuild(String URL, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();
    }

}
