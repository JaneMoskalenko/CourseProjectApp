package com.example.admin.courseproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreference {
    private static final String PREF_KEY_ALLOW_TO_SAVE_PHOTOS = "allow_to_save_photos";
    private static final String PREF_KEY_VOICE_IS_FEMALE = "is_female_voice";
    private static final String PREF_KEY_LANGUAGE_TYPE = "language_type";

    private static MyPreference sInstance;

    public static void instantiate(final Context context) {
        sInstance = new MyPreference(context);
    }

    public static MyPreference getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("Preferences are not instantiated yet. " +
                    "Did you call instantiate() before getInstance()?");
        }
        return sInstance;
    }

    private final SharedPreferences mSharedPreferences;

    private MyPreference(final Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setAllowSavePhotos(final boolean isAllowSavePhotos) {
        setBoolean(PREF_KEY_ALLOW_TO_SAVE_PHOTOS, isAllowSavePhotos);
    }

    public boolean getAllowSavePhotos() {
        return mSharedPreferences.getBoolean(PREF_KEY_ALLOW_TO_SAVE_PHOTOS, true);
    }

    public void setLanguageType(final String languageType) {
        setString(PREF_KEY_LANGUAGE_TYPE, languageType);
    }

    public String getLanguageType() {
        return mSharedPreferences.getString(PREF_KEY_LANGUAGE_TYPE, "RU");
    }

    public void setIsFemaleVoice(final boolean isFemaleVoice) {
        setBoolean(PREF_KEY_VOICE_IS_FEMALE, isFemaleVoice);
    }

    public boolean getIsFemaleVoice() {
        return mSharedPreferences.getBoolean(PREF_KEY_VOICE_IS_FEMALE, true);
    }

    private void setString(final String key, final String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    private void setBoolean(final String key, final boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
    }


}
