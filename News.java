package com.example.daniel.newsapp;

public class News {

    private String mTitle, mAuthor, mSection, mUrl, mDate;

    public News(String title, String author, String section, String url, String date) {
        mTitle = title;
        mAuthor = author;
        mSection = section;
        mUrl = url;
        mDate = date;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDate() {
        return mDate;
    }

}
