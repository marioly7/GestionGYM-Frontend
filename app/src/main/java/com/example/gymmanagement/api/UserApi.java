package com.example.gymmanagement.api;

import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface UserApi {

    //@Headers({"Accept: application/json"})

    @GET("allUsers")
    Call<ArrayList<UserResponse>> getAllUsers();

    @GET("userType")
    Call<Integer> getUserType(@Query("userId") Integer userId);

    @GET("login")
    Call<Integer> getId(@Query("email") String email, @Query("password") String password);

    @POST("addUser")
    Call<User> createUser(@Body User user);


}
