package com.example.gymmanagement.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymmanagement.R;
import com.example.gymmanagement.model.Plan;
import com.example.gymmanagement.model.UserResponse;
import com.example.gymmanagement.request.Request;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
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
            getPlanPay();
//            iconImage.setColorFilter(Color.parseColor(user.getColor()));
            name.setText(user.getUserName() + " " + user.getLastName());
            plan.setText(user.getPlan() == null?"No tiene":user.getPlan().toString());
            if (user.getPlan() == null) {
                payButton.setVisibility(View.INVISIBLE);
            } else {
                payButton.setVisibility(View.VISIBLE);
            }
            editButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
//                    request.updateUserPlan(user).enqueue(new Callback<UserResponse>() {
//                        @Override
//                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                            if (!response.isSuccessful()) {
//                                Log.d("code","Code: " + response.code());
////                                Toast.makeText(RegisterActivity.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
//                                return;
//                            }
////                            Intent intent = new Intent (RegisterActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            Log.d("code1","Code: " + response.code());
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<UserResponse> call, Throwable t) {
//                            Log.d("code failure","Code: " + t.getMessage());
////                            Toast.makeText(null,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    });

                    Log.d("edit", "edit" + user.getPlan());
                    showAlertDialog();
                }
            });

            payButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
//                    request.updateUserPlan(user).enqueue(new Callback<UserResponse>() {
//                        @Override
//                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                            if (!response.isSuccessful()) {
//                                Log.d("code","Code: " + response.code());
////                                Toast.makeText(RegisterActivity.this,"Respponse: "+response.code(),Toast.LENGTH_SHORT).show();
//                                return;
//                            }
////                            Intent intent = new Intent (RegisterActivity.this, MainActivity.class);
////                            startActivity(intent);
//                            Log.d("code1","Code: " + response.code());
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<UserResponse> call, Throwable t) {
//                            Log.d("code failure","Code: " + t.getMessage());
////                            Toast.makeText(null,"Failure: "+t.getMessage(),Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//                    });
                    Log.d("edit", "edit" + user.getUserName());
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("¿Quiere realizar el pago de "+user.getUserName() +" "+user.getLastName()+"?")
                        .setContentText("Plan: " + user.getPlan())
                        .setConfirmText("Pagar")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setCancelButton("Cancelar", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        }).show();
                }
            });
        }
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("Tipos de planes");
        String[] items = {"java","android","Data Structures","HTML","CSS"};
        int checkedItem = 1;
        alertDialog.setItems(items, null);
        alertDialog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Clicked on java", Toast.LENGTH_LONG).show();

            }
        });
        alertDialog.setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "ok", Toast.LENGTH_LONG).show();

//                                txtFrequency.setText(selectedFre);
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
//        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0:
//                        Toast.makeText(context, "Clicked on java", Toast.LENGTH_LONG).show();
//                        break;
//                    case 1:
//                        Toast.makeText(context, "Clicked on android", Toast.LENGTH_LONG).show();
//                        break;
//                    case 2:
//                        Toast.makeText(context, "Clicked on Data Structures", Toast.LENGTH_LONG).show();
//                        break;
//                    case 3:
//                        Toast.makeText(context, "Clicked on HTML", Toast.LENGTH_LONG).show();
//                        break;
//                    case 4:
//                        Toast.makeText(context, "Clicked on CSS", Toast.LENGTH_LONG).show();
//                        break;
//                }
//            }
//        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
    ArrayList<Plan> plans;
    RadioGroup radioPlansPay;
    RadioButton rdbtnPay;

    public void getPlanPay(){

        Request request = new Request();
        request.getPlans().enqueue(new Callback<ArrayList<Plan>>() {
            @Override
            public void onResponse(Call<ArrayList<Plan>> call, Response<ArrayList<Plan>> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    Plan cr = new Plan();
                    //cr.setUserName("Code: "+response.code());
                    plans.add(cr);
                    Toast.makeText(context, "onResponse is not successful", Toast.LENGTH_SHORT).show();
                    return;
                }
                plans = response.body();
                Log.d("Plans", "Plans: " + plans);
//                addRadioButtons();

                //userList.add(new UserResponse(userList.get(i).getIdUser(),userList.get(i).getUserName(),userList.get(i).getLastName(),userList.get(i).getEmail(),userList.get(i).getPassword(),userList.get(i).getUserType(),userList.get(i).getPlan()));
            }

            @Override
            public void onFailure(Call<ArrayList<Plan>> call, Throwable t) {
                Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
            }


        });
    }

    public void addRadioButtons(){
//                radioPlansPay.setOrientation(LinearLayout.VERTICAL);
        //
        for(int i=0; i<plans.size();i++) {
            rdbtnPay = new RadioButton(context);
            rdbtnPay.setId(View.generateViewId());
            //System.out.println("id "+rdbtn.getId());
            rdbtnPay.setText(plans.get(i).getPlan());
            rdbtnPay.setTextSize(22);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            rdbtnPay.setLayoutParams(params);
            radioPlansPay.addView(rdbtnPay);
        }

        rdbtnPay = new RadioButton(context);
        rdbtnPay.setId(View.generateViewId());
        rdbtnPay.setText("Ninguno");
        rdbtnPay.setTextSize(22);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        rdbtnPay.setLayoutParams(params);
        radioPlansPay.addView(rdbtnPay);


    }
}
