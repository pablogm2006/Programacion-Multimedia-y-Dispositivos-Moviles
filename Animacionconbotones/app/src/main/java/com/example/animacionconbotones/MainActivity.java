package com.example.animacionconbotones;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.linea);

    }
    protected  void onStart(){
    super.onStart();
    Button boton1 = findViewById(R.id.btnTranslate);
        Button boton2 = findViewById(R.id.btnRotate);
        Button boton3 = findViewById(R.id.btnDetener);
        TextView texto1 = findViewById(R.id.texto);
        Animation translate= AnimationUtils.loadAnimation(this,R.anim.botones);
        Animation rotate= AnimationUtils.loadAnimation(this,R.anim.n1);


        boton1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //texto1.setText("Entro en on click");
            texto1.setAnimation(translate);
        }
    });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texto1.setAnimation(rotate);
            }
        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                texto1.clearAnimation();
            }
        });
    }
}