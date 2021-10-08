package com.example.gymmanagement.activity;

import android.content.Intent;
import android.widget.RadioButton;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.adapter.ListAdapter;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class UserDisabledManagement extends AppCompatActivity {

    List<UserResponse> userList = new ArrayList<>();
    List<UserResponse>  userOriginalList = new ArrayList<>();
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    Request request = new Request();
    Integer userIdEdit, userId, userTypeId;
    SearchView searchUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_disabled_management);

        userId = getIntent().getIntExtra("userId", 0);
        userTypeId = getIntent().getIntExtra("userTypeId", 0);

        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserDisabledManagement.this));
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

    public void getUserListFromRestAPI(){
        request.getAllUsersDisabled().enqueue(new Callback<ArrayList<UserResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<UserResponse>> call, Response<ArrayList<UserResponse>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    UserResponse cr = new UserResponse();
                    cr.setUserName("Code: "+response.code());
                    userList.add(cr);
                    userOriginalList.add(cr);
                    Toast.makeText(getApplicationContext(), "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                userList = response.body();
                listAdapter=new ListAdapter(userList,UserDisabledManagement.this);
                recyclerView.setAdapter(listAdapter);


                listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        userOriginalList = listAdapter.getCurrentList();
                        userIdEdit=userOriginalList.get(position).getIdUser();
                        Intent intent = new Intent (UserDisabledManagement.this, EditUser.class);
                        intent.putExtra("userIdEdit",userIdEdit);
                        intent.putExtra("userId",userId);
                        intent.putExtra("userTypeId",userTypeId);
                        startActivity(intent);
                    }
                });

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<UserResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
        listAdapter=new ListAdapter(userList,UserDisabledManagement.this);
        recyclerView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }
}