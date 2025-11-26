package com.example.ejercicio31;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    Button btnAgregar, btnListar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnListar = findViewById(R.id.btnListar);

        btnAgregar.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, MainActivity.class))
        );

        btnListar.setOnClickListener(v ->
                startActivity(new Intent(MenuActivity.this, ListaProductosActivity.class))
        );
    }
}
