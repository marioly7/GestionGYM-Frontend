package com.example.gymmanagement.activity;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.UserResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class UserManagament extends AppCompatActivity {

    List<UserResponse> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_managament);

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

        call.enqueue(new Callback<ArrayList<UserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserResponse>> call, Response<ArrayList<UserResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                userList = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });

        userList = new ArrayList<>();
        for(int i=0; i<userList.size();i++){
            userList.add(userList.get(i));
            System.out.println("usuario");
        }
        //userList.add(new UserResponse(6875485,"Marioly","Vargas","marioly@gmail.com","molly","cliente","gold"));
        //userList.add(new UserResponse(88732742,"Erlan","Gonzales","erlan@gmail.com","erlan","encargado",""));

        ListAdapter listAdapter=new ListAdapter(userList,UserManagament.this);
        RecyclerView recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserManagament.this));
        recyclerView.setAdapter(listAdapter);
    }
}