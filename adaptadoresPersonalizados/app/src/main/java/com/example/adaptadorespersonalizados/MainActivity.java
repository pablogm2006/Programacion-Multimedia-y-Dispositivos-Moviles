package com.example.adaptadorespersonalizados;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        setContentView(R.layout.activity_main);

        Datos[] datos = new Datos[]{
                new Datos("Linea principal 1", "Linea inferior 1"),
                new Datos("Linea principal 2", "Linea inferior 2"),
                new Datos("Linea principal 3", "Linea inferior 3"),
                new Datos("Linea principal 4", "Linea inferior 4")};

        ListView listado = findViewById(R.id.listView);

        View miCabecera = getLayoutInflater().inflate(R.layout.cabecera,null);
        listado.addHeaderView(miCabecera);

        Adaptador miAdaptador = new Adaptador(this, datos);
        listado.setAdapter(miAdaptador);

        listado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((Datos)parent.getItemAtPosition(position)).getTexto1();
                Toast.makeText(MainActivity.this, "Elemento pulsado" + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });
    }
}