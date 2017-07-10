package com.example.admin.courseproject.Presenter.services.recognition;

/**
 * Created by admin on 10.07.2017.
 */

public interface RecognizeCallback {

    void onGetRequest(String result);

    void onGetRequest(RecognizeResponse recognizeResponse);

    void onGetRequestException(String resultMessage);
}
