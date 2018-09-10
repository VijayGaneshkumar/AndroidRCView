package com.vijay.countrynews.NetworkService;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.vijay.countrynews.Model.ApiResponse;
import com.vijay.countrynews.Presenter.NewsItemPresenterInterface;
import com.vijay.countrynews.Utils.AppConstants;
import com.vijay.countrynews.Utils.AppUtils;
import com.vijay.countrynews.Views.MainContract;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by vijayganeshkumar on 10/09/18.
 */

public class NewsItemServiceInteractor implements MainContract.NewsItemInteractor {

    private static NewsItemPresenterInterface mPresenterItemInterface;
    Context mContext;
    static final String TAG = NewsItemServiceInteractor.class.getSimpleName();
    public NewsItemServiceInteractor (NewsItemPresenterInterface iObj){
        mPresenterItemInterface = iObj;
    }

    public void getNewsItemFromUrl () {

        IRetrofitApiService retroApiService = RetrofitApiClient.getRetrofitInstance().create(IRetrofitApiService.class);

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
