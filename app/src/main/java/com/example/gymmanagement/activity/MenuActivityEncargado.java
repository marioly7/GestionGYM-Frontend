package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;

public class MenuActivityEncargado extends AppCompatActivity {

    Button registerButton, registerPaymentButton, usersButton, userDisabledButton, pagos;
    Integer userId, userTypeId;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_encargado);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        pagos = findViewById(R.id.miCartera);

        usersButton = findViewById(R.id.usersButton);

        userDisabledButton = findViewById(R.id.userDisabledButton);

        registerButton = findViewById(R.id.registerButton);

        registerPaymentButton = findViewById(R.id.registerPaymentButton);

        pagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityEncargado.this, PaymentUser.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });

        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivityEncargado.this, UserManagament.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });

        userDisabledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivityEncargado.this, UserDisabledManagement.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });

        registerPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MenuActivityEncargado.this, PaymentActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                startActivity(intent);
            }
        });


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