package com.example.gymmanagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.model.UserResponse;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<UserResponse> userList;
    private LayoutInflater inflater;
    private OnItemClickListener userListener;
    private Context context;

    public ListAdapter (List<UserResponse> userList, Context context){
        this.userList = userList;
        this.context = context;
        this.inflater =  LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        userListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_user_element, null);
        ListAdapter.ViewHolder userViewHolder = new ListAdapter.ViewHolder(view, userListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.bindData(userList.get(position));
    }

    public void setItems(List<UserResponse> items){
        userList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView name, lastName, email, userType, plan, ci;

        ViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener){
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            lastName = itemView.findViewById(R.id.tvLastName);
            email = itemView.findViewById(R.id.tvEmail);
            ci = itemView.findViewById(R.id.tvCI);
            userType = itemView.findViewById(R.id.tvUserType);
            plan = itemView.findViewById(R.id.tvPlan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        //lyCategory.setBackgroundColor(Color.parseColor("#ffffff"));
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


        void bindData(final UserResponse item){
            //iconImage.setColorFilter(Color.parseColor(item.get));
            name.setText(item.getUserName());
            lastName.setText(item.getLastName());
            email.setText(item.getEmail());
            userType.setText(item.getUserType());
            plan.setText(item.getPlan());
            ci.setText(item.getIdUser().toString());
        }
    }
}
