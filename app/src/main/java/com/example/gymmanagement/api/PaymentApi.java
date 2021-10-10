package com.example.gymmanagement.api;

import com.example.gymmanagement.model.Payment;
import com.example.gymmanagement.model.PaymentResponse;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface PaymentApi {
    @Headers({"Accept: application/json"})

    @GET("allPayments")
    Call<ArrayList<PaymentResponse>> getAllPayments();

    @POST("addPayment")
    Call<Payment> addPayment(@Body Payment payment);

    @GET("paymentByUserId")
    Call<Integer> getPaymentByUserId(@Query("userId") Integer userId);

    @PUT("updatePayment")
    Call<Payment> updatePayment(@Body Payment payment);
}
