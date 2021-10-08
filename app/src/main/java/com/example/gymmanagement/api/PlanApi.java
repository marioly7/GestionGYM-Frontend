package com.example.gymmanagement.api;

import com.example.gymmanagement.model.Plan;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface PlanApi {

    @Headers({"Accept: application/json"})

    @GET("allPlans")
    Call<ArrayList<Plan>> getPlans();

}
