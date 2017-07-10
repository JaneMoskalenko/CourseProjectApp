package com.example.admin.courseproject.Presenter.services.translate;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.admin.courseproject.R;
import com.example.admin.courseproject.View.activity.SettingsActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String URL = "https://translate.yandex.net/";
    private static final String KEY = "trnsl.1.1.20170628T144837Z.7cb1b99abc10c478.fa5af19e10cd0242b3b7e607d5244e88f4a0666d";
    private static final String PHRASE = "Go to work";
    private static final String LANGUAGE_EN_RU = "en-ru";
    Button btn_translate, btn_take_photo, btn_select_photo, btn_openPref;
    private Gson gson = new GsonBuilder().create();
    private Retrofit mRetrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(URL)
            .build();

    private TranslateLink inf = mRetrofit.create(TranslateLink.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_translate = (Button) findViewById(R.id.btn_translate);
        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        btn_select_photo = (Button) findViewById(R.id.btn_select_photo);
     //   btn_openPref = (Button) findViewById(R.id.btn_openPref);

        btn_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                intent.resolveActivity(getPackageManager());
                startActivity(intent);
            }
        });

        btn_openPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
             //   startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            }
        });


        btn_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> mapJson = new HashMap<>();
                mapJson.put("key", KEY);
                mapJson.put("text", PHRASE);
                mapJson.put("lang", LANGUAGE_EN_RU);
                Call<Object> call = inf.translate(mapJson);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Object myItem=response.body();
                        if (myItem != null) {
                            System.out.println("Response recieved");
                            System.out.println(myItem.toString());
                        }
                      //  gson.fromJson()
                       /* Map<String, String> map = gson.fromJson(response.body().toString(), Map.class);
                        for (Map.Entry e: map.entrySet()){
                            System.out.println(e.getKey()+ " " + e.getValue());
                        }*/
                    }
                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        //Handle failure
                    }
                });
            }
        });
    }
}
