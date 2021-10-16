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
import com.example.gymmanagement.model.Activity;
import com.example.gymmanagement.model.Plan;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterActivity extends RecyclerView.Adapter<ListAdapterActivity.ViewHolder> implements Filterable {
    private List<Activity> activityList;
    private LayoutInflater inflater;
    private ListAdapter.OnItemClickListener planListener;
    private Context context;
    private List<Activity> filteredActivityList;

    public ListAdapterActivity(List<Activity> activityArrayList, Context context){
        this.activityList = activityArrayList;
        this.context = context;
        this.inflater =  LayoutInflater.from(context);
        this.filteredActivityList = activityList;
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
                filteredActivityList = activityList;
            }else if (filteredActivityList.isEmpty()){
                Toast.makeText(context.getApplicationContext(), "NO EXISTEN COINCIDENCIAS",Toast.LENGTH_SHORT).show();
            }else{
                String txt = charSequence.toString().toLowerCase();
                ArrayList<Activity> tempList = new ArrayList<>();

                for(Activity ur : filteredActivityList){
                    if (ur.getActivity().toLowerCase().contains(txt))
                    {
                        tempList.add(ur);
                    }
                }
                filteredActivityList = tempList;
            }

            FilterResults results = new FilterResults();
            results.values = filteredActivityList;
            results.count = activityList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            filteredActivityList = (List<Activity>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public List<Activity> getCurrentList() {
        return filteredActivityList;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public int getItemCount() {
        return filteredActivityList.size();
    }

    @Override
    public ListAdapterActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_activity_element, null);
        ListAdapterActivity.ViewHolder userViewHolder = new ListAdapterActivity.ViewHolder(view, planListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterActivity.ViewHolder holder, int position) {
        holder.bindData(filteredActivityList.get(position));
    }

    public void setItems(List<Activity> items){
        filteredActivityList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ViewHolder(@NonNull @NotNull View itemView, final ListAdapter.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);

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


        void bindData(final Activity item) {
            //iconImage.setColorFilter(Color.parseColor(item.get));
            name.setText(item.getActivity());
        }
    }

}