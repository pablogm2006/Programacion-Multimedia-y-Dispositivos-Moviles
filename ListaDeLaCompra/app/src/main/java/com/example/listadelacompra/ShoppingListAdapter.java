package com.example.listadelacompra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> items;
    private LayoutInflater inflater;

    // Interfaz para escuchar el botón eliminar
    public interface OnDeleteListener {
        void onDelete(int position);
    }

    private OnDeleteListener deleteListener;

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.deleteListener = listener;
    }

    public ShoppingListAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
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

    static class ViewHolder {
        ImageView image;
        TextView name;
        TextView quantity;
        Button btnDelete;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_shopping, parent, false);
            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.item_image);
            holder.name = convertView.findViewById(R.id.item_name);
            holder.quantity = convertView.findViewById(R.id.item_quantity);
            holder.btnDelete = convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener item actual
        Item item = items.get(position);

        // Setear valores
        holder.image.setImageResource(item.getImageResId());
        holder.name.setText(item.getName());
        holder.quantity.setText("Cantidad: " + item.getQuantity());

        // Botón eliminar
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onDelete(position);
                }
            }
        });

        return convertView;
    }
}