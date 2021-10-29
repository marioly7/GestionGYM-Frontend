package com.example.gymmanagement.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class UserMenuActivity extends AppCompatActivity {

    Button plans, horarios;
    Integer userId,userTypeId,planId;
    Request request = new Request();
    Integer status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);
        planId = getIntent().getIntExtra("planId",0);

        plans = findViewById(R.id.plansButton);
        horarios = findViewById(R.id.misHorariosButton);

        horarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserMenuActivity.this, UserActivities.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                intent.putExtra("planId",planId);
                startActivity(intent);
            }
        });

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPaymentStatus();
            }
        });
    }

    private void checkPaymentStatus() {
        request.getPaymentByUserId(userId).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                status = response.body();
                if(status==1){
                    Intent intent=new Intent(UserMenuActivity.this, ActivitiesActivity.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("userTypeId",userTypeId);
                    intent.putExtra("planId",planId);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "No tiene acceso a las actividades debido a un pago pendiente", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}