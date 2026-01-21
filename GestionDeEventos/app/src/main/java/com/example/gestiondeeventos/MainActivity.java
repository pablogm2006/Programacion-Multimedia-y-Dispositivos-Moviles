package com.example.gestiondeeventos;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "canal_demo";
    private static final int REQ_POST_NOTIFICATIONS = 100;

    List<Evento> eventos = new ArrayList<>();
    AdaptadorEvento adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        RecyclerView recycler = findViewById(R.id.recyclerEvents);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adaptador = new AdaptadorEvento(eventos, evento -> showCustomToast(evento));
        recycler.setAdapter(adaptador);

        Button btnAdd = findViewById(R.id.btnAddEvent);
        btnAdd.setOnClickListener(v -> openCreateEventDialog());

        loadEvents(); // cargar eventos guardados
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveEvents(); // guardar eventos al minimizar
    }

    private void openCreateEventDialog() {
        View view = getLayoutInflater().inflate(R.layout.eventodialogo, null);
        EditText etName = view.findViewById(R.id.etEventName);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Crear evento")
                .setView(view)
                .setPositiveButton("Siguiente", (d, which) -> {
                    String name = etName.getText().toString().trim();
                    if (name.isEmpty()) return;

                    pickDateTime(name);
                })
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.show();
    }

    private void pickDateTime(String name) {
        Calendar c = Calendar.getInstance();

        DatePickerDialog datePicker = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {

                    TimePickerDialog timePicker = new TimePickerDialog(this,
                            (timeView, hourOfDay, minute) -> {
                                String fechaHora = dayOfMonth + "/" + (month + 1) + "/" + year +
                                        " " + hourOfDay + ":" + (minute < 10 ? "0" + minute : minute);

                                Evento evento = new Evento(name, fechaHora);
                                eventos.add(evento);
                                adaptador.notifyDataSetChanged();

                                // Notificación al crear el evento
                                if (canPostNotifications()) {
                                    showNotification(evento);
                                } else {
                                    requestPostNotificationsPermission();
                                }

                            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                    timePicker.show();

                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePicker.show();
    }

    private void showCustomToast(Evento evento) {
        View view = getLayoutInflater().inflate(R.layout.toast_custom, null);
        TextView tv = view.findViewById(R.id.tvToast);
        tv.setText(evento.nombre + "\n" + evento.fechaHora);

        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }


    private boolean canPostNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPostNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    REQ_POST_NOTIFICATIONS
            );
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Canal Demo",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            nm.createNotificationChannel(channel);
        }
    }

    private void showNotification(Evento evento) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, flags);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle(evento.nombre)
                        .setContentText(evento.fechaHora)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

        NotificationManager nm =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify((int) System.currentTimeMillis(), builder.build());
    }


    private void saveEvents() {
        StringBuilder sb = new StringBuilder();
        for (Evento e : eventos) {
            sb.append(e.nombre).append("|").append(e.fechaHora).append(";");
        }
        getSharedPreferences("EVENTS_PREF", MODE_PRIVATE)
                .edit()
                .putString("EVENT_LIST", sb.toString())
                .apply();
    }

    private void loadEvents() {
        String saved = getSharedPreferences("EVENTS_PREF", MODE_PRIVATE)
                .getString("EVENT_LIST", "");

        if (!saved.isEmpty()) {
            String[] items = saved.split(";");
            eventos.clear();

            for (String item : items) {
                if (item.isEmpty()) continue;
                String[] parts = item.split("\\|");
                if (parts.length == 2) {
                    eventos.add(new Evento(parts[0], parts[1]));
                }
            }
        }

        if (adaptador != null) {
            adaptador.notifyDataSetChanged();
        }
    }
}
