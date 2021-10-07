package com.example.gymmanagement.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagement.R;
import com.example.gymmanagement.model.UserResponse;

import java.util.ArrayList;

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

        ViewHolder(View itemView){
            super(itemView);
            iconImage = itemView.findViewById(R.id.iconImageView);
            name = itemView.findViewById(R.id.nameTextView);
            plan = itemView.findViewById(R.id.plan);
        }

        void bindData(final UserResponse user){
//            iconImage.setColorFilter(Color.parseColor(user.getColor()));
            name.setText(user.getUserName() + " " + user.getLastName());
            plan.setText(user.getPlan() == null?"No tiene":user.getPlan());
        }
    }
}
