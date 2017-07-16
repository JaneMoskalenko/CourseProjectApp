package com.example.admin.courseproject.Presenter.services.translate;


public interface TranslateCallback {

    void onGetRequest(String result);

    void onGetRequestException(String resultMessage);
}
