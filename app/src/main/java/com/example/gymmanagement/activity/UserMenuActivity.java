package com.example.gymmanagement.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;

public class UserMenuActivity extends AppCompatActivity {

    Button plans;
    Integer userId,userTypeId,planId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);
        planId = getIntent().getIntExtra("planId",0);

        plans = findViewById(R.id.plansButton);

        plans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserMenuActivity.this, ActivitiesActivity.class);
                intent.putExtra("planId",planId);
                startActivity(intent);
            }
        });
    }
}