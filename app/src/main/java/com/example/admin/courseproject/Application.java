package com.example.admin.courseproject;


import com.example.admin.courseproject.Model.base.DBHelper;
import com.example.admin.courseproject.Presenter.services.recognition.RecognitionClient;
import com.example.admin.courseproject.Presenter.services.translate.TranslateRetrofitClient;

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper.init(getApplicationContext());

        MyPreference.instantiate(this);

        RecognitionClient.instantiate();

        TranslateRetrofitClient.init();

    }
}
