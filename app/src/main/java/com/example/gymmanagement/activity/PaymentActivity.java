package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagement.Parametros;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.adapter.ListAdapterPayment;
import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.PaymentResponse;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {
    List<PaymentResponse> userList = new ArrayList<>();
    List<PaymentResponse>  userOriginalList = new ArrayList<>();
    ListAdapterPayment listAdapter;
    RecyclerView recyclerView;
    Request request = new Request();
    Integer userIdEdit, userId, userTypeId;
    SearchView searchUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PaymentActivity.this));
        recyclerView.setAdapter(listAdapter);

        searchUser = findViewById(R.id.searchUser);

        searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listAdapter.getFilter().filter(s);
                return false;
            }
        });

        getUserListFromRestAPI();

    }

    public void getUserListFromRestAPI(){
        request.getAllPayments().enqueue(new Callback<ArrayList<PaymentResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentResponse>> call, Response<ArrayList<PaymentResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    PaymentResponse cr = new PaymentResponse();
                    cr.setName("Code: "+response.code());
                    userList.add(cr);
                    userOriginalList.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                userList = response.body();
                listAdapter=new ListAdapterPayment(userList,PaymentActivity.this);
                recyclerView.setAdapter(listAdapter);


                listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        userOriginalList = listAdapter.getCurrentList();
                        userIdEdit=userOriginalList.get(position).getUserId();
                        Intent intent = new Intent (PaymentActivity.this, EditUser.class);
                        intent.putExtra("userIdEdit",userIdEdit);
                        intent.putExtra("userId",userId);
                        intent.putExtra("userTypeId",userTypeId);
                        startActivity(intent);
                    }
                });

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<PaymentResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
        listAdapter=new ListAdapterPayment(userList,PaymentActivity.this);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}
