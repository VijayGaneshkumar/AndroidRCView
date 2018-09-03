package com.vijay.countrynews;


import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import com.vijay.countrynews.Model.ApiResponse;
import com.vijay.countrynews.Network.IRetrofitApiService;
import com.vijay.countrynews.Network.RetrofitApiClient;
import com.vijay.countrynews.Utils.AppConstants;
import com.vijay.countrynews.Views.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by vijayganeshkumar on 04/09/18.
 */


@RunWith(AndroidJUnit4.class)
public class NetworkTest {
    private IRetrofitApiService retrofitInterface;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        //setup
    }

    @Test
    public void testNetworkCall() throws Exception {
            retrofitInterface  = RetrofitApiClient.getRetrofitInstance().create(IRetrofitApiService.class);
        retrofitInterface.doGetNewsFeed().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        assertEquals("Response is successful!", "200", response.code());
                    }
                } else {
                    assertTrue("Error in Response", response.code() > 200);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    if (t != null)
                        assertNull("Internet not available", t == null);
                }
            }
        });
    }
}