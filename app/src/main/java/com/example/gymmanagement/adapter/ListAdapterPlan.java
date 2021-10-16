package com.example.gymmanagement.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.activity.ActivitiesActivity;
import com.example.gymmanagement.activity.UserManagament;
import com.example.gymmanagement.model.PaymentResponse;
import com.example.gymmanagement.model.Plan;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterPlan extends RecyclerView.Adapter<ListAdapterPlan.ViewHolder> implements Filterable {
    private List<Plan> planList;
    private LayoutInflater inflater;
    private ListAdapter.OnItemClickListener planListener;
    private Context context;
    private List<Plan> filteredPlanList;

    public ListAdapterPlan(List<Plan> planArrayList, Context context){
        this.planList = planArrayList;
        this.context = context;
        this.inflater =  LayoutInflater.from(context);
        this.filteredPlanList = planList;
    }



    public void setOnItemClickListener(ListAdapter.OnItemClickListener listener){
        planListener = listener;
    }

    @Override
    public Filter getFilter() {
        return filtered;
    }

    private final Filter filtered = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if(charSequence == null || charSequence.length() == 0 ){
                filteredPlanList = planList;
            }else if (filteredPlanList.isEmpty()){
                Toast.makeText(context.getApplicationContext(), "NO EXISTEN COINCIDENCIAS",Toast.LENGTH_SHORT).show();
            }else{
                String txt = charSequence.toString().toLowerCase();
                ArrayList<Plan> tempList = new ArrayList<>();

                for(Plan ur : filteredPlanList){
                    if (ur.getPlan().toLowerCase().contains(txt))
                    {
                        tempList.add(ur);
                    }
                }
                filteredPlanList = tempList;
            }

            FilterResults results = new FilterResults();
            results.values = filteredPlanList;
            results.count = planList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredPlanList = (List<Plan>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public List<Plan> getCurrentList() {
        return filteredPlanList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return filteredPlanList.size();
    }

    @Override
    public ListAdapterPlan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_plan_element, null);
        ListAdapterPlan.ViewHolder userViewHolder = new ListAdapterPlan.ViewHolder(view, planListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterPlan.ViewHolder holder, int position) {
        holder.bindData(filteredPlanList.get(position));
        Plan current =filteredPlanList.get(position);
        holder.actividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitiesActivity.class);
                intent.putExtra("planId",current.getPlanId());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public void setItems(List<Plan> items){
        filteredPlanList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        Button actividades;

        ViewHolder(@NonNull @NotNull View itemView, final ListAdapter.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            price = itemView.findViewById(R.id.tvPrice);
            actividades = itemView.findViewById(R.id.buttonActivityPlan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        //lyCategory.setBackgroundColor(Color.parseColor("#ffffff"));
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }


        void bindData(final Plan item) {
            //iconImage.setColorFilter(Color.parseColor(item.get));
            name.setText(item.getPlan());
            price.setText(String.valueOf(item.getPrice())+" Bs.");
        }
    }

}