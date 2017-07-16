package com.example.admin.courseproject.Presenter.services.recognition;

public interface RecognizeCallback {

    void onGetRequest(String result);

    void onGetRequest(RecognizeResponse recognizeResponse);

    void onGetRequestException(String resultMessage);
}
