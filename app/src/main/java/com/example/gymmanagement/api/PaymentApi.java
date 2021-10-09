package com.example.gymmanagement.api;

import com.example.gymmanagement.model.PaymentResponse;
import com.example.gymmanagement.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import java.util.ArrayList;

public interface PaymentApi {
    @Headers({"Accept: application/json"})

    @GET("allPayments")
    Call<ArrayList<PaymentResponse>> getAllPayments();
}
