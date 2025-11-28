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
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private Context context;
    private List<Producto> lista;
    private ApiCliente api;


    public ProductoAdapter(Context context, List<Producto> lista, ApiCliente api) {
        this.context = context;
        this.lista = lista;
        this.api = api;
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

        // BOTÓN EDITAR
        holder.btnEditar.setOnClickListener(v -> {
            Intent i = new Intent(context, MainActivity.class);
            i.putExtra("modo", "actualizar");
            i.putExtra("id", p.getId());
            i.putExtra("nombre", p.getNombre());
            i.putExtra("precio", p.getPrecio());
            i.putExtra("stock", p.getStock());
            context.startActivity(i);
        });

        // BOTÓN ELIMINAR
        holder.btnEliminar.setOnClickListener(v -> {
            eliminarProducto(p.getId(), position, position);

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

    // Función eliminar
    private void eliminarProducto(String id, int position, int index) {
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


                        lista.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, lista.size());
                    });
                }
            });

        } catch(Exception e){
            Toast.makeText(context, "Error JSON eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
