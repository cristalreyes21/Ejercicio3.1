package com.example.ejercicio31;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText editUsuario, editPassword, editApiKey;
    Button btnIngresar;
    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferencias = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);

        // Entrar directo si hay sesión
        if (preferencias.getBoolean("sesionIniciada", false)) {
            startActivity(new Intent(this, MenuActivity.class));
            finish();
            return;
        }

        editUsuario = findViewById(R.id.editUsuario);
        editPassword = findViewById(R.id.editPassword);
        editApiKey = findViewById(R.id.editApiKey);
        btnIngresar = findViewById(R.id.btnIngresar);

        //login del servidor
        btnIngresar.setOnClickListener(v -> validarLogin());
    }

    private void validarLogin() {
        String usuario = editUsuario.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String apiKey = editApiKey.getText().toString().trim();

        if (usuario.isEmpty() || password.isEmpty() || apiKey.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Petición a PHP
        RequestBody formBody = new FormBody.Builder()
                .add("usuario", usuario)
                .add("password", password)
                .add("apiKey", apiKey)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.1.35:8080/api_crud/login.php")
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(LoginActivity.this, "Error conexión: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String respuesta = response.body().string();
                    JSONObject json = new JSONObject(respuesta);
                    boolean success = json.getBoolean("success");
                    String mensaje = json.getString("mensaje");

                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, mensaje, Toast.LENGTH_SHORT).show();
                        if (success) {
                            GuardarSesion(usuario, apiKey);
                            ApiCliente.API_KEY = apiKey;
                            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                            finish();
                        }
                    });

                } catch (Exception e) {
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, "Error servidor: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
            }
        });
    }

    private void GuardarSesion(String usuario, String key) {
        preferencias.edit().putBoolean("sesionIniciada", true)
                .putString("usuario", usuario)
                .putString("apiKey", key)
                .apply();
    }
}
