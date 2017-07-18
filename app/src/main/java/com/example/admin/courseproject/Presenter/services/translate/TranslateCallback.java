package com.example.admin.courseproject.Presenter.services.translate;


public interface TranslateCallback {

    void onGetTranslateResponse(String result);

    void onGetTranslateResponseException(String resultMessage);
}
