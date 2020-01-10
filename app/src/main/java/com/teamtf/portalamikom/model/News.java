package com.teamtf.portalamikom.model;

public class News {

    private int id;
    private String category, title, date, content, publisher, imgResource;
//    private final int imgResource;

    public News(int id, String category, String title, String date, String content, String imgResource, String publisher) {
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

    public String getImgResource() {
        return imgResource;
    }

    public String getPublisher() {
        return publisher;
    }
}
