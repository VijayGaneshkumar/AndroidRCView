package com.vijay.countrynews.networkservice;

import android.content.Context;
import android.util.Log;

import com.vijay.countrynews.model.ApiResponse;
import com.vijay.countrynews.presenter.NewsItemPresenterInterface;
import com.vijay.countrynews.views.MainContract;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by vijayganeshkumar on 10/09/18.
 */

public class NewsItemServiceInteractor implements MainContract.NewsItemInteractor {

    NewsItemPresenterInterface mPresenterItemInterface;
    IRetrofitApiService retroApiService = RetrofitApiClient.getRetrofitInstance().create(IRetrofitApiService.class);
    static final String TAG = NewsItemServiceInteractor.class.getSimpleName();
    public NewsItemServiceInteractor (NewsItemPresenterInterface iObj){
        mPresenterItemInterface = iObj;
    }

    public void getNewsItemFromUrl () {

        final Call<ApiResponse> newsFeedCall = retroApiService.doGetNewsFeed();

        //Get the json response vua retorfit asynchronously
        newsFeedCall.enqueue(new Callback<ApiResponse>() {

            //Handle success response from server
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "jSON Response :" + response.body().getPageTitle().toString());
                    Log.d(TAG, "jSOn Data :" + response.body().getNewsFeeds().size());
                    mPresenterItemInterface.onGetNewsItemSuccess(response.body());
                }
            }

            //Handle failure response from server
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                mPresenterItemInterface.onGetNewsItemFailure(t.getMessage());
                Log.d(TAG, "OnFailure" + call.toString());
                newsFeedCall.cancel();
            }
        });

    }
}
