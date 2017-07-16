package com.example.admin.courseproject.Presenter.services.translate;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface YandexTranslateService {

    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<TranslateData> translate(@FieldMap Map<String, String> map);

}
