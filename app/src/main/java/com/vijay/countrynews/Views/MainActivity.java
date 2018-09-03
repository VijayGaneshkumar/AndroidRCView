package com.vijay.countrynews.Views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.vijay.countrynews.Model.ApiResponse;
import com.vijay.countrynews.Model.NewsItems;
import com.vijay.countrynews.Network.IRetrofitApiService;
import com.vijay.countrynews.Network.RetrofitApiClient;
import com.vijay.countrynews.R;
import com.vijay.countrynews.Utils.AppUtils;
import com.vijay.countrynews.Adapter.NewsItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vijayganeshkumar on 02/09/18.
 */


public class MainActivity extends AppCompatActivity  {

    public ProgressDialog mProgressDialog;

    IRetrofitApiService retroApiService;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.newsItemRecyclerView)
    RecyclerView newsItemRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeView();
        requestNewsItems(MainActivity.this);

    }

    private void setupProgressDialog()
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(0);
    }

    private void initializeView() {

        setupProgressDialog();
        mSwipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
        floatingActionButton.setOnClickListener(fabRefreshListener);
    }

    public void showProgress() {
        if(!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void hideProgress() {
        if(mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void displayErrorNotification(String errorMessage) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(errorMessage)
                    .setNeutralButton(R.string.buttonOk, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alertDialog.show();
    }


    private void requestNewsItems(Context mContext) {

        if(AppUtils.hasDataConnectivity(mContext)) {
            retroApiService = RetrofitApiClient.getRetrofitInstance().create(IRetrofitApiService.class);

            final Call<ApiResponse> newsFeedCall = retroApiService.doGetNewsFeed();
            newsFeedCall.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if(response.isSuccessful()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        hideProgress();
                        Log.d(TAG, "jSON Response :" + response.body().getPageTitle().toString());
                        Log.d(TAG, "jSOn Data :" + response.body().getNewsFeeds().size());
                        displayNewsItems(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Log.d(TAG, "OnFailure" + call.toString());
                    newsFeedCall.cancel();
                }
            });
        }
        else {
            // No Network Connectivity show an alert to user
        }

    }

    public void displayNewsItems(ApiResponse newsItems) {
        List<NewsItems> newsItemList = new ArrayList();;
        if(newsItems ==null)
        {
            //display some text;
        }
        String newsPageTitle = newsItems.getPageTitle();

        if( newsPageTitle != null)
            getSupportActionBar().setTitle(newsPageTitle);
        if(newsItems.getNewsFeeds() != null) {
            for (NewsItems item : newsItems.getNewsFeeds()) {
                if (item.getRowTitle() != null || item.getRowDescription() != null || item.getRowImageUrl() != null) {
                    newsItemList.add(item);
                }
            }
            newsItems.setNewsFeeds(newsItemList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            newsItemRecyclerView.setLayoutManager(layoutManager);
            setDataToRecyclerView(newsItemList);
        }
    }

    private void setDataToRecyclerView(List<NewsItems> newsItemList) {

        NewsItemAdapter adapter = new NewsItemAdapter(MainActivity.this,newsItemList);
        newsItemRecyclerView.setAdapter(adapter);

    }

    SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        @Override
        public void onRefresh() {
            //Swipe refresh callback
            Log.d(TAG,"SwipeRefreshListener clicked");
            mSwipeRefreshLayout.setRefreshing(true);
            requestNewsItems(MainActivity.this);
        }
    };

    FloatingActionButton.OnClickListener fabRefreshListener = new FloatingActionButton.OnClickListener() {

        @Override
        public void onClick(View v) {

            //floating button refresh callback
            Log.d(TAG,"fabRefreshListener clicked");
            showProgress();
            requestNewsItems(MainActivity.this);
        }

    };

}
