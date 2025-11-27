package com.example.ejercicio31;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ListaProductosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiCliente api;
    ProductoAdapter adapter;
    List<Producto> listaProductos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        api = new ApiCliente(); // instancia API

        recyclerView = findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new ProductoAdapter(this, listaProductos, api);
        recyclerView.setAdapter(adapter);

        // Cargar datos desde backend/API que consulta tu base de datos
        cargarProductos();
    }

    private void cargarProductos() {
        api.get("read.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(ListaProductosActivity.this, "Error de conexión con servidor ❌", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String jsonData = response.body().string();
                    JSONArray jsonArray = new JSONArray(jsonData);

                    listaProductos.clear(); // limpiar lista actual

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        // ⚠ Ajusta las claves si tu API backend usa otros nombres
                        listaProductos.add(new Producto(
                                obj.getString("id"),
                                obj.getString("nombre"),
                                obj.getString("precio"),
                                obj.getString("stock")
                        ));
                    }

                    runOnUiThread(() -> adapter.notifyDataSetChanged());

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(ListaProductosActivity.this, "Error al procesar JSON ❌", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}
