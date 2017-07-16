package com.example.admin.courseproject.Presenter.services.translate;

import java.util.ArrayList;

public class TranslateData {
    private int code;
    private String lang;
    private ArrayList<String> text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        StringBuilder resultText = new StringBuilder();
        for (String s: text) {
            resultText.append(s);
        }
        return resultText.toString();
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }
}