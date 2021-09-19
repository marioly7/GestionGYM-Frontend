package com.example.gymmanagement.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;

public class MenuActivity extends AppCompatActivity {

    Button registerEmpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        registerEmpButton = findViewById(R.id.registerEmpButton);

        registerEmpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}