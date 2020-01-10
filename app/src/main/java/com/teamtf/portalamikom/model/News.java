package com.teamtf.portalamikom.model;

import android.graphics.Bitmap;

public class News {

    private int id;
    private String category, title, date, content, publisher;
    private Bitmap imgResource;
//    private final int imgResource;

    public News(int id, String category, String title, String date, String content, Bitmap imgResource, String publisher) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.date = date;
        this.content = content;
        this.imgResource = imgResource;
        this.publisher = publisher;

    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public Bitmap getImgResource() {
        return imgResource;
    }

    public String getPublisher() {
        return publisher;
    }
}
