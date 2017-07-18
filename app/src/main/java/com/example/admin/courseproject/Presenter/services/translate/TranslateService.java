package com.example.admin.courseproject.Presenter.services.translate;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TranslateService {

    private static final String URL = "https://translate.yandex.net/";
    private static final String KEY = "trnsl.1.1.20170628T144837Z.7cb1b99abc10c478.fa5af19e10cd0242b3b7e607d5244e88f4a0666d";
    private static final String LANGUAGE_EN_RU = "en-ru";
    private static final String TAG = TranslateService.class.getSimpleName();

    private Gson gson;
    private Retrofit mRetrofit;
    private YandexTranslateService mYandexTranslateService;
    private Map<String, String> mapJson;
    private TranslateData data;
    private TranslateCallback mTranslateCallback;

    public TranslateService(TranslateCallback translateCallback) {
        this.mTranslateCallback = translateCallback;
    }

    public void execute( String translatePhrase){
        gson = new GsonBuilder().create();
        mRetrofit = retrofitBuild(URL, gson);
        mYandexTranslateService = mRetrofit.create(YandexTranslateService.class);

        mapJson = new HashMap<>();
        mapJson.put("key", KEY);
        mapJson.put("text", translatePhrase);
        mapJson.put("lang", LANGUAGE_EN_RU);

        mYandexTranslateService.translate(mapJson).enqueue(new Callback<TranslateData>() {
            @Override
            public void onResponse(Call<TranslateData> call, Response<TranslateData> response) {
               if ( response.isSuccessful() ){
                   data = response.body();
                   mTranslateCallback.onGetTranslateResponse(data.getText());
                   Log.d(TAG, data.getText());
                }
            }

            @Override
            public void onFailure(Call<TranslateData> call, Throwable t) {
                //Handle failure
                Log.d(TAG, t.getMessage() + "Error while translation");

                if(call.isCanceled()) {
                    Log.e(TAG, "request was aborted");
                    mTranslateCallback.onGetTranslateResponseException("request was aborted");
                }else {
                    Log.e(TAG, "Unable to submit post to API.");
                    mTranslateCallback.onGetTranslateResponseException("Unable to submit post to API.");
                }
            }
        });
        if (data != null )
            Log.d(TAG, data.getText());
        else
            Log.d(TAG, "DATA IS NULL");

       // return data;

    }

    private Retrofit retrofitBuild(String URL, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();
    }


}
