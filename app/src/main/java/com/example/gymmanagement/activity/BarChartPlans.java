package com.example.gymmanagement.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class BarChartPlans extends AppCompatActivity {

    private ArrayList<UserResponse> users;
    private Request request = new Request();
    private Integer gold=0, premium=0;



    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bar_chart_plan);

        getUsers();

    }

    private void createChart() {

        BarChart barChart = findViewById(R.id.barChartPlans);

        ArrayList<BarEntry> plans = new ArrayList<>();

        plans.add(new BarEntry(1,gold));
        plans.add(new BarEntry(2,premium));

        BarDataSet barDataSet = new BarDataSet(plans,"Planes");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Planes");
        barChart.animateY(2000);
        //barChart.invalidate();

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
                counter();
            }
        }, 4000);

    }

    private void counter() {
        for(int i=0; i<users.size();i++){
            if(users.get(i).getPlan()!=null) {
                if (users.get(i).getPlan().equals("Gold")) {
                    gold++;
                } else {
                    premium++;
                }
            }
        }
        createChart();
    }
}
