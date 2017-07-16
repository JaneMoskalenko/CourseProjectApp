package com.example.admin.courseproject.View.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.admin.courseproject.Model.image.ImageBitmap;
import com.example.admin.courseproject.R;


public class PreparingActivity extends Activity {

    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparing);
        img = (ImageView) findViewById(R.id.img);

        Intent intent = getIntent();
        byte[] bytes = intent.getByteArrayExtra("BMP");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.elephant);

        img.setImageBitmap(bmp);

       /*
        ImageBitmap imageBitmap = getIntent().getParcelableExtra(ImageBitmap.class.getCanonicalName());
        if (imageBitmap != null) {
            img.setImageBitmap(imageBitmap.getImageBitmap());

        }*/

    }
}
