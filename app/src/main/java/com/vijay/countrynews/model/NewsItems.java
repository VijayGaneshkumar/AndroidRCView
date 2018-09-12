package com.vijay.countrynews.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vijayganeshkumar on 01/09/18.
 */

public class NewsItems {
    @SerializedName("title")
    public String rowTitle;

    @SerializedName("description")
    public String rowDescription;

    @SerializedName("imageHref")
    public String rowImageUrl;

    public String getRowTitle() {
        return rowTitle;
    }

    public void setRowTitle(String rowTitle) {
        this.rowTitle = rowTitle;
    }

    public String getRowDescription() {
        return rowDescription;
    }

    public void setRowDescription(String rowDescription) {
        this.rowDescription = rowDescription;
    }

    public String getRowImageUrl() {
        return rowImageUrl;
    }

    public void setRowImageUrl(String rowImageUrl) {
        this.rowImageUrl = rowImageUrl;
    }
}
