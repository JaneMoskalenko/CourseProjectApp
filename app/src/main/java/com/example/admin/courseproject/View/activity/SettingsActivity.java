package com.example.admin.courseproject.View.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.admin.courseproject.MyPreference;
import com.example.admin.courseproject.R;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String PREF_KEY_SAVE_PHOTOS_SETUP= "save_photos_setup";
    private static final String PREF_KEY_VOICE_FEMALE_SETUP= "voice_setup_female";
    private static final String PREF_KEY_LANGUAGE_SETUP= "language_setup";
    private static final String TAG = SettingsActivity.class.getSimpleName();

    final MyPreference preferences = MyPreference.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        CheckBoxPreference allowToSavePhotoPreference = (CheckBoxPreference) findPreference(PREF_KEY_SAVE_PHOTOS_SETUP);
        allowToSavePhotoPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newCheck) {
                preferences.setAllowSavePhotos((boolean) newCheck);
                Log.i(TAG,  " Settings were changed: allow_save_photos " + preferences.getAllowSavePhotos());
                return true;
            }
        });

        SwitchPreference isFemaleVoicePreference = (SwitchPreference) findPreference(PREF_KEY_VOICE_FEMALE_SETUP);
        isFemaleVoicePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newState) {
                preferences.setIsFemaleVoice((boolean) newState);
                Log.i(TAG,  " Settings were changed: female_voice " + preferences.getIsFemaleVoice());
                return true;
            }
        });

        ListPreference languagePreferences = (ListPreference) findPreference(PREF_KEY_LANGUAGE_SETUP);
        languagePreferences.setOnPreferenceChangeListener(
                new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object object) {
                        preferences.setLanguageType(object.toString());
                        Log.i(TAG,  " Settings were changed: language_setup " + preferences.getLanguageType());
                        return true;
                    }
                });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
