package com.example.admin.courseproject.View.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.courseproject.Model.image.ImageBitmap;
import com.example.admin.courseproject.Model.image.ImageManager;
import com.example.admin.courseproject.R;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class StartingActivity extends Activity {

    private static final String TAG = StartingActivity.class.getSimpleName();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 2;

    Button btn_start_makePhoto, btn_start_selectPhoto, btn_start_next;

    ImageBitmap mImageBitmap;
    Bitmap imageBitmap = null;

    Uri photoFileUri;
    ImageManager mImageManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);

        btn_start_makePhoto = (Button) findViewById(R.id.btn_start_makePhoto);
        btn_start_selectPhoto = (Button) findViewById(R.id.btn_start_selectPhoto);
        btn_start_next = (Button) findViewById(R.id.btn_start_next);

        mImageManager = new ImageManager();
        btn_start_makePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoFileUri = mImageManager.generateTimeStampPhotoFileUri();

                if (photoFileUri != null){
                    Intent takePictureIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFileUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btn_start_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmap != null ) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] bytes = stream.toByteArray();


                  /*  if (imageBitmap != null) {
                        mImageBitmap = new ImageBitmap(imageBitmap);
                    }*/

                    Intent intent = new Intent(StartingActivity.this, PreparingActivity.class);
                    intent.putExtra("BMP",bytes);
                    //  intent.putExtra(ImageBitmap.class.getCanonicalName(), mImageBitmap);
                    Log.d(TAG, "Start Preparing Activity");
                    startActivity(intent);
                }
                else
                    Toast.makeText(StartingActivity.this, "ImageBitmap is empty", Toast.LENGTH_LONG).show();
            }
        });

        Log.d(TAG, "Main Scenario Finished");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case RESULT_CANCELED:
                Toast.makeText(this, "User cancelled", Toast.LENGTH_SHORT).show();
                return;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "User cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }

                imageBitmap = BitmapFactory.decodeFile(photoFileUri.getPath());

                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);




    }
}
