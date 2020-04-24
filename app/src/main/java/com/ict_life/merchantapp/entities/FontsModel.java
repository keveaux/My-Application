package com.ict_life.merchantapp.entities;

import android.graphics.Typeface;

public class FontsModel {
    String font_name;
    Typeface typeface;

    public FontsModel(String font_name, Typeface typeface) {
        this.font_name = font_name;
        this.typeface = typeface;
    }

    public String getFont_name() {
        return font_name;
    }

    public Typeface getTypeface() {
        return typeface;
    }
}
