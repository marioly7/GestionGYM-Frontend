package com.example.gymmanagement.api;

import com.example.gymmanagement.model.Activity;
import com.example.gymmanagement.model.ActivityResponse;
import com.example.gymmanagement.model.Plan;
import com.example.gymmanagement.model.UserActivity;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface ActivityApi {

    @Headers({"Accept: application/json"})

    @POST("activityRegister")
    Call<UserActivity> activityRegister(@Body UserActivity userActivity);

    @GET("activityScheduleById")
    Call<ActivityResponse> activityScheduleById(@Query("activityId") Integer activityId);

    @GET("activityScheduleByUserId")
    Call<ArrayList<ActivityResponse>> activitiesByUserId(@Query("userId") Integer userId);

    @GET("activityDetails")
    Call<ArrayList<ActivityResponse>> getActivityDetails(@Query("activityId") Integer activityId);

}
