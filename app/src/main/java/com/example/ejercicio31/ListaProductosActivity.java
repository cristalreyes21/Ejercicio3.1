package com.example.ejercicio31;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ListaProductosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ApiCliente api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        api = new ApiCliente(); // instancia de API aquí

        recyclerView = findViewById(R.id.recyclerProductos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Lista temporal de prueba (por ahora)
        List<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("1", "Guitarra", "1500", "5"));
        listaProductos.add(new Producto("2", "Piano", "5000", "2"));

        // ✅ Crear adapter RECIBIENDO api desde la Activity
        ProductoAdapter adapter = new ProductoAdapter(this, listaProductos, api);
        recyclerView.setAdapter(adapter);
    }
}
