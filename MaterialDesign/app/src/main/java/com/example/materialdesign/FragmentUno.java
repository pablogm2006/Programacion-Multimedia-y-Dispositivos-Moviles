package com.example.materialdesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FragmentUno extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_uno, container, false);

        TextInputLayout inputLayout = view.findViewById(R.id.inputLayout);
        TextInputEditText editText = view.findViewById(R.id.editText);

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (editText.getText().toString().isEmpty()) {
                    inputLayout.setError("Campo obligatorio");
                } else {
                    inputLayout.setError(null);
                }
            }
        });

        Button btnEnviar = view.findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(v -> {
            String mensaje = editText.getText().toString().trim();
            if (mensaje.isEmpty()) {
                mensaje = "Campo vacío";
            }
            Snackbar.make(v, "Mensaje: " + mensaje, Snackbar.LENGTH_LONG).show();
        });

        return view;
    }
}
