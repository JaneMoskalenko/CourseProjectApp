package com.example.admin.courseproject.Presenter.services.recognition;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.EditText;

import com.example.admin.courseproject.Model.base.DBHelper;

public class RecognizeService {
    private RecognizeCallback mRecognizeCallback;

    public RecognizeService(Uri imageUri, Bitmap bitmap, RecognizeCallback mRecognizeCallback) {
        mImageUri = imageUri;
        mBitmap = bitmap;
        this.mRecognizeCallback = mRecognizeCallback;
        mRecognizeRequest = new RecognizeRequest(mBitmap, mRecognizeCallback);
    }

    // The URI of the image selected to detect.
    private Uri mImageUri;

    // The image selected to detect.
    private Bitmap mBitmap;

    private static final String TAG = RecognizeService.class.getSimpleName();
    private RecognizeRequest mRecognizeRequest;

    // NOT IN USE
    public String getResult(){

        DBHelper dbHelper = DBHelper.getInstance();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor =  database.query("ImageDescription", null, null, null, null, null, null);
        String Desc="";
        int i=0;
        while (cursor.moveToNext()){
            Desc = cursor.getString(cursor.getColumnIndex("description"));
            i++;
            Log.i(TAG, "FROM DB: "+ Desc);
        }
        Log.i(TAG, "COUNT FROM DB: "+ i);
        return Desc;
    }

    public void doDescribe() {
        /*TODO Compress image before request*/
       /* mBitmap = ImageHelper.loadSizeLimitedBitmapFromUri(
                mImageUri, mContentResolver );*/
         //       mImageUri, getContentResolver());

      /*  Log.d("DescribeActivity", "Image: " + mImageUri + " resized to " + mBitmap.getWidth()
                + "x" + mBitmap.getHeight());*/

        try {
            Log.i(TAG, "Execute main start" );
            mRecognizeRequest.execute();
            Log.i(TAG, "Execute main finished" );

        } catch (Exception e)
        {
          /*TODO catch the exception*/
        }

    }

}
