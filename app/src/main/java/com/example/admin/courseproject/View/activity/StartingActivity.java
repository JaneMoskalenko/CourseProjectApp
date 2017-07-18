package com.example.admin.courseproject.View.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.courseproject.Model.image.ImageBitmap;
import com.example.admin.courseproject.Model.image.ImageManager;
import com.example.admin.courseproject.R;

import java.io.ByteArrayOutputStream;

public class StartingActivity extends AppCompatActivity {

    private static final String TAG = StartingActivity.class.getSimpleName();
    private static final String IMAGE_EXTRA = "BitmapExtra";
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

        mImageManager = new ImageManager(this);
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

        btn_start_selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_SELECT_IMAGE_IN_ALBUM);
            }
        });

        btn_start_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageBitmap != null ) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] bytes = stream.toByteArray();

                    Intent intent = new Intent(StartingActivity.this, PreparingActivity.class);
                    intent.putExtra(IMAGE_EXTRA,bytes);
                    startActivity(intent);
                }
                else
                    Toast.makeText(StartingActivity.this, getString(R.string.noImageSelected), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(StartingActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case RESULT_CANCELED:
                Toast.makeText(this, getString(R.string.imageCaptureCancelled), Toast.LENGTH_SHORT).show();
                return;
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, getString(R.string.imageCaptureCancelled), Toast.LENGTH_SHORT).show();
                    return;
                }
                imageBitmap = BitmapFactory.decodeFile(photoFileUri.getPath());
                break;
            case REQUEST_SELECT_IMAGE_IN_ALBUM:
                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, getString(R.string.noImageSelected), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (resultCode == RESULT_OK) {
                    imageBitmap = mImageManager.getSelectedBitmap(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
