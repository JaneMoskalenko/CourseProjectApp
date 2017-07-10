package com.example.admin.courseproject.Model.image;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 09.07.2017.
 */

public class ImageManager {

    private File outputDir;
    private static final String FOLDER_NAME_FOR_PICTURES = "sPikcher";
    private static final String DATE_FORMAT_FOR_NAMING_PICTURES = "yyyyMMDD_HHmmss";
    private static final String TAG = ImageManager.class.getSimpleName();

    private File getPhotoDirectory(){
        outputDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        Log.i(TAG, " externalStorageState " + externalStorageState);

        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)){
            File pictureDir = getDefaultPicturesDirectory();
            outputDir = new File (pictureDir, FOLDER_NAME_FOR_PICTURES);
            checkOutputDirectoryExistance();
        }
        return outputDir;
    }

    private void checkOutputDirectoryExistance() {
        if (!outputDir.exists()){
            if (!outputDir.mkdirs()) {
                /*TODO MAKE Logging or exeption*/
               /* Toast.makeText(this, "Failed creating directory: "
                        + outputDir.getAbsolutePath(), Toast.LENGTH_LONG).show();*/
                outputDir = null;
            }
        }
    }

    private File getDefaultPicturesDirectory() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    public Uri generateTimeStampPhotoFileUri(){
        Uri photoFileUri = null;
        File outputDir = getPhotoDirectory();

        if (outputDir != null) {
            String timeStamp = new SimpleDateFormat(DATE_FORMAT_FOR_NAMING_PICTURES).format(new Date());
            String photoFileName = "IMG_"+ timeStamp + ".jpg";

            File photoFile = new File(outputDir, photoFileName);
            photoFileUri = Uri.fromFile(photoFile);
        }
        return photoFileUri;
    }

    /*TODO Parcelable */
}
