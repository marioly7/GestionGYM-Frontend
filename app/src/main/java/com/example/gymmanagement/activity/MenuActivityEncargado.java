package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;

public class MenuActivityEncargado extends AppCompatActivity {

    Button registerButton;
    Integer userId, userTypeId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_encargado);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        registerButton = findViewById(R.id.registerButton);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivityEncargado.this, RegisterActivityEncargado.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });
    }
}