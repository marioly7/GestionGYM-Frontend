package com.example.gymmanagement.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class EditUser extends AppCompatActivity {

    EditText etName, etLastName, etEmail, etPassword, etConfirmPassword;
    UserResponse users = new UserResponse();
    Button registerButton;
    Integer userType, planId;
    Integer flag=0;
    RadioButton premium, gold, none;
    RadioButton admi, enc, cli;
    Integer userId, userTypeId, userIdEdit;
    Request request = new Request();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);
        userIdEdit = getIntent().getIntExtra("userIdEdit",0);

        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        registerButton = findViewById(R.id.registerEmpButton);
        admi = findViewById(R.id.radioAdmi);
        cli = findViewById(R.id.radioCli);
        enc = findViewById(R.id.radioEnc);
        premium = findViewById(R.id.radioPremium);
        gold = findViewById(R.id.radioGold);
        none = findViewById(R.id.radioNone);


        request.getUserById(userIdEdit).enqueue(new Callback<UserResponse>() {
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                users = response.body();

                etName.setText(users.getUserName());
                etLastName.setText(users.getLastName());
                etEmail.setText(users.getEmail());
                etPassword.setText(users.getPassword());

                System.out.println("plan "+users.getPlan());
                if(users.getPlan()!=null) {
                    if (users.getPlan().equals("Gold")) {
                        gold.setChecked(true);
                    } else if (users.getPlan().equals("Premium")) {
                        premium.setChecked(true);
                    }
                }else{
                    none.setChecked(true);
                }

                if(users.getUserType().equals("Administrador")){
                    admi.setChecked(true);
                }else if(users.getUserType().equals("Encargado")){
                    enc.setChecked(true);
                }else{
                    cli.setChecked(true);
                }
            }


            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
}