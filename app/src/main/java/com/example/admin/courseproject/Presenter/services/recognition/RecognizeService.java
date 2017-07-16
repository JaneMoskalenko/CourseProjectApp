package com.example.admin.courseproject.Presenter.services.recognition;

import android.graphics.Bitmap;
import android.util.Log;

public class RecognizeService {
    private static final String TAG = RecognizeService.class.getSimpleName();
    private RecognizeCallback mRecognizeCallback;
    private Bitmap mBitmap;
    private RecognizeRequest mRecognizeRequest;

    public RecognizeService(Bitmap bitmap, RecognizeCallback mRecognizeCallback) {
        mBitmap = bitmap;
        this.mRecognizeCallback = mRecognizeCallback;
    }

    public void doDescribe() {

        mRecognizeRequest = new RecognizeRequest(mBitmap, mRecognizeCallback);

        /*TODO Compress image before request*/
       /* mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                mImageUri, mContentResolver );*/
         //       mImageUri, getContentResolver());

      /*  Log.d("DescribeActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                + "x" + mBitmap.getHeight());*/

        try {
            Log.i(TAG, "Recognition execute started" );
            mRecognizeRequest.execute();
            Log.i(TAG, "Recognition execute finished" );

        } catch (Exception e)
        {
          /*TODO catch the exception*/
            Log.i(TAG, "Exception was catches while recognition executing" );
        }

    }

}
