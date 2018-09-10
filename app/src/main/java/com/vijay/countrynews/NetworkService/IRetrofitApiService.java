package com.vijay.countrynews.NetworkService;

import com.vijay.countrynews.Model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vijayganeshkumar on 02/09/18.
 */

public interface IRetrofitApiService {

    @GET("/s/2iodh4vg0eortkl/facts.json")
    Call<ApiResponse> doGetNewsFeed();

}
