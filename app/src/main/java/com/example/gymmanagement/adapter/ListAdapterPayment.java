package com.example.gymmanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymmanagement.R;
import com.example.gymmanagement.activity.PaymentActivity;
import com.example.gymmanagement.activity.UserManagament;
import com.example.gymmanagement.model.PaymentResponse;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterPayment extends RecyclerView.Adapter<ListAdapterPayment.ViewHolder> implements Filterable {
    private List<PaymentResponse> userList;
    private LayoutInflater inflater;
    private ListAdapter.OnItemClickListener userListener;
    private Context context;
    private List<PaymentResponse> filteredUserList;
    private UserManagament userManagament;

    public ListAdapterPayment (List<PaymentResponse> userArrayList, Context context){
        this.userList = userArrayList;
        this.context = context;
        this.inflater =  LayoutInflater.from(context);
        this.filteredUserList = userList;
    }



    public void setOnItemClickListener(ListAdapter.OnItemClickListener listener){
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
                ArrayList<PaymentResponse> tempList = new ArrayList<>();

                for(PaymentResponse ur : filteredUserList){
                    if (ur.getName().toLowerCase().contains(txt)||ur.getUserId().toString().toLowerCase().contains(txt))
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
            filteredUserList = (List<PaymentResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    };

    public List<PaymentResponse> getCurrentList() {
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
    public ListAdapterPayment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_user_element_payment, null);
        ListAdapterPayment.ViewHolder userViewHolder = new ListAdapterPayment.ViewHolder(view, userListener);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapterPayment.ViewHolder holder, int position) {
        holder.bindData(filteredUserList.get(position));
    }

    public void setItems(List<PaymentResponse> items){
        filteredUserList = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, lastName, stateStatic, paymentState, ci,planStatic, plan;

        ViewHolder(@NonNull @NotNull View itemView, final ListAdapter.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            lastName = itemView.findViewById(R.id.tvLastName);
            stateStatic = itemView.findViewById(R.id.tvStateStatic);
            ci = itemView.findViewById(R.id.tvCI);
            paymentState = itemView.findViewById(R.id.tvPaymentState);
            planStatic = itemView.findViewById(R.id.tvPlanStatic);
            plan = itemView.findViewById(R.id.tvPlan);

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


        void bindData(final PaymentResponse item) {
            //iconImage.setColorFilter(Color.parseColor(item.get));
            plan.setText(item.getPlan());
            name.setText(item.getName());
            lastName.setText(item.getLastName());
            ci.setText(item.getUserId().toString());
            if(item.getStatus()==0){
                paymentState.setText("No pagado");
            }else{
                paymentState.setText("Pagado");
            }
        }
    }

}