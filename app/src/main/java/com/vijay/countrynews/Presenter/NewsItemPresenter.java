package com.vijay.countrynews.Presenter;

import com.vijay.countrynews.Model.ApiResponse;
import com.vijay.countrynews.Model.NewsItems;
import com.vijay.countrynews.NetworkService.NewsItemServiceInteractor;
import com.vijay.countrynews.Views.MainContract;

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
            mainView.hideProgress();
            List<NewsItems> newsItemList = new ArrayList();
            mainView.displayNewsItems(newsItems);

        }
    }

}
