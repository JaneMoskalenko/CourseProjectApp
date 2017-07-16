package com.example.admin.courseproject.View.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.courseproject.Presenter.services.speechkit.SpeechHelper;
import com.example.admin.courseproject.Presenter.services.translate.TranslateCallback;
import com.example.admin.courseproject.Presenter.services.translate.TranslateService;
import com.example.admin.courseproject.R;

import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.VocalizerListener;

public class TranslateActivity extends Activity implements TranslateCallback{

    private static final String TAG = TranslateService.class.getSimpleName();
    TextView tv_translate;
    Button btn_play;
    private TranslateService mTranslateService;
    private SpeechHelper vocalizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        mTranslateService = new TranslateService(this);
        btn_play = (Button) findViewById(R.id.btn_play);

        tv_translate = (TextView) findViewById(R.id.tv_translate);
        Intent intent = getIntent();
        String description = intent.getStringExtra("description");

        mTranslateService.execute(description);

        vocalizer = new SpeechHelper(TranslateActivity.this);

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tv_translate.getText().toString();
                vocalizer.spechExecute(text);
            }
        });
    }

    @Override
    public void onGetRequest(String result) {
        tv_translate.setText(result);
    }

    @Override
    public void onGetRequestException(String resultMessage) {
        Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();

    }

}
