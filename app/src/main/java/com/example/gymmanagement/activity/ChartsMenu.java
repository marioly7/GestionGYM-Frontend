package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;

public class ChartsMenu extends AppCompatActivity {

    Button barras, pastel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charts_menu);

        barras = findViewById(R.id.barras);
        pastel = findViewById(R.id.pastel);

        barras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartsMenu.this,BarChartPlans.class);
                startActivity(intent);
            }
        });

        pastel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChartsMenu.this,PieChartPlans.class);
                startActivity(intent);
            }
        });

    }
}
