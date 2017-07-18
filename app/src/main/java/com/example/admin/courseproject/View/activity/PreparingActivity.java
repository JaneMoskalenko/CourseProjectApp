package com.example.admin.courseproject.View.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.courseproject.Presenter.services.recognition.RecognizeCallback;
import com.example.admin.courseproject.Presenter.services.recognition.RecognizeResponse;
import com.example.admin.courseproject.Presenter.services.recognition.RecognizeService;
import com.example.admin.courseproject.Presenter.services.speechkit.SpeechHelper;
import com.example.admin.courseproject.Presenter.services.translate.TranslateCallback;
import com.example.admin.courseproject.Presenter.services.translate.TranslateService;
import com.example.admin.courseproject.R;


public class PreparingActivity extends AppCompatActivity implements RecognizeCallback, TranslateCallback {
    private static final String TAG = PreparingActivity.class.getSimpleName();
    private static final String IMAGE_EXTRA = "BitmapExtra";

    ImageView img;
    TextView tv_result_description;
    Button btn_next;
    Bitmap imageBitmap;
    public ProgressDialog dialog;
    RecognizeService mRecognizeService;
    private TranslateService mTranslateService;
    private SpeechHelper vocalizer;
    StringBuilder mDialogProgressMessage = new StringBuilder();
    private String mResultDescription;
    private String mResultTrasnlatedDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparing);
        img = (ImageView) findViewById(R.id.img);
        tv_result_description = (TextView) findViewById(R.id.tv_result_description);
        btn_next = (Button) findViewById(R.id.btn_next);

        Intent intent = getIntent();
        byte[] bytes = intent.getByteArrayExtra(IMAGE_EXTRA);
        imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        img.setImageBitmap(imageBitmap);

        mTranslateService = new TranslateService(this);
        vocalizer = new SpeechHelper(this);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecognizeService = new RecognizeService(imageBitmap, PreparingActivity.this);
                mRecognizeService.doDescribe();

                dialog = new ProgressDialog(PreparingActivity.this);
                dialog.setMessage(getString(R.string.dialog_process_recognize));
                dialog.setIndeterminate(true);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setCancelable(true);
                dialog.show();
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
                Intent intent = new Intent(PreparingActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onGetRequest(String result) {
    }

    @Override
    public void onGetRequest(RecognizeResponse recognizeResponse) {
        tv_result_description.setVisibility(View.VISIBLE);
        tv_result_description.setText(recognizeResponse.getDescription());

        dialog.setMessage(getString(R.string.dialog_process_translate));

      //  Toast.makeText(this,recognizeResponse.getDescription(),Toast.LENGTH_LONG ).show();

        mResultDescription = recognizeResponse.getDescription();

        mTranslateService.execute(mResultDescription);

    }

    @Override
    public void onGetRequestException(String resultMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Произошла ошибка")
                .setMessage(resultMessage)
                .setPositiveButton("Повторить попытку", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User pressed OK
                        Toast.makeText(PreparingActivity.this, "MAKING NEXT TRY", Toast.LENGTH_LONG).show();
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

    @Override
    public void onGetTranslateResponse(String result) {
        mResultTrasnlatedDescription = result;
        vocalizer.spechExecute(mResultTrasnlatedDescription);
        dialog.dismiss();
    }

    @Override
    public void onGetTranslateResponseException(String resultMessage) {

    }
}
