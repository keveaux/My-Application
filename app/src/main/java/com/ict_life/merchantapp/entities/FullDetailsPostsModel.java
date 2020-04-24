package com.ict_life.merchantapp.entities;

import java.util.ArrayList;

public class FullDetailsPostsModel {

    String caption,date;
    ArrayList<Integer> post_images;
    int number_of_likes,number_of_comments;

    public FullDetailsPostsModel (String caption, String date, ArrayList<Integer> post_images, int number_of_likes, int number_of_comments) {
        this.caption = caption;
        this.date = date;
        this.post_images = post_images;
        this.number_of_likes = number_of_likes;
        this.number_of_comments = number_of_comments;
    }

    public String getCaption() {
        return caption;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Integer> getPost_images() {
        return post_images;
    }

    public int getNumber_of_likes() {
        return number_of_likes;
    }

    public int getNumber_of_comments() {
        return number_of_comments;
    }
}
