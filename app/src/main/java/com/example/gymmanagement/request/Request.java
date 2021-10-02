package com.example.gymmanagement.request;

import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;

public class Request {
    public Call<ArrayList<UserResponse>> getAllUsers(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.31.150:8085/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        UserApi userApi = retrofit.create(UserApi.class);
        Call<ArrayList<UserResponse>> call = userApi.getAllUsers();

        return call;
    }

    public Call<Integer> getId(String email, String password){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.150:8085/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi = retrofit.create(UserApi.class);

        Call<Integer> call = userApi.getId(email, password);

        return call;
    }

    public Call<User> createUser(Integer userType, String userName, String lastName, String email, String password, Integer regitrantId, Integer planId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.31.150:8085/user/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);


        User user = new User();
        user.setUserTypeId(userType);
        user.setUserName(userName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrantId(regitrantId);
        user.setPlanId(planId);

        Call<User> call = userApi.createUser(user);

        return call;
    }

    public Call<Integer> getUserType(Integer userId){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.31.150:8085/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi= retrofit.create(UserApi.class);

        Call<Integer> call = userApi.getUserType(userId);

        return call;
    }
}
