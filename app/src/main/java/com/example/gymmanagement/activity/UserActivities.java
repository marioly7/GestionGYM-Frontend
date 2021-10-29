package com.example.gymmanagement.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.adapter.ListAdapterActivity;
import com.example.gymmanagement.adapter.ListAdapterActivityDetails;
import com.example.gymmanagement.model.Activity;
import com.example.gymmanagement.model.ActivityResponse;
import com.example.gymmanagement.request.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class UserActivities extends AppCompatActivity {

    List<ActivityResponse> activityList = new ArrayList<>();
    List<ActivityResponse> activityOriginalList = new ArrayList<>();
    ListAdapterActivityDetails listAdapter;
    RecyclerView recyclerView;
    Request request = new Request();
    SearchView searchPlan;
    Integer userId, userTypeId, planId;
    TextView tvName, tvPrice;
    Button actividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_activities);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);
        planId = getIntent().getIntExtra("planId", 0);

        tvName = findViewById(R.id.tvName);
        tvPrice = findViewById(R.id.tvPrice);
        actividades = findViewById(R.id.buttonActivityPlan);

        recyclerView = findViewById(R.id.rvPlans);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserActivities.this));
        recyclerView.setAdapter(listAdapter);

        searchPlan = findViewById(R.id.searchPlan);

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
        request.getActivitiesByUserId(userId).enqueue(new Callback<ArrayList<ActivityResponse>>() {
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
                listAdapter=new ListAdapterActivityDetails(activityList,UserActivities.this);
                recyclerView.setAdapter(listAdapter);


                listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(getApplicationContext(), "Usted está inscrito en este horario", Toast.LENGTH_SHORT).show();
                    }
                });

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<ActivityResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
        listAdapter=new ListAdapterActivityDetails(activityList,UserActivities.this);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}