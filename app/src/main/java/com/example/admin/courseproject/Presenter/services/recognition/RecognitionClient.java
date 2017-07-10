package com.example.admin.courseproject.Presenter.services.recognition;

import android.content.Context;

import com.example.admin.courseproject.MyPreference;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;

/**
 * Created by admin on 09.07.2017.
 */

public class RecognitionClient {
    private static final String MS_CV_API_SUBSCRIPTION_KEY= "5d037ac0906f49808a4da3d2f1a19d90";
    private static final String MS_CV_REQUEST_API_URL= "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0";

    private static RecognitionClient sInstance;
    private final VisionServiceClient client;

    private RecognitionClient() {
        client = new VisionServiceRestClient(MS_CV_API_SUBSCRIPTION_KEY,
                MS_CV_REQUEST_API_URL);
    }

    public static void instantiate() {
        sInstance = new RecognitionClient();
    }

    public static RecognitionClient getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("RecognitionClient are not instantiated yet. " +
                    "Did you call instantiate() before getInstance()?");
        }
        return sInstance;
    }

}
