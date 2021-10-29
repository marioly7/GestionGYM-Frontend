package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.adapter.ListAdapterActivity;
import com.example.gymmanagement.adapter.ListAdapterActivityDetails;
import com.example.gymmanagement.model.Activity;
import com.example.gymmanagement.model.ActivityResponse;
import com.example.gymmanagement.model.UserActivity;
import com.example.gymmanagement.request.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ActivityDetailsAcitivty extends AppCompatActivity {

    List<ActivityResponse> activityList = new ArrayList<>();
    List<ActivityResponse> activityOriginalList = new ArrayList<>();
    ListAdapterActivityDetails listAdapter;
    RecyclerView recyclerView;
    Request request = new Request();
    SearchView searchPlan;
    Integer userId, userTypeId, activityId,planId;
    TextView tvName, tvPrice, title;
    Button actividades;
    String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_details);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);
        activityId = getIntent().getIntExtra("activityId", 0);
        planId = getIntent().getIntExtra("planId",0);
        activity = getIntent().getStringExtra("activity");

        title = findViewById(R.id.textViewActivityDetails);
        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        actividades = findViewById(R.id.buttonActivityPlan);


        recyclerView = findViewById(R.id.rvAcitivityDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ActivityDetailsAcitivty.this));
        recyclerView.setAdapter(listAdapter);

        searchPlan = findViewById(R.id.searchActivityDetails);

        title.setText("Detalles "+activity);

        searchPlan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        getActivityListFromRestAPI();
    }

    public void getActivityListFromRestAPI(){
        request.getActivityDetails(activityId).enqueue(new Callback<ArrayList<ActivityResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ActivityResponse>> call, Response<ArrayList<ActivityResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    ActivityResponse cr = new ActivityResponse();
                    //cr.setUserName("Code: "+response.code());
                    activityList.add(cr);
                    activityOriginalList.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                activityList = response.body();
                listAdapter=new ListAdapterActivityDetails(activityList, ActivityDetailsAcitivty.this);
                recyclerView.setAdapter(listAdapter);


                listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        activityOriginalList = listAdapter.getCurrentList();
                        Integer activityScheduleId = activityOriginalList.get(position).getActivityScheduleId();
                        getEspaciosDisponibles(activityScheduleId);
                    }
                });

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<ActivityResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
        listAdapter=new ListAdapterActivityDetails(activityList, ActivityDetailsAcitivty.this);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    private void getEspaciosDisponibles(Integer activityScheduleId) {
        final ActivityResponse[] activityResponse = {new ActivityResponse()};
        request.activityScheduleById(activityScheduleId).enqueue(new Callback<ActivityResponse>() {
            @Override
            public void onResponse(Call<ActivityResponse> call, Response<ActivityResponse> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    //cr.setUserName("Code: "+response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                activityResponse[0] = response.body();
                if(activityResponse[0].getCapacity()>0){
                    alertDialog(activityScheduleId);
                }else{
                    Toast.makeText(getApplicationContext(), "Lo sentimos, ya no hay espacios disponibles para esta actividad", Toast.LENGTH_SHORT).show();
                }
                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ActivityResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void alertDialog(Integer activityScheduleId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityDetailsAcitivty.this);
        builder.setTitle("Confirmación de inscripcion");
        builder.setMessage("Está por inscribirse a "+activity);

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        registerUserInActivity(activityScheduleId);
                    }
                })
                .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // CANCEL
                        Toast.makeText(getApplicationContext(), "No se registró la inscripcion", Toast.LENGTH_SHORT).show();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void registerUserInActivity(Integer activityScheduleId) {
        System.out.println("user Id"+userId);
        System.out.println("activityShceduelid "+activityScheduleId);
        request.activityRegister(userId,activityScheduleId).enqueue(new Callback<UserActivity>() {
            @Override
            public void onResponse(Call<UserActivity> call, Response<UserActivity> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    //cr.setUserName("Code: "+response.code());
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Usted ha sido inscrito correctamente a "+activity+"!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityDetailsAcitivty.this, ActivitiesActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userTypeId",userTypeId);
                intent.putExtra("planId",planId);
                startActivity(intent);
            }


            @Override
            public void onFailure(Call<UserActivity> call, Throwable t) {

            }
        });

    }
}