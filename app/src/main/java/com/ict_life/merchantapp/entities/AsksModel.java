package com.ict_life.merchantapp.entities;

public class AsksModel {
    String name_of_asker,ask_text,time;

    public AsksModel(String name_of_asker, String ask_text, String time) {
        this.name_of_asker = name_of_asker;
        this.ask_text = ask_text;
        this.time = time;
    }

    public String getName_of_asker() {
        return name_of_asker;
    }

    public String getAsk_text() {
        return ask_text;
    }

    public String getTime() {
        return time;
    }
}
