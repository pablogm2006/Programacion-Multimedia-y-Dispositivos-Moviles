package com.example.baseadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.baseadapter.ListItem;
import com.example.baseadapter.R;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<ListItem> items;
    private int selectedPosition = -1; // Para seleccionar un único botón de radio 2 usages

    public CustomAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.ItemImage);
        TextView titleTextView = convertView.findViewById(R.id.itemTitle);
        TextView contentTextView = convertView.findViewById(R.id.itemcontent);
        RadioButton radioButton = convertView.findViewById(R.id.itemRadioButton);
        ListItem item = items.get(position);
        imageView.setImageResource(item.getImageResid());
        titleTextView.setText(item.getTitle());
        contentTextView.setText(item.getContent());
// Gestionar el estado del botón de radio
        radioButton.setChecked(position == selectedPosition);
        radioButton.setOnClickListener(v -> {
        });
        selectedPosition = position;
        notifyDataSetChanged();
        return convertView;
    }
}