package com.example.gymmanagement.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gymmanagement.R;
import org.w3c.dom.Text;

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //ANIMACIONES
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);

        TextView de = findViewById(R.id.tvDe);
        TextView titulo = findViewById(R.id.titulo);
        ImageView logo = findViewById(R.id.ivLogo);
        TextView marioly = findViewById(R.id.tvMarioly);
        TextView nap = findViewById(R.id.tvNapoleon);
        TextView may = findViewById(R.id.tvMayumi);

        titulo.setAnimation(animation2);
        de.setAnimation(animation2);
        marioly.setAnimation(animation2);
        nap.setAnimation(animation2);
        may.setAnimation(animation2);
        logo.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}
