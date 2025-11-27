package com.example.ejercicio31;
import okhttp3.*;
public class ApiCliente {

    public static String API_KEY = "ecc367d49ac72ffb51ab5375f0eead4e";
    private static final String BASE_URL = "http://192.168.1.42:8080/api_crud/";

    OkHttpClient client = new OkHttpClient();

    public void post(String endpoint, String json, Callback callback) {
        RequestBody body = RequestBody.create(
                json, MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .post(body)
                .addHeader("API_KEY", API_KEY)
                .build();

        client.newCall(request).enqueue(callback);
    }

    public void get(String endpoint, Callback callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + endpoint)
                .addHeader("API_KEY", API_KEY)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
