package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.table);

        TextView texto = findViewById(R.id.texto);

        Button num0 = findViewById(R.id.numero0);
        Button num1 = findViewById(R.id.numero1);
        Button num2 = findViewById(R.id.numero2);
        Button num3 = findViewById(R.id.numero3);
        Button num4 = findViewById(R.id.numero4);
        Button num5 = findViewById(R.id.numero5);
        Button num6 = findViewById(R.id.numero6);
        Button num7 = findViewById(R.id.numero7);
        Button num8 = findViewById(R.id.numero8);
        Button num9 = findViewById(R.id.numero9);

        Button dividir = findViewById(R.id.dividir);
        Button multiplicar = findViewById(R.id.multiplicar);
        Button sumar = findViewById(R.id.sumar);
        Button restar = findViewById(R.id.restar);
        Button igual = findViewById(R.id.igual);
        Button punto = findViewById(R.id.punto);
        Button ac = findViewById(R.id.ac);
        Button c = findViewById(R.id.c);

        texto.setText("0");

        android.view.View.OnClickListener numberListener = v -> {
            Button b = (Button) v;
            String current = texto.getText().toString();
            String digit = b.getText().toString();


            if (current.equals("0")) {
                texto.setText(digit);
            } else {
                texto.setText(current + digit);
            }
        };


        num0.setOnClickListener(numberListener);
        num1.setOnClickListener(numberListener);
        num2.setOnClickListener(numberListener);
        num3.setOnClickListener(numberListener);
        num4.setOnClickListener(numberListener);
        num5.setOnClickListener(numberListener);
        num6.setOnClickListener(numberListener);
        num7.setOnClickListener(numberListener);
        num8.setOnClickListener(numberListener);
        num9.setOnClickListener(numberListener);

        android.view.View.OnClickListener operatorListener = v -> {
            Button b = (Button) v;
            String current = texto.getText().toString();
            String operator = b.getText().toString();


            if (!current.isEmpty()) {
                char last = current.charAt(current.length() - 1);
                if ("+-*/.".indexOf(last) == -1) {
                    texto.setText(current + operator);
                }
            }
        };
        dividir.setOnClickListener(operatorListener);
        multiplicar.setOnClickListener(operatorListener);
        sumar.setOnClickListener(operatorListener);
        restar.setOnClickListener(operatorListener);

        punto.setOnClickListener(v -> {
            String current = texto.getText().toString();

            String[] partes = current.split("[+\\-*/]");
            String ultimaParte = partes[partes.length - 1];

            if (!ultimaParte.contains(".")) {
                texto.setText(current + ".");
            }
        });
        ac.setOnClickListener(v -> texto.setText("0"));


        c.setOnClickListener(v -> {
            String current = texto.getText().toString();
            if (current.length() > 1) {
                texto.setText(current.substring(0, current.length() - 1));
            } else {
                texto.setText("0");
            }
        });
    }
}