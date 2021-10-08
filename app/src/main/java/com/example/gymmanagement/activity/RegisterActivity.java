package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.Plan;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity{

    EditText etCI, etName, etLastName, etEmail, etPassword, etConfirmPassword;
    ArrayList<UserResponse> users;
    ArrayList<Plan> plans;
    Button registerButton;
    Integer userType, planId;
    Integer flag=0, flagCI=0;
    RadioButton premium, gold, none;
    RadioButton admi, enc, cli;
    Integer userId, userTypeId;
    Request request = new Request();
    RadioGroup radioPlans;
    RadioButton rdbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        etCI  =findViewById(R.id.etCI);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        registerButton = findViewById(R.id.registerEmpButton);
        admi = findViewById(R.id.radioAdmi);
        cli = findViewById(R.id.radioCli);
        enc = findViewById(R.id.radioEnc);
        radioPlans = findViewById(R.id.radioPlans);

        getPlans();


        request.getAllUsers().enqueue(new Callback<ArrayList<UserResponse>>() {
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

        radioPlans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                if(radioButton.getText()!="Ninguno"){
                    planId = radioButton.getId();
                }else{
                    planId = null;
                }
                //Toast.makeText(getApplicationContext(),radioButton.getText(),Toast.LENGTH_LONG).show();
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

                for (int i=0; i<users.size() ; i++)
                {
                    if((etCI.getText().toString()).equals(users.get(i).getIdUser().toString()))
                    {
                        flagCI=1;
                    }
                    if (flagCI==1){
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
                }
                else if(flagCI==1){
                    flagCI=0;
                    Toast.makeText(getApplicationContext(), "El CI: "+etCI.getText().toString()+" ya pertenece a una cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }else if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show();
                    return;
                }else if (etCI.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar CI", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etLastName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu apellido", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debes ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!admi.isChecked() && !cli.isChecked() && !enc.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un tipo de usuario", Toast.LENGTH_SHORT).show();
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


        if(admi.isChecked()){
            userType = 1;
        }else if(enc.isChecked()){
            userType = 2;
        }else{
            userType = 3;
        }


        request.createUser(Integer.parseInt(etCI.getText().toString()),userType,etName.getText().toString(),etLastName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString(),userId,planId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(RegisterActivity.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent (RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("code failure","Code: " + t.getMessage());
                Toast.makeText(RegisterActivity.this,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }


    private boolean validateEmail(String etEmail) {
        //Toast.makeText(RegisterActivity.this,"entra a validate email",Toast.LENGTH_SHORT).show();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(etEmail).matches();
    }

    public void getPlans(){
        Request request = new Request();
        request.getPlans().enqueue(new Callback<ArrayList<Plan>>() {
            @Override
            public void onResponse(Call<ArrayList<Plan>> call, Response<ArrayList<Plan>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Plan cr = new Plan();
                    //cr.setUserName("Code: "+response.code());
                    plans.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                plans = response.body();
                addRadioButtons();

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<Plan>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void addRadioButtons(){
        radioPlans.setOrientation(LinearLayout.VERTICAL);
        //
        for(int i=0; i<plans.size();i++) {
            rdbtn = new RadioButton(RegisterActivity.this);
            rdbtn.setId(View.generateViewId());
            //System.out.println("id "+rdbtn.getId());
            rdbtn.setText(plans.get(i).getPlan());
            rdbtn.setTextSize(22);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rdbtn.setLayoutParams(params);
            radioPlans.addView(rdbtn);
        }

        rdbtn = new RadioButton(RegisterActivity.this);
        rdbtn.setId(View.generateViewId());
        rdbtn.setText("Ninguno");
        rdbtn.setTextSize(22);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        rdbtn.setLayoutParams(params);
        radioPlans.addView(rdbtn);


    }

}