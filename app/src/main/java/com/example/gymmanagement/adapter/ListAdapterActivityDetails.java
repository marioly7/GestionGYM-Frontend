package com.example.gymmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.model.Activity;
import com.example.gymmanagement.model.ActivityResponse;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterActivityDetails extends RecyclerView.Adapter<ListAdapterActivityDetails.ViewHolder> implements Filterable {
    private List<ActivityResponse> activityList;
    private LayoutInflater inflater;
    private ListAdapter.OnItemClickListener planListener;
    private Context context;
    private List<ActivityResponse> filteredActivityList;

    public ListAdapterActivityDetails(List<ActivityResponse> activityArrayList, Context context){
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
                ArrayList<ActivityResponse> tempList = new ArrayList<>();

                for(ActivityResponse ur : filteredActivityList){
                    if (ur.getInstructor().toLowerCase().contains(txt))
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
            filteredActivityList = (List<ActivityResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public List<ActivityResponse> getCurrentList() {
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
    public ListAdapterActivityDetails.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_activity_details_element, null);
        ListAdapterActivityDetails.ViewHolder userViewHolder = new ListAdapterActivityDetails.ViewHolder(view, planListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterActivityDetails.ViewHolder holder, int position) {
        holder.bindData(filteredActivityList.get(position));
    }

    public void setItems(List<ActivityResponse> items){
        filteredActivityList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,capacity,instructor,day,hour;

        ViewHolder(@NonNull @NotNull View itemView, final ListAdapter.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            capacity = itemView.findViewById(R.id.tvCapacidad);
            instructor = itemView.findViewById(R.id.tvInstructor);
            day = itemView.findViewById(R.id.tvDia);
            hour = itemView.findViewById(R.id.tvHora);


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


        void bindData(final ActivityResponse item) {
            //iconImage.setColorFilter(Color.parseColor(item.get));
            capacity.setText("Espacios: "+item.getCapacity().toString());
            instructor.setText(item.getInstructor());
            day.setText(item.getDay().toString());
            hour.setText(item.getHour().toString());
            name.setText(item.getActivity());
        }
    }

}