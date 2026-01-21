package com.example.tema4edittext;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.line);
    String [] opciones = {"op1", "op2", "op3", "op4"};
        AutoCompleteTextView textoLeido = findViewById(R.id.autoComplete);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,opciones);
        textoLeido.setAdapter(adaptador);
        Spinner miSpinner = (Spinner) findViewById(R.id.miSpinner);
        String[] valores = {"CR7", "Messi", "Mbappe"};
        miSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
        miSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                System.out.println(" has seleccionadoel valor: " + adapterView.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                System.out.println("NO has seleccionado nada");
            }
        });
        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.isSelected()){

                }
            }
        });
        CheckBox checkbox1 = findViewById(R.id.checkbox1);
        TextView text = findViewById(R.id.text);
        Button boton1 = findViewById(R.id.boton1);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int isSelectedID = radioGroup.getCheckedRadioButtonId();
            if (isSelectedID != -1 ){
                RadioButton radioButton = findViewById(isSelectedID);
                String selectedOpcion = radioButton.getText().toString();
                text.setText("Seleccionado: " + selectedOpcio);
            }
            }
        });
       /* checkbox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    text.setText("1");
                }else{

                }
            }
        });*/


    }
}