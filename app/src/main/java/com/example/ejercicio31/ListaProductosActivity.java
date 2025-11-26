package com.example.ejercicio31;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ejercicio31.ProductoAdapter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ListaProductosActivity extends AppCompatActivity {

    RecyclerView recycler;
    ApiCliente api;
    ArrayList<Producto> lista = new ArrayList<>();
    ProductoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        recycler = findViewById(R.id.recyclerProductos);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        api = new ApiCliente();

        cargarProductos();
    }

    private void cargarProductos() {
        api.get("read.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(ListaProductosActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resp = response.body().string();

                runOnUiThread(() -> {
                    try {
                        JSONArray array = new JSONArray(resp);
                        lista.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);

                            lista.add(new Producto(
                                    obj.getString("id"),
                                    obj.getString("nombre"),
                                    obj.getString("precio"),
                                    obj.getString("stock")
                            ));
                        }

                        adapter = new ProductoAdapter(ListaProductosActivity.this, lista);
                        recycler.setAdapter(adapter);

                    } catch (Exception ex) {
                        Toast.makeText(ListaProductosActivity.this, "Error JSON", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    // Eliminar llamado desde el Adapter
    public void eliminarProducto(String id) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);

            api.post("delete.php", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(ListaProductosActivity.this, "Error eliminando", Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(() -> {
                        Toast.makeText(ListaProductosActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                        cargarProductos(); // refrescar lista
                    });
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Error JSON", Toast.LENGTH_SHORT).show();
        }
    }
}
