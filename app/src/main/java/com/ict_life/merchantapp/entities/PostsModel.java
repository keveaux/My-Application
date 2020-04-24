package com.ict_life.merchantapp.entities;

public class PostsModel {

    String likes,time;
    int image;

    public PostsModel(String likes, String time, int image) {
        this.likes = likes;
        this.time = time;
        this.image = image;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImages_list() {
        return image;
    }

    public void setImages_list(int images_list) {
        this.image = images_list;
    }
}
