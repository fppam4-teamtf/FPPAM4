package com.teamtf.portalamikom.model;

import android.graphics.Bitmap;

public class NewsListHome {
    private int id;
    private String title;
    private String date;
    private Bitmap image;

    public NewsListHome(int id, String title, String date, Bitmap image) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getImage() {
        return image;
    }
}
