package com.example.admin.courseproject.Presenter.services.recognition;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.courseproject.Model.base.DBHelper;
import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceClient;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.contract.Caption;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RecognizeRequest extends AsyncTask<String, String, String> {
    private static final String TAG = RecognizeRequest.class.getSimpleName();
    private static final String MS_CV_API_SUBSCRIPTION_KEY = "5d037ac0906f49808a4da3d2f1a19d90";
    private static final String MS_CV_REQUEST_API_URL = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0";

    private Exception mException = null;
    private Bitmap mBitmap;
    private String resultMessage ="";
    private VisionServiceClient client;
    private RecognizeCallback mRecognizeCallback;
    private RecognizeResponse recognizeResponse;

    public RecognizeRequest(Bitmap bitmap, RecognizeCallback mRecognizeCallback) {
        this.mBitmap = bitmap;
        this.mRecognizeCallback = mRecognizeCallback;
    }

    private String process() throws VisionServiceException, IOException {
        /*TODO  refactor to singleton */
        if (client==null){
            client = new VisionServiceRestClient(MS_CV_API_SUBSCRIPTION_KEY,
                    MS_CV_REQUEST_API_URL);
        }
        Gson gson = new Gson();
        // Put the image into an input stream for detection.
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        /*TODO compress was 100%*/
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 50, output);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(output.toByteArray());
        AnalysisResult v = client.describe(inputStream, 1);

        String result = gson.toJson(v);
        Log.d(TAG, "Request result: " + result);
        return result;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        /*TODO update status or progress bar*/
    }

    @Override
    protected String doInBackground(String... args) {
        try {
            return process();
        } catch (Exception e) {
            this.mException = e;    // Store error
        }
        return null;
    }

    @Override
    protected void onPostExecute(String data) {
        super.onPostExecute(data);

        // Display based on error existence
        if (mException != null) {
            resultMessage = "Error: " + mException.getMessage();
            Log.d(TAG, "Error encountered. Exception is: " + mException.getMessage());
            this.mException = null;
            mRecognizeCallback.onGetRequestException(resultMessage);
        }
        else {
            Gson gson = new Gson();
            AnalysisResult result = gson.fromJson(data, AnalysisResult.class);
            resultMessage = parseResultDataFromJsonInDetail(data, result);
            Log.i(TAG, "recognize FULL" + resultMessage);
            recognizeResponse = parseResultDataFromJsonToResponce(data, result);
        }

        if (recognizeResponse != null) {
            mRecognizeCallback.onGetRequest(recognizeResponse);
        }
        else
            Log.d(TAG, "recognizeResponse is NULL");
    }

    private RecognizeResponse parseResultDataFromJsonToResponce(String data, AnalysisResult result) {
        String description="";
        Double confidence = 0D;
        for (Caption caption: result.description.captions) {
            description=caption.text;
            confidence = caption.confidence;
        }
        Log.d(TAG, "DESCRIPTION RESULT " + description + confidence);
        return new RecognizeResponse(description, confidence);
    }

    /*TODO refactor shortResultMethod + separate description & confidence*/
    private String parseResultDataFromJsonInDetail(String data, AnalysisResult result) {

        StringBuilder  resultDetail = new StringBuilder();

        resultDetail.append("Image format: ").append(result.metadata.format).append("\n");
        resultDetail.append("Image width: ").append(result.metadata.width).append(", height:")
                .append(result.metadata.height).append("\n");
        resultDetail.append("\n");

        for (Caption caption: result.description.captions) {
            resultDetail.append("Caption: ").append(caption.text).append(", confidence: ")
                    .append(caption.confidence).append("\n");
        }
        resultDetail.append("\n");

        for (String tag: result.description.tags) {
            resultDetail.append("Tag: ").append(tag).append("\n");
        }
        resultDetail.append("\n");

        resultDetail.append("\n--- Raw Data ---\n\n");
        resultDetail.append(data);

        return resultDetail.toString();

    }
}