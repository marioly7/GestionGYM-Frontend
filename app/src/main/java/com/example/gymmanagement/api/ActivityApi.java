package com.example.gymmanagement.api;

import com.example.gymmanagement.model.Activity;
import com.example.gymmanagement.model.ActivityResponse;
import com.example.gymmanagement.model.Plan;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.ArrayList;

public interface ActivityApi {

    @Headers({"Accept: application/json"})


    @GET("activityDetails")
    Call<ArrayList<ActivityResponse>> getActivityDetails(@Query("activityId") Integer activityId);

}
