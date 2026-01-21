package com.example.gestiondeeventos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorEvento extends RecyclerView.Adapter<AdaptadorEvento.EventViewHolder> {

    List<Evento> events;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Evento evento);
    }

    public AdaptadorEvento(List<Evento> events, OnItemClickListener listener) {
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.evento, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Evento e = events.get(position);
        holder.tvName.setText(e.nombre);
        holder.tvDateTime.setText(e.fechaHora);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(e));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateTime;
        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}
