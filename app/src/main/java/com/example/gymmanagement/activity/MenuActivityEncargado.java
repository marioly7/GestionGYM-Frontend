package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;

public class MenuActivityEncargado extends AppCompatActivity {

    Button registerButton, registerPaymentButton, usersButton, userDisabledButton, pagos;
    Integer userId, userTypeId;
    Intent intent;
    Button cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_encargado);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        cerrar = findViewById(R.id.cerrarSesion);

        pagos = findViewById(R.id.miCartera);

        usersButton = findViewById(R.id.usersButton);

        userDisabledButton = findViewById(R.id.userDisabledButton);

        registerButton = findViewById(R.id.registerButton);

        registerPaymentButton = findViewById(R.id.registerPaymentButton);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

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

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivityEncargado.this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("Está seguro de cerrar sesión?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MenuActivityEncargado.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}