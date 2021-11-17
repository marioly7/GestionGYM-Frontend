package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;
import com.example.gymmanagement.model.Plan;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class EditUser extends AppCompatActivity {

    EditText etCI, etName, etLastName, etEmail, etPassword, etConfirmPassword;
    UserResponse users = new UserResponse();
    Button saveButton, disableButton, enableButton;
    ArrayList<UserResponse> usersa;
    ArrayList<Plan> plans;
    RadioGroup radioPlans;
    RadioButton rdbtn;
    Integer userType, planId;
    Integer flag=0, flagCI=0;
    RadioButton admi, enc, cli;
    Integer userId, userTypeId, userIdEdit; //userEdit es el ID del usuario que queremos editar
    Request request = new Request();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);
        userIdEdit = getIntent().getIntExtra("userIdEdit",0);

        etCI = findViewById(R.id.etCI);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        saveButton = findViewById(R.id.saveChangesButton);
        disableButton = findViewById(R.id.disableButton);
        enableButton = findViewById(R.id.enableButton);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        admi = findViewById(R.id.radioAdmi);
        cli = findViewById(R.id.radioCli);
        enc = findViewById(R.id.radioEnc);
        radioPlans = findViewById(R.id.radioPlans);

        getPlans();

        radioPlans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                if(radioButton.getText()!="Ninguno"){
                    for(int j= 0; j<plans.size();j++){
                        if(radioButton.getText().equals(plans.get(j).getPlan())){
                            planId = plans.get(j).getPlanId();
                        }
                    }
                }else{
                    planId = null;
                }
                //Toast.makeText(getApplicationContext(),radioButton.getText(),Toast.LENGTH_LONG).show();
            }
        });

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setIdUser(userIdEdit);
                request.enableUser(user).enqueue(new Callback<User>() {
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            //textViewResult.setText("Code: " + response.code());
                            Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "Usuario habilitado exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (EditUser.this, UserManagament.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("userTypeId",userTypeId);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error onFailure enable edit user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user=new User();
                user.setIdUser(userIdEdit);
                request.disableUser(user).enqueue(new Callback<User>() {
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            //textViewResult.setText("Code: " + response.code());
                            Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "Usuario deshabilitado exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent (EditUser.this, UserManagament.class);
                        intent.putExtra("userId",userId);
                        intent.putExtra("userTypeId",userTypeId);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error onFailure edit user", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        request.getAllUsers().enqueue(new Callback<ArrayList<UserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserResponse>> call, Response<ArrayList<UserResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                usersa = response.body();
                for(int i=0; i<usersa.size();i++){
                    if(usersa.get(i).getEmail().equals(etEmail.getText().toString())){
                        usersa.remove(i);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                for (int i=0; i<usersa.size() ; i++)
                {
                    if((etEmail.getText().toString()).equals(usersa.get(i).getEmail()))
                    {
                        flag=1;
                    }
                    if (flag==1){
                        break;
                    }
                }

                for (int i=0; i<usersa.size() ; i++)
                {
                    if((etCI.getText().toString()).equals(usersa.get(i).getIdUser().toString()))
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
                }else if(flagCI==1){
                    flagCI=0;
                    Toast.makeText(getApplicationContext(), "El CI: "+etCI.getText().toString()+" ya pertenece a una cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (etName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu nombre", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etLastName.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu apellido", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etCI.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar CI", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes ingresar tu email", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Debes ingresar una contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (validateEmail(etEmail.getText().toString())) {
                        //Toast.makeText(RegisterActivity.this, "valida correo", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditUser.this);
                        builder.setMessage("Guardar cambios?")
                                .setTitle("Confirmación de almacenamiento")
                                .setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        updateUser();
                                    }
                                })
                                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // CANCEL
                                        Toast.makeText(getApplicationContext(), "No se guardaron los datos", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Email inválido", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });


        request.getUserById(userIdEdit).enqueue(new Callback<UserResponse>() {
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                users = response.body();

                etCI.setText(users.getIdUser().toString());
                etName.setText(users.getUserName());
                etLastName.setText(users.getLastName());
                etEmail.setText(users.getEmail());
                etPassword.setText(users.getPassword());

                System.out.println("plan "+users.getPlan());

            }


            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateEmail(String etEmail) {
        //Toast.makeText(RegisterActivity.this,"entra a validate email",Toast.LENGTH_SHORT).show();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(etEmail).matches();
    }

    private void updateUser() {

        //Toast.makeText(RegisterActivity.this,"entra a create user",Toast.LENGTH_SHORT).show();


        if(admi.isChecked()){
            userType = 1;
        }else if(enc.isChecked()){
            userType = 2;
        }else{
            userType = 3;
        }


        request.updateUser(Integer.parseInt(etCI.getText().toString()),userType,etName.getText().toString(),etLastName.getText().toString(),etEmail.getText().toString(),etPassword.getText().toString(),userId,planId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(EditUser.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Cambios guardados con éxito", Toast.LENGTH_SHORT).show();

                changeScreen();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("code failure","Code: " + t.getMessage());
                Toast.makeText(EditUser.this,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void changeScreen() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                Intent intent = new Intent (EditUser.this, UserManagament.class);
                startActivity(intent);
            }
        }, 1000);
    }

    public void addRadioButtons(){
        radioPlans.setOrientation(LinearLayout.VERTICAL);
        //
        for(int i=0; i<plans.size();i++) {
            rdbtn = new RadioButton(EditUser.this);
            rdbtn.setId(View.generateViewId());
            //System.out.println("id "+rdbtn.getId());
            rdbtn.setText(plans.get(i).getPlan());
            rdbtn.setTextSize(22);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rdbtn.setLayoutParams(params);
            radioPlans.addView(rdbtn);
        }

        rdbtn = new RadioButton(EditUser.this);
        rdbtn.setId(View.generateViewId());
        rdbtn.setText("Ninguno");
        rdbtn.setTextSize(22);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        rdbtn.setLayoutParams(params);
        radioPlans.addView(rdbtn);
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
}