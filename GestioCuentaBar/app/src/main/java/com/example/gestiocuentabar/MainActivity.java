package com.example.gestiocuentabar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
        import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etTotal;
    CheckBox cbPropina;
    SeekBar seekBarPropina;
    TextView tvPorcentajePropina, tvResultado;
    RadioGroup rgPago;
    RadioButton rbEfectivo, rbTarjeta;
    RatingBar rbCalificacion;
    Button btnCalcular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los elementos
        etTotal = findViewById(R.id.etTotal);
        cbPropina = findViewById(R.id.cbPropina);
        seekBarPropina = findViewById(R.id.seekBarPropina);
        tvPorcentajePropina = findViewById(R.id.tvPorcentajePropina);
        rgPago = findViewById(R.id.rgPago);
        rbEfectivo = findViewById(R.id.rbEfectivo);
        rbTarjeta = findViewById(R.id.rbTarjeta);
        rbCalificacion = findViewById(R.id.rbCalificacion);
        btnCalcular = findViewById(R.id.btnCalcular);
        tvResultado = findViewById(R.id.tvResultado);

        // Actualizar porcentaje de propina
        seekBarPropina.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPorcentajePropina.setText("Propina: " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // Botón calcular
        btnCalcular.setOnClickListener(v -> {
            String totalText = etTotal.getText().toString();

            // Validaciones
            if(totalText.isEmpty()){
                tvResultado.setTextColor(Color.RED);
                tvResultado.setText("Por favor, ingrese el total de la cuenta");
                return;
            }

            double total;
            try {
                total = Double.parseDouble(totalText);
            } catch (NumberFormatException e){
                tvResultado.setTextColor(Color.RED);
                tvResultado.setText("Formato de número inválido");
                return;
            }

            if(total <= 0){
                tvResultado.setTextColor(Color.RED);
                tvResultado.setText("El total debe ser mayor que 0");
                return;
            }

            // Calcular propina
            double porcentajePropina = cbPropina.isChecked() ? seekBarPropina.getProgress() : 0;
            double propina = total * porcentajePropina / 100.0;
            double totalConPropina = total + propina;

            // Método de pago
            int metodoId = rgPago.getCheckedRadioButtonId();
            String metodoPago;
            if(metodoId == rbEfectivo.getId()){
                metodoPago = "Efectivo";
            } else if(metodoId == rbTarjeta.getId()){
                metodoPago = "Tarjeta";
            } else {
                metodoPago = "No seleccionado";
            }

            // Calificación
            float calificacion = rbCalificacion.getRating();

            // Mostrar resultado
            tvResultado.setTextColor(Color.BLACK);
            tvResultado.setText("Total: " + String.format("%.2f", totalConPropina) +
                    "\nMétodo de pago: " + metodoPago +
                    "\nCalificación del servicio: " + calificacion + " estrellas");
        });
    }
}
