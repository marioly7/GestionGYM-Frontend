package com.example.gymmanagement.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.gymmanagement.R;
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

import java.util.ArrayList;

public class PaymentReportActivity extends AppCompatActivity {
    Request request = new Request();
    TextView ingresos, noPagado, pagado;
    float pagadoPor, noPagadoPor, ingresosFloat;
    Button bolivianos, dolares, euros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_report);

        ingresos = findViewById(R.id.ingresos);
        noPagado = findViewById(R.id.pagosNoRealizados);
        pagado = findViewById(R.id.pagosRealizados);

        bolivianos = findViewById(R.id.bolivianos);
        dolares = findViewById(R.id.dolares);
        euros = findViewById(R.id.euros);

        bolivianos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingresos.setText(String.valueOf(ingresosFloat)+" Bs.");
            }
        });

        dolares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ingresosDolares = (float) (ingresosFloat * 0.15);
                ingresos.setText(String.valueOf(ingresosDolares)+" $");
            }
        });

        euros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float ingresoEuros = (float) (ingresosFloat * 0.12);
                ingresos.setText(String.valueOf(ingresoEuros)+" €");
            }
        });

        getPaymentReportFromAPI();
    }

    private void getPaymentReportFromAPI() {
        final PaymentReportResponse[] paymentReportResponse = {new PaymentReportResponse()};
        request.getPaymentReport().enqueue(new Callback<PaymentReportResponse>() {
            @Override
            public void onResponse(Call<PaymentReportResponse> call, Response<PaymentReportResponse> response) {
                paymentReportResponse[0] = response.body();
                pagadoPor = (paymentReportResponse[0].getPorcentajePagado()/(paymentReportResponse[0].getPorcentajeNoPagado()+paymentReportResponse[0].getPorcentajePagado()))*100;
                noPagadoPor = (paymentReportResponse[0].getPorcentajeNoPagado()/(paymentReportResponse[0].getPorcentajeNoPagado()+paymentReportResponse[0].getPorcentajePagado()))*100;
                ingresosFloat = paymentReportResponse[0].getIngresos();

                ingresos.setText(String.valueOf(ingresosFloat)+" Bs.");
                noPagado.setText(String.valueOf(noPagadoPor)+" %");
                pagado.setText(String.valueOf(pagadoPor)+" %");

                createChart();
            }

            @Override
            public void onFailure(Call<PaymentReportResponse> call, Throwable t) {

            }
        });
    }

    private void createChart() {

        PieChart pieChart = findViewById(R.id.pieChartPayments);

        ArrayList<PieEntry> plans = new ArrayList<>();

        plans.add(new PieEntry(pagadoPor,"Pagos Realizados"));
        plans.add(new PieEntry(noPagadoPor,"Pagos No Realizados"));


        PieDataSet pieDataSet = new PieDataSet(plans,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Reporte de Pagos");
        pieChart.animate();
        pieChart.invalidate();

    }
}