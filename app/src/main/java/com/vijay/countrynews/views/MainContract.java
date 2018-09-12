package com.vijay.countrynews.views;

import com.vijay.countrynews.model.NewsItems;

import java.util.List;

/**
 * Created by vijayganeshkumar on 10/09/18.
 */

public interface MainContract {


    void showProgress();

    void hideProgress();

    void setDataToRecyclerView(List<NewsItems> newsItemList, String newsPageTitle);

    void displayErrorNotification(String errorMessage);


    interface  NewsItemInteractor {
        void getNewsItemFromUrl();

    }


}
