package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagement.Parametros;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.adapter.ListAdapterPayment;
import com.example.gymmanagement.api.UserApi;
import com.example.gymmanagement.model.Payment;
import com.example.gymmanagement.model.PaymentResponse;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentActivity extends AppCompatActivity {
    List<PaymentResponse> userList = new ArrayList<>();
    List<PaymentResponse>  userOriginalList = new ArrayList<>();
    ListAdapterPayment listAdapter;
    RecyclerView recyclerView;
    Request request = new Request();
    Integer userIdEdit, userId, userTypeId, paymentId, userStatus;
    SearchView searchUser;
    Button payButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PaymentActivity.this));
        recyclerView.setAdapter(listAdapter);

        searchUser = findViewById(R.id.searchUser);

        searchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listAdapter.getFilter().filter(s);
                return false;
            }
        });

        getUserListFromRestAPI();

    }

    private void updatePayment() {

        request.updatePayment(userIdEdit, userStatus, paymentId).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (!response.isSuccessful()) {
                    Log.d("code","Code: " + response.code());
                    Toast.makeText(PaymentActivity.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println("type: "+userTypeId.toString());

                Intent intent;
                if(userTypeId==1){
                    intent = new Intent (PaymentActivity.this, MenuActivity.class);
                }else{
                    intent = new Intent (PaymentActivity.this, MenuActivityEncargado.class);
                }
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Log.d("code failure","Code: " + t.getMessage());
                Toast.makeText(PaymentActivity.this,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void getUserListFromRestAPI(){
        request.getAllPayments().enqueue(new Callback<ArrayList<PaymentResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentResponse>> call, Response<ArrayList<PaymentResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    PaymentResponse cr = new PaymentResponse();
                    cr.setName("Code: "+response.code());
                    userList.add(cr);
                    userOriginalList.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                userList = response.body();
                listAdapter=new ListAdapterPayment(userList,PaymentActivity.this);
                recyclerView.setAdapter(listAdapter);


                listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        getPaymentStatus(position);
                    }
                });

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<PaymentResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
        listAdapter=new ListAdapterPayment(userList,PaymentActivity.this);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void getPaymentStatus(Integer position) {
        userOriginalList = listAdapter.getCurrentList();
        userIdEdit=userOriginalList.get(position).getUserId();
        paymentId = userOriginalList.get(position).getPaymentId();
        request.getPaymentByUserId(userIdEdit).enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Code usertype: " + response.code());
                    return;
                }
                userStatus =response.body();
                //Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                return;
            }


            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "onFailure paymentActivity", Toast.LENGTH_SHORT).show();
            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // acciones que se ejecutan tras los milisegundos
                alertDialog();
            }
        }, 1000);
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
        builder.setTitle("Confirmación de pago");
        if(userStatus==0){
            builder.setMessage("Está por cambiar el estado a PAGADO para "+userIdEdit);
        }else{
            builder.setMessage("Está por cambiar el estado a NO PAGADO para "+userIdEdit);
        }
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updatePayment();
            }
        })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                        Toast.makeText(getApplicationContext(), "No se registró el pago", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
