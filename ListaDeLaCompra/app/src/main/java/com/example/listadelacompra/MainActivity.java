package com.example.listadelacompra;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ShoppingListAdapter adapter;
    private List<Item> shoppingItems;
    private EditText editName, editQuantity;
    private Button btnAdd;
    private Spinner spinner;

    // Spinner entry simple
    private static class SpinnerEntry { String name; int img; SpinnerEntry(String n,int i){name=n;img=i;} }
    private List<SpinnerEntry> spinnerEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout with root id = main (para insets)
        setContentView(R.layout.activity_main);

        // Edge-to-edge padding (status/nav bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.list_view);
        editName = findViewById(R.id.edit_name);
        editQuantity = findViewById(R.id.edit_quantity);
        btnAdd = findViewById(R.id.btn_add);
        spinner = findViewById(R.id.spinner_items);

        shoppingItems = new ArrayList<>();

        // PREPOPULAMOS la lista con: Leche, Carne, Pizza
        shoppingItems.add(new Item("Leche", 1, R.drawable.leche));
        shoppingItems.add(new Item("Carne", 1, R.drawable.carne));
        shoppingItems.add(new Item("Pizza", 1, R.drawable.pizza));

        // Spinner entries (mismas opciones)
        spinnerEntries = new ArrayList<>();
        spinnerEntries.add(new SpinnerEntry("Leche", R.drawable.leche));
        spinnerEntries.add(new SpinnerEntry("Carne", R.drawable.carne));
        spinnerEntries.add(new SpinnerEntry("Pizza", R.drawable.pizza));

        // Adapter para el spinner con imagen y texto
        ArrayAdapter<SpinnerEntry> spinnerAdapter = new ArrayAdapter<SpinnerEntry>(this, R.layout.spinner_item, spinnerEntries){
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                return createSpinnerView(position, convertView, parent);
            }

            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                return createSpinnerView(position, convertView, parent);
            }

            private View createSpinnerView(int position, View convertView, ViewGroup parent){
                if(convertView==null) convertView = getLayoutInflater().inflate(R.layout.spinner_item, parent, false);
                ImageView iv = convertView.findViewById(R.id.spinner_image);
                TextView tv = convertView.findViewById(R.id.spinner_text);
                SpinnerEntry e = spinnerEntries.get(position);
                iv.setImageResource(e.img);
                tv.setText(e.name);
                return convertView;
            }

            @Override
            public long getItemId(int position) { return position; }

            @Override
            public SpinnerEntry getItem(int position) { return spinnerEntries.get(position); }
        };

        spinner.setAdapter(spinnerAdapter);

        adapter = new ShoppingListAdapter(this, shoppingItems);
        listView.setAdapter(adapter);

        // Eliminar pulsando el botón dentro del item
        adapter.setOnDeleteListener(new ShoppingListAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                if (position >= 0 && position < shoppingItems.size()) {
                    shoppingItems.remove(position);
                    adapter.notifyDataSetChanged();
                }
            }
        });

        // Añadir con botón: usa selección del spinner si el usuario no escribe nombre
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String qText = editQuantity.getText().toString().trim();

                // Si no pone nombre, toma el del spinner
                if (name.isEmpty()) {
                    SpinnerEntry sel = (SpinnerEntry) spinner.getSelectedItem();
                    if (sel != null) name = sel.name;
                }

                if (name.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Introduce un nombre", Toast.LENGTH_SHORT).show();
                    return;
                }

                int q = 1;
                try { q = Integer.parseInt(qText); } catch (Exception e){ }

                SpinnerEntry selected = (SpinnerEntry) spinner.getSelectedItem();
                int imageRes = selected != null ? selected.img : R.drawable.leche;

                shoppingItems.add(new Item(name, q, imageRes));
                adapter.notifyDataSetChanged();

                editName.setText("");
                editQuantity.setText("");
            }
        });

        // Registrar menú contextual para la lista (long-press)
        registerForContextMenu(listView);
    }

    // Menú contextual
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
            getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        try {
            info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        } catch (ClassCastException e) {
            return super.onContextItemSelected(item);
        }

        int position = info.position;
        if (position < 0 || position >= shoppingItems.size()) {
            return super.onContextItemSelected(item);
        }

        Item selectedItem = shoppingItems.get(position);

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete) {
            // Eliminar el elemento seleccionado
            shoppingItems.remove(position);
            adapter.notifyDataSetChanged();
            return true;
        } else if (itemId == R.id.menu_add) {
            // Rellenar campos con datos del elemento para duplicar/editar
            editName.setText(selectedItem.getName());
            editQuantity.setText(String.valueOf(selectedItem.getQuantity()));
            Toast.makeText(this, "Rellena los campos y pulsa Añadir para duplicar/editar", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onContextItemSelected(item);
    }
}