package com.teamtf.portalamikom.model;

public class NewsModel {

    private String title, date, content, publisher;
    private final int imgResource;

    public NewsModel(String title, String date, String content, String publisher, int imgResource) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.publisher = publisher;
        this.imgResource = imgResource;
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

    public int getImgResource() {
        return imgResource;
    }

    public String getPublisher() {
        return publisher;
    }
}
