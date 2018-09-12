package com.vijay.countrynews.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by vijayganeshkumar on 01/09/18.
 */

public class ApiResponse {

    @SerializedName("title")
    private  String pageTitle;

    @SerializedName("rows")
    private List<NewsItems> newsFeeds;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<NewsItems> getNewsFeeds() {
        return newsFeeds;
    }

    public void setNewsFeeds(List<NewsItems> newsFeeds) {
        this.newsFeeds = newsFeeds;
    }
}
