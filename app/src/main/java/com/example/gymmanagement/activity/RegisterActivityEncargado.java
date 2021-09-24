package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;
import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegisterActivityEncargado extends AppCompatActivity{

    EditText etName, etLastName, etEmail, etPassword, etConfirmPassword;
    ArrayList<UserResponse> users;
    Button registerButton;
    Integer userType, planId;
    Integer flag=0;
    RadioButton premium, gold;
    RadioButton cli;
    Integer userId, userTypeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_enc);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        registerButton = findViewById(R.id.registerEmpButton);
        cli = findViewById(R.id.radioCli);
        premium = findViewById(R.id.radioPremium);
        gold = findViewById(R.id.radioGold);


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.31.150:8085/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        UserApi userApi = retrofit.create(UserApi.class);
        Call<ArrayList<UserResponse>> call = userApi.getAllUsers();

        call.enqueue(new Callback<ArrayList<UserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserResponse>> call, Response<ArrayList<UserResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                users = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<users.size() ; i++)
                {
                    if((etEmail.getText().toString()).equals(users.get(i).getEmail()))
                    {
                        flag=1;
                    }
                    if (flag==1){
                        break;
                    }
                }


                if(!(etPassword.getText().toString()).equals(etConfirmPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(flag==1){
                    flag=0;
                    Toast.makeText(getApplicationContext(), "El correo: "+etEmail.getText().toString()+" ya pertenece a una cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }else if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etLastName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un apellido", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar un email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debe ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!premium.isChecked() && !gold.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un plan", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (validateEmail(etEmail.getText().toString())) {
                        //Toast.makeText(RegisterActivity.this, "valida correo", Toast.LENGTH_SHORT).show();
                        createUser();
                    } else {
                        Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

    }

    private void createUser() {

        //Toast.makeText(RegisterActivity.this,"entra a create user",Toast.LENGTH_SHORT).show();

        if(premium.isChecked()){
            planId =1;
        }else{
            planId = 2;
        }

        userType = 1;


        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl("http://192.168.31.150:8085/user/")
                //.baseUrl("http://192.168.31.148:8081/v1/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        UserApi userApi= retrofit.create(UserApi.class);


        User user = new User();
        user.setUserTypeId(userType);
        user.setUserName(etName.getText().toString());
        user.setLastName(etLastName.getText().toString());
        user.setEmail(etEmail.getText().toString());
        user.setPassword(etPassword.getText().toString());
        user.setRegistrantId(userId);
        user.setPlanId(planId);

        Call<User> call = userApi.createUser(user);


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(RegisterActivityEncargado.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent (RegisterActivityEncargado.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("code failure","Code: " + t.getMessage());
                Toast.makeText(RegisterActivityEncargado.this,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }


    private boolean validateEmail(String etEmail) {
        //Toast.makeText(RegisterActivity.this,"entra a validate email",Toast.LENGTH_SHORT).show();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(etEmail).matches();
    }

}