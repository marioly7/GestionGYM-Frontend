package com.example.gymmanagement.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;
import com.example.gymmanagement.model.PaymentDetails;
import com.example.gymmanagement.model.PaymentReportResponse;
import com.example.gymmanagement.request.Request;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PaymentUser extends AppCompatActivity {
    Request request = new Request();
    TextView precio, plan, estado, fechaI, fechaL;
    float precioFloat;
    Button bolivianos, dolares, euros;
    Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user);

        userId = getIntent().getIntExtra("userId", 0);

        precio = findViewById(R.id.precio);
        plan = findViewById(R.id.tvPlan);
        estado = findViewById(R.id.tvEstado);
        fechaI = findViewById(R.id.fechaInscripcion);
        fechaL = findViewById(R.id.fechaLimite);

        bolivianos = findViewById(R.id.bolivianos);
        dolares = findViewById(R.id.dolares);
        euros = findViewById(R.id.euros);

        bolivianos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precio.setText(String.valueOf(precioFloat)+" Bs.");
            }
        });

        dolares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ingresosDolares = (float) (precioFloat * 0.15);
                precio.setText(String.valueOf(ingresosDolares)+" $");
            }
        });

        euros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ingresoEuros = (float) (precioFloat * 0.12);
                precio.setText(String.valueOf(ingresoEuros)+" €");
            }
        });

        getPaymentReportFromAPI();
    }

    private void getPaymentReportFromAPI() {
        final PaymentDetails[] paymentReportResponse = {new PaymentDetails()};
        request.getPaymentByUserIdDetails(userId).enqueue(new Callback<PaymentDetails>() {
            @Override
            public void onResponse(Call<PaymentDetails> call, Response<PaymentDetails> response) {
                paymentReportResponse[0] = response.body();
                precioFloat = paymentReportResponse[0].getPrice();

                precio.setText(String.valueOf(precioFloat)+" Bs.");

                if(paymentReportResponse[0].getStatus()==0){
                    estado.setText("Estado: No Pagado");
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    Date fecha = null;
                    try {
                        fecha = formato.parse(paymentReportResponse[0].getFecha());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar c = Calendar.getInstance();
                    c.setTime(fecha);
                    c.add(Calendar.DATE, 15);
                    fecha = c.getTime();
                    fechaL.setText(formato.format(fecha));
                }else{
                    estado.setText("Estado: Pagado");
                    fechaL.setText("");
                }

                plan.setText(paymentReportResponse[0].getPlan());

                fechaI.setText(paymentReportResponse[0].getFecha());

            }

            @Override
            public void onFailure(Call<PaymentDetails> call, Throwable t) {

            }
        });
    }
}