package com.vijay.countrynews.presenter;

import com.vijay.countrynews.model.ApiResponse;

/**
 * Created by vijayganeshkumar on 11/09/18.
 */

public interface NewsItemPresenterInterface {

    void onGetNewsItemSuccess(ApiResponse newsResponse);

    void onGetNewsItemFailure(String errorMessage);

    void onRefreshButtonClick();
}
