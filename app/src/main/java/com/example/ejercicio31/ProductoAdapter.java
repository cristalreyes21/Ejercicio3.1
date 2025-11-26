package com.example.ejercicio31;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    Context context;
    ArrayList<Producto> lista;

    public ProductoAdapter(Context context, ArrayList<Producto> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto p = lista.get(position);

        holder.textNombre.setText(p.nombre);
        holder.textPrecio.setText("Precio: L. " + p.precio);
        holder.textStock.setText("Stock: " + p.stock);

        // EDITAR
        holder.btnEditar.setOnClickListener(v -> {
            // Abrir la pantalla de actualización
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("modo", "actualizar");
            intent.putExtra("id", p.id());
            intent.putExtra("nombre", p.nombre());
            intent.putExtra("precio", p.precio());
            intent.putExtra("stock", p.stock());
            context.startActivity(intent);
        });

        // ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            if (context instanceof ListaProductosActivity) {
                ((ListaProductosActivity) context).eliminarProducto(p.id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textNombre, textPrecio, textStock;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textPrecio = itemView.findViewById(R.id.textPrecio);
            textStock = itemView.findViewById(R.id.textStock);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}
