package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;
import com.example.gymmanagement.api.UserApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginButton;
    TextView customerService;
    Integer passwordCounter=1, userId, userTypeId;
    private EditText etEmail;
    private EditText etPassword;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        customerService = findViewById(R.id.customerService);
        customerService.setOnClickListener(this);

        SpannableString content = new SpannableString("COMUNÍCATE CON NOSOTROS");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        customerService.setText(content);

        loginButton = findViewById(R.id.loginbutton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordCounter<3){
                if (etPassword.getText().toString().isEmpty() || etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.31.150:8085/user/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(httpClient)
                            .build();
                    final UserApi userApi = retrofit.create(UserApi.class);

                    Call<Integer> call = userApi.getId(etEmail.getText().toString(), etPassword.getText().toString());

                    call.enqueue(new Callback<Integer>() {
                                     @Override
                                     public void onResponse(Call<Integer> call, Response<Integer> response) {
                                         if (!response.isSuccessful()) {
                                             etEmail.setText("Code userId: " + response.code());
                                             return;
                                         }
                                         userId = response.body();
                                         userTypeForMenu(userId);
                                     }

                                     @Override
                                     public void onFailure(Call<Integer> call, Throwable t) {
                                         //etEmail.setText("userId "+t.getMessage());
                                         etEmail.setText("");
                                         etPassword.setText("");
                                         passwordCounter++;
                                         Toast.makeText(MainActivity.this, "Email o password incorrectos", Toast.LENGTH_SHORT).show();
                                         return;
                                     }
                                 }
                    );
                }
                }else{
                    Toast.makeText(MainActivity.this, "Ha llegado al máximo de intentos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Comuníquese con atención al cliente", Toast.LENGTH_SHORT).show();
                    etEmail.setEnabled(false);
                    etPassword.setEnabled(false);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // acciones que se ejecutan tras los milisegundos
                            etEmail.setEnabled(true);
                            etPassword.setEnabled(true);
                            passwordCounter=1;
                        }
                    }, 10000);
                }
            }
        });
    }

    private void userTypeForMenu(final Integer userId) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.31.150:8085/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
        final UserApi userApi= retrofit.create(UserApi.class);

        Call<Integer> call = userApi.getUserType(userId);

        call.enqueue(new Callback<Integer>() {
                         @Override
                         public void onResponse(Call<Integer> call, Response<Integer> response) {
                             if (!response.isSuccessful()) {
                                 etEmail.setText("Code usertype: " + response.code());
                                 return;
                             }
                             userTypeId = response.body();

                             //Toast.makeText(MainActivity.this,"userTypeId: "+userTypeId, Toast.LENGTH_SHORT).show();

                             switch (userTypeId){
                                 case 2:
                                     intent = new Intent(MainActivity.this, MenuActivity.class);
                                     break;
                                 case 3:
                                     intent = new Intent(MainActivity.this, MenuActivityEncargado.class);
                                     break;
                             }

                             intent.putExtra("userId",userId);
                             intent.putExtra("userTypeId", userTypeId);
                             startActivity(intent);

                         }

                         @Override
                         public void onFailure(Call<Integer> call, Throwable t) {
                             etEmail.setText(t.getMessage());
                             return;
                         }
                     }
        );
    }

    @Override
    public void onClick(View view) {
        Intent intent1 = new Intent(MainActivity.this, CustomerService.class);
        startActivity(intent1);
    }
}