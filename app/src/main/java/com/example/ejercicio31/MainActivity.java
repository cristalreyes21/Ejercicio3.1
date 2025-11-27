package com.example.ejercicio31;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.content.Intent;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText editNombre, editPrecio, editStock;
    TextView textResultado;
    Button btnCrear;
    ApiCliente api;

    String modo = "crear"; // ✅ Declararla con valor por defecto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = new ApiCliente();

        // Inicializar campos
        editNombre = findViewById(R.id.editNombre);
        editPrecio = findViewById(R.id.editPrecio);
        editStock = findViewById(R.id.editStock);
        textResultado = findViewById(R.id.textResultado);
        btnCrear = findViewById(R.id.btnCrear);

        // Obtener Intent y modo
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("modo")) {
            modo = intent.getStringExtra("modo");
        }

        String idProducto = intent != null ? intent.getStringExtra("id") : null;

        if ("actualizar".equals(modo)) {
            btnCrear.setText("Actualizar Producto");

            // Llenar campos con los datos existentes si están presentes
            if (intent != null) {
                editNombre.setText(intent.getStringExtra("nombre"));
                editPrecio.setText(intent.getStringExtra("precio"));
                editStock.setText(intent.getStringExtra("stock"));
            }
        }

        // Listener para crear o actualizar según el modo
        btnCrear.setOnClickListener(v -> {
            if ("actualizar".equals(modo)) {
                actualizarProducto(idProducto); // ✅ ID pasado correctamente
            } else {
                crearProducto();
            }
        });
    }

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

    private void actualizarProducto(String id) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", id);
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

    private void mostrar(String mensaje) {
        runOnUiThread(() -> textResultado.setText(mensaje));
    }
}
