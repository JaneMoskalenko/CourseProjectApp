package com.example.admin.courseproject.View.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.courseproject.Model.image.ImageManager;
import com.example.admin.courseproject.Presenter.services.recognition.RecognizeCallback;
import com.example.admin.courseproject.Presenter.services.recognition.RecognizeResponse;
import com.example.admin.courseproject.Presenter.services.recognition.RecognizeService;
import com.example.admin.courseproject.R;


public class StartActivity extends Activity implements RecognizeCallback{

    private static final String TAG = StartActivity.class.getSimpleName();
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SELECT_IMAGE_IN_ALBUM = 2;

    Button btn_makePhoto, btn_openPref;
    Button btn_selectPhoto;
    Button btn_go_to_playsound;
    TextView tv_confidence, tv_description;
    ImageView imv_resultPhoto;
    Uri photoFileUri;
    ImageManager mImageManager;
    RecognizeService mRecognizeService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btn_makePhoto = (Button) findViewById(R.id.btn_makePhoto);
        btn_selectPhoto = (Button) findViewById(R.id.btn_selectPhoto);
        btn_openPref = (Button) findViewById(R.id.btn_openPref);
       // btn_go_to_playsound = (Button) findViewById(R.id.btn_go_to_playsound);
        tv_description = (TextView) findViewById(R.id.tv_description);
        tv_confidence = (TextView) findViewById(R.id.tv_confidence);
        mImageManager = new ImageManager();

        btn_makePhoto.setOnClickListener(new View.OnClickListener() {
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

        btn_openPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        btn_selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_SELECT_IMAGE_IN_ALBUM);

            }
        });

        tv_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if (recognizeResponse != null){
                /*TODO check if image was selected and recognized*/
                //  }
                Intent intent = new Intent(StartActivity.this, TranslateActivity.class);
                intent.putExtra("description",tv_description.getText().toString());
                startActivity(intent);
            }
        });

        /*btn_go_to_playsound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, PlayingSoundActivity.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap imageBitmap = null;
        imv_resultPhoto = (ImageView) findViewById(R.id.imv_resultPhoto);

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

            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "User cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resultCode == RESULT_OK) {
                    photoFileUri = data.getData();
                    /*TODO wrap to ImageManager*/
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(photoFileUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    imageBitmap = BitmapFactory.decodeFile(imgDecodableString);

                }
                break;
            default:
                break;
        }

        if (imageBitmap != null) {
            imv_resultPhoto.setImageBitmap(imageBitmap);
            tv_description.setText("");
            tv_confidence.setText("");
        }

        mRecognizeService = new RecognizeService(imageBitmap, this);
        mRecognizeService.doDescribe();

       /* TODO why fails here?
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                Uri.parse("file://" + Environment.getExternalStorageDirectory())));*/

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("description", tv_description.getText().toString());
        outState.putString("confidence", tv_confidence.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tv_description.setText(savedInstanceState.getString("description"));
        tv_confidence.setText(savedInstanceState.getString("confidence"));
    }


    @Override
    public void onGetRequest(String result) {
    }

    @Override
    public void onGetRequest(RecognizeResponse recognizeResponse) {
        // got responce
        tv_description.setText(recognizeResponse.getDescription());
        tv_confidence.setText(recognizeResponse.getConfidence().toString());

    }

    @Override
    public void onGetRequestException(String resultMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle("Произошла ошибка")
                .setMessage(resultMessage)
                .setPositiveButton("Повторить попытку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User pressed OK
                        Toast.makeText(StartActivity.this, "MAKING NEXT TRY", Toast.LENGTH_LONG).show();
                        mRecognizeService.doDescribe();
                    }
                })
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
