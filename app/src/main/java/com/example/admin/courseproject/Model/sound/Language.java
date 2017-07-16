package com.example.admin.courseproject.Model.sound;

import com.example.admin.courseproject.MyPreference;

public class Language {

    public String getLanguageForSpeech(){

        return MyPreference.getInstance().getLanguageType();

    }
}
