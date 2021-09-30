package com.example.gymmanagement.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class UserManagament extends AppCompatActivity {

    List<UserResponse> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_managament);

        userList = new ArrayList<>();
        userList.add(new UserResponse(6875485,"Marioly","Vargas","marioly@gmail.com","molly","cliente","gold"));
        userList.add(new UserResponse(88732742,"Erlan","Gonzales","erlan@gmail.com","erlan","encargado",""));

        ListAdapter listAdapter=new ListAdapter(userList,UserManagament.this);
        RecyclerView recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserManagament.this));
        recyclerView.setAdapter(listAdapter);
    }
}