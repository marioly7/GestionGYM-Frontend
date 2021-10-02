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
import com.example.gymmanagement.request.Request;
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

    List<UserResponse>  userList = new ArrayList<>();
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    Request request = new Request();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_managament);

        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserManagament.this));
        recyclerView.setAdapter(listAdapter);


        request.getAllUsers().enqueue(new Callback<ArrayList<UserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserResponse>> call, Response<ArrayList<UserResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    UserResponse cr = new UserResponse();
                    cr.setUserName("Code: "+response.code());
                    userList.add(cr);
                    listAdapter=new ListAdapter(userList,UserManagament.this);
                    recyclerView.setAdapter(listAdapter);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                userList = response.body();
                listAdapter=new ListAdapter(userList,UserManagament.this);
                recyclerView.setAdapter(listAdapter);

                    //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });



    }
}