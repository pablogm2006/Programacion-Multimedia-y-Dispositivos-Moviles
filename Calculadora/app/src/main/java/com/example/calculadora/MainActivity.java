package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Esta clase es para una calculadora.
 *
 * Se encarga de mostrar los números y operadores en pantalla, y de controlar
 * qué pasa cuando el usuario toca los botones (números, operaciones, borrar, etc.).
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Este método se llama cuando se inicia la aplicación.
     * Aquí se conectan todos los botones y se define qué hacen cuando se tocan.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this); // Activa diseño a pantalla completa
        setContentView(R.layout.table); // Usa el diseño que contiene los botones

        // Muestra el texto de la operación o número actual
        TextView texto = findViewById(R.id.texto);

        // Botones de números
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

        // Botones de operaciones
        Button dividir = findViewById(R.id.dividir);
        Button multiplicar = findViewById(R.id.multiplicar);
        Button sumar = findViewById(R.id.sumar);
        Button restar = findViewById(R.id.restar);

        // Botones extra
        Button igual = findViewById(R.id.igual); // (todavía no hace nada)
        Button punto = findViewById(R.id.punto);
        Button ac = findViewById(R.id.ac); // borra todo
        Button c = findViewById(R.id.c);   // borra un solo carácter

        // Muestra "0" al inicio
        texto.setText("0");

        // Cuando se toca un número, se agrega al texto
        View.OnClickListener numberListener = v -> {
            Button b = (Button) v;
            String current = texto.getText().toString();
            String digit = b.getText().toString();

            if (current.equals("0")) {
                texto.setText(digit); // Reemplaza el 0
            } else {
                texto.setText(current + digit); // Agrega el número al final
            }
        };

        // Conectamos el listener a todos los botones de número
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

        // Cuando se toca un operador (+, -, *, /), lo agrega si no hay ya uno al final
        View.OnClickListener operatorListener = v -> {
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

        // Botón del punto decimal
        punto.setOnClickListener(v -> {
            String current = texto.getText().toString();

            // Parte el texto por los operadores y revisa la última parte
            String[] partes = current.split("[+\\-*/]");
            String ultimaParte = partes[partes.length - 1];

            // Solo agrega un punto si no hay uno ya en ese número
            if (!ultimaParte.contains(".")) {
                texto.setText(current + ".");
            }
        });

        // Botón AC: borra todo
        ac.setOnClickListener(v -> texto.setText("0"));

        // Botón C: borra el último carácter
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
