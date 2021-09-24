package com.example.gymmanagement.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;

public class MenuActivity extends AppCompatActivity {

    Button registerButton;
    Integer userId, userTypeId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        registerButton = findViewById(R.id.registerButton);

        switch (userTypeId){
            case 2:
                intent = new Intent(MenuActivity.this, RegisterActivity.class);
                break;
            case 3:
                intent = new Intent(MenuActivity.this, RegisterActivityEncargado.class);
                break;
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });
    }
}