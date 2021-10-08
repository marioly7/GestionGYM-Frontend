package com.example.gymmanagement.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagement.R;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<UserResponse> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(ArrayList<UserResponse> usersList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_users, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }else
            return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, plan;
        Button editButton, payButton;
        Request request = new Request();

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            plan = itemView.findViewById(R.id.plan);
            editButton = itemView.findViewById(R.id.editButton);
            payButton = itemView.findViewById(R.id.payButton);
        }

        void bindData(final UserResponse user){
//            iconImage.setColorFilter(Color.parseColor(user.getColor()));
            name.setText(user.getUserName() + " " + user.getLastName());
            plan.setText(user.getPlan() == null?"No tiene":user.getPlan());
            editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    request.updateUser(user).enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (!response.isSuccessful()) {
                                Log.d("code","Code: " + response.code());
//                                Toast.makeText(RegisterActivity.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
                                return;
                            }
//                            Intent intent = new Intent (RegisterActivity.this, MainActivity.class);
//                            startActivity(intent);
                            Log.d("code1","Code: " + response.code());

                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            Log.d("code failure","Code: " + t.getMessage());
//                            Toast.makeText(null,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });
                }
            });
        }
    }
}
