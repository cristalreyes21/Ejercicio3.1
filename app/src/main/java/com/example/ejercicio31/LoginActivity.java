package com.example.ejercicio31;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editApiKey;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editApiKey = findViewById(R.id.editApiKey);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(v -> {
            String key = editApiKey.getText().toString().trim();

            if (key.isEmpty()) {
                Toast.makeText(this, "Ingrese la API KEY", Toast.LENGTH_SHORT).show();
                return;
            }

            if (key.equals("ecc367d49ac72ffb51ab5375f0eead4e")) {
                // Guardamos la API KEY
                ApiCliente.API_KEY = key;

                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "API KEY incorrecta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
