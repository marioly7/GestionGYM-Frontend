package com.example.gymmanagement.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.activity.UserManagament;
import com.example.gymmanagement.model.User;
import com.example.gymmanagement.model.UserResponse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> implements Filterable {
    private List<UserResponse> userList;
    private LayoutInflater inflater;
    private OnItemClickListener userListener;
    private Context context;
    private List<UserResponse> filteredUserList;
    private UserManagament userManagament;

    public ListAdapter (List<UserResponse> userArrayList, Context context){
        this.userList = userArrayList;
        this.context = context;
        this.inflater =  LayoutInflater.from(context);
        this.filteredUserList = userList;
    }



    public void setOnItemClickListener(OnItemClickListener listener){
        userListener = listener;
    }

    @Override
    public Filter getFilter() {
        return filtered;
    }

    private final Filter filtered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence == null || charSequence.length() == 0 ){
                filteredUserList = userList;
            }else if (filteredUserList.isEmpty()){
                Toast.makeText(context.getApplicationContext(), "NO EXISTEN COINCIDENCIAS",Toast.LENGTH_SHORT).show();
            }else{
                String txt = charSequence.toString().toLowerCase();
                ArrayList<UserResponse> tempList = new ArrayList<>();

                for(UserResponse ur : filteredUserList){
                    if (ur.getUserName().toLowerCase().contains(txt)||ur.getIdUser().toString().toLowerCase().contains(txt)||ur.getPlan().toLowerCase().contains(txt))
                    {
                        tempList.add(ur);
                    }
                }
                filteredUserList = tempList;
            }

            FilterResults results = new FilterResults();
            results.values = filteredUserList;
            results.count = userList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredUserList = (List<UserResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public List<UserResponse> getCurrentList() {
        return filteredUserList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return filteredUserList.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_user_element, null);
        ListAdapter.ViewHolder userViewHolder = new ListAdapter.ViewHolder(view, userListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        holder.bindData(filteredUserList.get(position));
    }

    public void setItems(List<UserResponse> items){
        filteredUserList = items;
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
