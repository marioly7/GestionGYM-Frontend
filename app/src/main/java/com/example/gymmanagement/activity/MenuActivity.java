package com.example.gymmanagement.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.model.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    Button registerButton, paymentButton, usersButton, graficosButton, planesButton;
    Integer userId, userTypeId;
    Intent intent;
    Button userDisabledButton;
    List<UserResponse> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        registerButton = findViewById(R.id.registerButton);
        paymentButton = findViewById(R.id.paymentButton);
        graficosButton = findViewById(R.id.graficosButton);
        userDisabledButton = findViewById(R.id.userDisabledButton);
        planesButton = findViewById(R.id.plansButton);

        planesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, PlansActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });

        graficosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, ChartsMenu.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });

        userDisabledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, UserDisabledManagement.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, RegisterActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });

        usersButton = findViewById(R.id.usersButton);

        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, UserManagament.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivity.this, PaymentActivity.class);
//                intent.putExtra("userId",userId);
//                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
                Log.d("Payment", "payment ...");
            }
        });
    }
}