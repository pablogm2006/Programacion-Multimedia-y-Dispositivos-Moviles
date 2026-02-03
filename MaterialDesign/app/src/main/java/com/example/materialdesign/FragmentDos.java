package com.example.materialdesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.activity.EdgeToEdge;

/**
 * Fragment que muestra la calculadora.
 * Solo se visualiza, sin evaluar expresiones.
 */
public class FragmentDos extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflamos el layout de la calculadora (el que ya tienes)
        View view = inflater.inflate(R.layout.fragment_dos, container, false);

        // Pantalla de la calculadora
        TextView texto = view.findViewById(R.id.texto);

        // Botones de números
        Button num0 = view.findViewById(R.id.numero0);
        Button num1 = view.findViewById(R.id.numero1);
        Button num2 = view.findViewById(R.id.numero2);
        Button num3 = view.findViewById(R.id.numero3);
        Button num4 = view.findViewById(R.id.numero4);
        Button num5 = view.findViewById(R.id.numero5);
        Button num6 = view.findViewById(R.id.numero6);
        Button num7 = view.findViewById(R.id.numero7);
        Button num8 = view.findViewById(R.id.numero8);
        Button num9 = view.findViewById(R.id.numero9);

        // Botones de operaciones
        Button dividir = view.findViewById(R.id.dividir);
        Button multiplicar = view.findViewById(R.id.multiplicar);
        Button sumar = view.findViewById(R.id.sumar);
        Button restar = view.findViewById(R.id.restar);

        // Botones extra
        Button igual = view.findViewById(R.id.igual);
        Button punto = view.findViewById(R.id.punto);
        Button ac = view.findViewById(R.id.ac);
        Button c = view.findViewById(R.id.c);

        // Inicializa pantalla
        texto.setText("0");

        // Listener números (igual que en tu código original)
        View.OnClickListener numberListener = v -> {
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

        // Listener operadores (igual que tu código)
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

        // Botón punto
        punto.setOnClickListener(v -> {
            String current = texto.getText().toString();
            String[] partes = current.split("[+\\-*/]");
            String ultimaParte = partes[partes.length - 1];

            if (!ultimaParte.contains(".")) {
                texto.setText(current + ".");
            }
        });

        // Botón AC
        ac.setOnClickListener(v -> texto.setText("0"));

        // Botón C
        c.setOnClickListener(v -> {
            String current = texto.getText().toString();
            if (current.length() > 1) {
                texto.setText(current.substring(0, current.length() - 1));
            } else {
                texto.setText("0");
            }
        });

        // Igual → solo muestra lo que hay, no evalúa nada
        igual.setOnClickListener(v -> {
            // No hacemos nada
        });

        return view;
    }
}
