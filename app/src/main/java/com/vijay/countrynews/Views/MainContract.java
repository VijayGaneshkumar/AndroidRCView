package com.vijay.countrynews.Views;

import com.vijay.countrynews.Model.ApiResponse;
import com.vijay.countrynews.Model.NewsItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijayganeshkumar on 10/09/18.
 */

public interface MainContract {


    void showProgress();

    void hideProgress();

    void displayNewsItems(ApiResponse newsItems);

    void displayErrorNotification(String errorMessage);


    interface  NewsItemInteractor {
        void getNewsItemFromUrl();
    }


}
