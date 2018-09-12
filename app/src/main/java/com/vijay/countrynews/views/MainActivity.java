package com.vijay.countrynews.views;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


import com.vijay.countrynews.model.NewsItems;
import com.vijay.countrynews.presenter.NewsItemPresenter;
import com.vijay.countrynews.R;
import com.vijay.countrynews.utils.AppConstants;
import com.vijay.countrynews.utils.AppUtils;
import com.vijay.countrynews.views.itemadapter.NewsItemAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vijayganeshkumar on 02/09/18.
 */


public class MainActivity extends AppCompatActivity  implements MainContract {

    public ProgressDialog mProgressDialog;

    NewsItemPresenter newsItemPresenter;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.newsItemRecyclerView)
    RecyclerView newsItemRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

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
        if(AppUtils.hasDataConnectivity(this)) {
            createPresenter();
        }
        else {
            displayErrorNotification(AppConstants.NoInternet);
        }

    }

    private void setupProgressDialog()
    {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage(getString(R.string.loading));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setProgress(0);
    }

    private  void createPresenter ()
    {
        newsItemPresenter = new NewsItemPresenter(this);
        newsItemPresenter.requestNewsItems();
    }

    private void initializeView() {
        setupProgressDialog();
        mSwipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
        floatingActionButton.setOnClickListener(fabRefreshListener);
        mProgressBar.setIndeterminate(true);
    }

    @Override
    public void showProgress() {
        if(!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if(mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        mProgressBar.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayErrorNotification(String errorMessage) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(errorMessage)
                .setNeutralButton(R.string.buttonOk, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void setDataToRecyclerView(List<NewsItems> newsItemList, String newsPageTitle) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        newsItemRecyclerView.setLayoutManager(layoutManager);
        NewsItemAdapter adapter = new NewsItemAdapter(MainActivity.this,newsItemList);
        newsItemRecyclerView.setAdapter(adapter);
    }

    SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {

        //Swipe refresh callback
        @Override
        public void onRefresh() {
            if(AppUtils.hasDataConnectivity(MainActivity.this)) {

                if (newsItemPresenter == null) {
                    //create the presenter in-case it wasn't created earlier
                    createPresenter();
                }

                Log.d(TAG, "SwipeRefreshListener clicked");
                newsItemPresenter.onRefreshButtonClick();
            }
            else {
                displayErrorNotification(AppConstants.NoInternet);
            }
        }
    };

    FloatingActionButton.OnClickListener fabRefreshListener = new FloatingActionButton.OnClickListener() {

        //floating button refresh callback
        @Override
        public void onClick(View v) {
            if(AppUtils.hasDataConnectivity(MainActivity.this)) {

                if (newsItemPresenter == null) {
                    //create the presenter in-case it wasn't created earlier
                    createPresenter();
                }
                Log.d(TAG, "fabRefreshListener clicked");
                newsItemPresenter.onRefreshButtonClick();

            }
            else {
                displayErrorNotification(AppConstants.NoInternet);
            }
        }

    };
}
