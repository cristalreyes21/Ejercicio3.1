package com.example.ejercicio31;

public class Producto {
    private String id;
    private String nombre;
    private String precio;
    private String stock;

    public Producto(String id, String nombre, String precio, String stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // ✅ Getters necesarios para el Adapter
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getStock() {
        return stock;
    }
}
