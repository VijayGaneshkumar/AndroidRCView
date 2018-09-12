package com.vijay.countrynews.presenter;

import com.vijay.countrynews.model.ApiResponse;
import com.vijay.countrynews.model.NewsItems;
import com.vijay.countrynews.networkservice.NewsItemServiceInteractor;
import com.vijay.countrynews.views.MainContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijayganeshkumar on 10/09/18.
 */

public class NewsItemPresenter implements NewsItemPresenterInterface {

    private MainContract mainView;
    private NewsItemServiceInteractor newsItemServiceInteractor;

    public NewsItemPresenter(MainContract  mainView) {
        this.mainView = mainView;
        newsItemServiceInteractor = new NewsItemServiceInteractor(this);
    }

    public void requestNewsItems(){
        newsItemServiceInteractor.getNewsItemFromUrl();
        mainView.hideProgress();
    }

    public void onRefreshButtonClick() {

        if(mainView != null){
            mainView.showProgress();
        }
        newsItemServiceInteractor.getNewsItemFromUrl();
    }

    @Override
    public void onGetNewsItemFailure(String errorMessage) {
        if(mainView !=null) {
            mainView.displayErrorNotification(errorMessage);
            mainView.hideProgress();
        }

    }

    @Override
    public void onGetNewsItemSuccess(ApiResponse newsItems) {
        if(mainView != null){
            List<NewsItems> newsItemList = new ArrayList();
            String newsPageTitle = newsItems.getPageTitle();
            for (NewsItems item : newsItems.getNewsFeeds()) {
                if (item.getRowTitle() != null || item.getRowDescription() != null || item.getRowImageUrl() != null) {
                    newsItemList.add(item);
                }
            }
            newsItems.setNewsFeeds(newsItemList);
            mainView.setDataToRecyclerView(newsItemList,newsPageTitle);
            mainView.hideProgress();

        }
    }

}
