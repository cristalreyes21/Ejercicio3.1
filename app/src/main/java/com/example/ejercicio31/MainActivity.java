package com.example.ejercicio31;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;
import org.json.JSONObject;

import okhttp3.*;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText editId, editNombre, editPrecio, editStock;
    TextView textResultado;
    ApiClient api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = new ApiClient();

        editId = findViewById(R.id.editId);
        editNombre = findViewById(R.id.editNombre);
        editPrecio = findViewById(R.id.editPrecio);
        editStock = findViewById(R.id.editStock);
        textResultado = findViewById(R.id.textResultado);

        findViewById(R.id.btnCrear).setOnClickListener(v -> crearProducto());
        findViewById(R.id.btnLeer).setOnClickListener(v -> leerProductos());
        findViewById(R.id.btnActualizar).setOnClickListener(v -> actualizarProducto());
        findViewById(R.id.btnEliminar).setOnClickListener(v -> eliminarProducto());
    }

    // CREAR
    private void crearProducto() {
        try {
            JSONObject json = new JSONObject();
            json.put("nombre", editNombre.getText().toString());
            json.put("precio", editPrecio.getText().toString());
            json.put("stock", editStock.getText().toString());

            api.post("create.php", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mostrar("Error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mostrar(response.body().string());
                }
            });

        } catch (Exception e) {
            mostrar("Error JSON: " + e.getMessage());
        }
    }

    // LEER
    private void leerProductos() {
        api.get("read.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mostrar("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mostrar(response.body().string());
            }
        });
    }

    // ACTUALIZAR
    private void actualizarProducto() {
        try {
            JSONObject json = new JSONObject();
            json.put("id", editId.getText().toString());
            json.put("nombre", editNombre.getText().toString());
            json.put("precio", editPrecio.getText().toString());
            json.put("stock", editStock.getText().toString());

            api.post("update.php", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mostrar("Error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mostrar(response.body().string());
                }
            });

        } catch (Exception e) {
            mostrar("Error JSON: " + e.getMessage());
        }
    }

    // ELIMINAR
    private void eliminarProducto() {
        try {
            JSONObject json = new JSONObject();
            json.put("id", editId.getText().toString());

            api.post("delete.php", json.toString(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mostrar("Error: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    mostrar(response.body().string());
                }
            });

        } catch (Exception e) {
            mostrar("Error JSON: " + e.getMessage());
        }
    }

    // MOSTRAR RESULTADO EN UI
    private void mostrar(String mensaje) {
        runOnUiThread(() -> textResultado.setText(mensaje));
    }
}
