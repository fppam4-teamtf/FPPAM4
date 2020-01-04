package com.teamtf.portalamikom.model;

public class NewsList {
    private int id;
    private String title;
    private String date;

    public NewsList(int id, String title, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
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
}
