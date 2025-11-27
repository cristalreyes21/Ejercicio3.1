package com.example.ejercicio31;
import com.example.ejercicio31.Producto;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONObject;
import java.util.ArrayList;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private Context context;
    private List<Producto> lista;

    public ProductoAdapter(Context context, List<Producto> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Producto p = lista.get(position);

        holder.textNombre.setText(p.getNombre());
        holder.textPrecio.setText(p.getPrecio());
        holder.textStock.setText(p.getStock());

        // ✅ BOTÓN EDITAR
        holder.btnEditar.setOnClickListener(v -> {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("modo", "actualizar");
            i.putExtra("id", p.getId());
            i.putExtra("nombre", p.getNombre());
            i.putExtra("precio", p.getPrecio());
            i.putExtra("stock", p.getStock());
            context.startActivity(i);
        });

        // ✅ BOTÓN ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            eliminarProducto(p.getId());
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textNombre, textPrecio, textStock;
        Button btnEditar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textNombre = itemView.findViewById(R.id.textNombre);
            textPrecio = itemView.findViewById(R.id.textPrecio);
            textStock  = itemView.findViewById(R.id.textStock);

            btnEditar  = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    private void eliminarProducto(String id) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);

            api.post("delete.php", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ((AppCompatActivity) context).runOnUiThread(() ->
                            Toast.makeText(context, "Error eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ((AppCompatActivity) context).runOnUiThread(() -> {
                        Toast.makeText(context, "Eliminado ✅", Toast.LENGTH_SHORT).show();
                    });
                }
            });

        } catch(Exception e){
            Toast.makeText(context, "Error JSON eliminar", Toast.LENGTH_SHORT).show();
        }
    }


}

