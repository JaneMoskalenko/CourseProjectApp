package com.example.admin.courseproject.Presenter.services.speechkit;


import android.content.Context;

import com.example.admin.courseproject.View.activity.TranslateActivity;

import ru.yandex.speechkit.Error;
import ru.yandex.speechkit.SpeechKit;
import ru.yandex.speechkit.Synthesis;
import ru.yandex.speechkit.Vocalizer;
import ru.yandex.speechkit.VocalizerListener;

public class SpeechHelper implements VocalizerListener {
    private static final String TAG = SpeechHelper.class.getSimpleName();
    public static final String API_KEY_SPEECHKIT = "7a8971c9-17f0-449f-9b91-65a858499237";
    private Vocalizer vocalizer;
    private Context context;
    private VocalizerListener mVocalizerListener;

    public SpeechHelper(Context context ) {
        this.context = context;
        SpeechKit.getInstance().configure(context, SpeechHelper.API_KEY_SPEECHKIT);
    }

    public void spechExecute(String textToSpeak){
        resetVocalizer();

        // To create a new vocalizer, specify the language, the text to be vocalized, the auto play parameter
        // and the voice.
        vocalizer = Vocalizer.createVocalizer(
                Vocalizer.Language.RUSSIAN,
                textToSpeak,
                true,
                Vocalizer.Voice.JANE);

        vocalizer.setListener(mVocalizerListener);
        vocalizer.start();
    }

    private void resetVocalizer() {
        if (vocalizer != null) {
            vocalizer.cancel();
            vocalizer = null;
        }
    }

    @Override
    public void onSynthesisBegin(Vocalizer vocalizer) {

    }

    @Override
    public void onSynthesisDone(Vocalizer vocalizer, Synthesis synthesis) {

    }

    @Override
    public void onPlayingBegin(Vocalizer vocalizer) {

    }

    @Override
    public void onPlayingDone(Vocalizer vocalizer) {

    }

    @Override
    public void onVocalizerError(Vocalizer vocalizer, Error error) {
        resetVocalizer();
    }
}
