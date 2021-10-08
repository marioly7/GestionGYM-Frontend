package com.example.gymmanagement.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.model.Plan;
import com.example.gymmanagement.model.PlanQuantity;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Collections;

public class PieChartPlans extends AppCompatActivity {

    private ArrayList<UserResponse> users = new ArrayList<>();
    private Request request = new Request();
    private ArrayList<Plan> plans = new ArrayList<>();
    private ArrayList<PlanQuantity> planQuantities = new ArrayList<>();
    public PlanQuantity planQuantity;



    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pie_chart_plan);

        getUsers();

    }

    private void createChart() {

        PieChart pieChart = findViewById(R.id.pieChartPlans);

        ArrayList<PieEntry> plans = new ArrayList<>();

        for(int i=0;i<planQuantities.size();i++){
            plans.add(new PieEntry(planQuantities.get(i).getQuantity(),planQuantities.get(i).getPlan()));
        }

        PieDataSet pieDataSet = new PieDataSet(plans,"Planes");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Planes");
        pieChart.animate();
        pieChart.invalidate();

    }

    private void getUsers() {
        request.getAllUsers().enqueue(new Callback<ArrayList<UserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserResponse>> call, Response<ArrayList<UserResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    UserResponse cr = new UserResponse();
                    cr.setUserName("Code: "+response.code());
                    users.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                users = response.body();

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPlans();
            }
        }, 4000);

    }

    public void getPlans(){
        Request request = new Request();
        request.getPlans().enqueue(new Callback<ArrayList<Plan>>() {
            @Override
            public void onResponse(Call<ArrayList<Plan>> call, Response<ArrayList<Plan>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    //Plan cr = new Plan();
                    //cr.setUserName("Code: "+response.code());
                    //plans.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                plans = response.body();

                agregarPlanQuantity();

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<Plan>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarPlanQuantity() {
        for(int i=0; i<plans.size();i++){
            System.out.println("entra a for de plans");
            int contador=0;
            for(int j=0;j<users.size();j++){
                if(plans.get(i).getPlan().equals(users.get(j).getPlan())){
                    contador++;
                    planQuantity = new PlanQuantity();
                    System.out.println("contador "+contador+" plan: "+plans.get(i).getPlan());
                    planQuantity.setPlan(plans.get(i).getPlan());
                    planQuantity.setQuantity(contador);
                }
            }
            if(contador>0) {
                planQuantities.add(planQuantity);
            }
        }

        createChart();
    }

}
