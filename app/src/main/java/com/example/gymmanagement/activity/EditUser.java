package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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
import java.util.regex.Pattern;

public class EditUser extends AppCompatActivity {

    EditText etCI, etName, etLastName, etEmail, etPassword, etConfirmPassword;
    UserResponse users = new UserResponse();
    Button saveButton, disableButton, enableButton;
    Integer userType, planId;
    Integer flag=0;
    RadioButton premium, gold, none;
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
        premium = findViewById(R.id.radioPremium);
        gold = findViewById(R.id.radioGold);
        none = findViewById(R.id.radioNone);

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(etPassword.getText().toString()).equals(etConfirmPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(flag==1){
                    flag=0;
                    Toast.makeText(getApplicationContext(), "El correo: "+etEmail.getText().toString()+" ya pertenece a una cuenta", Toast.LENGTH_SHORT).show();
                    return;
                }else if (etName.getText().toString().isEmpty()) {
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
                }else if (!admi.isChecked() && !cli.isChecked() && !enc.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un tipo de usuario", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!premium.isChecked() && !gold.isChecked() && cli.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Debe seleccionar un plan", Toast.LENGTH_SHORT).show();
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

    private boolean validateEmail(String etEmail) {
        //Toast.makeText(RegisterActivity.this,"entra a validate email",Toast.LENGTH_SHORT).show();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(etEmail).matches();
    }

    private void updateUser() {

        //Toast.makeText(RegisterActivity.this,"entra a create user",Toast.LENGTH_SHORT).show();

        if(premium.isChecked()){
            planId =1;
        }else if(gold.isChecked()){
            planId = 2;
        }else{
            planId = null;
        }


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
                Intent intent = new Intent (EditUser.this, MainActivity.class);
                startActivity(intent);
            }
        }, 1000);
    }

}