package com.example.myapplication;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ApiClient {
    private static final String SERVER_HOST = "10.0.2.2";
    private static final int SERVER_PORT = 7000;

    private static final String TAG = "ApiClient";
    private final Gson gson = new Gson();

    /**
     * Асинхронная отправка запроса (безопасно для UI)
     */
    public interface Callback {
        void onSuccess(ApiResponse response);
        void onError(String error);
    }

    public void sendRequest(String command, JsonObject data, Callback callback) {
        // Выполняем в отдельном потоке (не блокируем UI)
        new Thread(() -> {
            Socket socket = null;
            try {
                Log.d(TAG, "🔗 Connecting to " + SERVER_HOST + ":" + SERVER_PORT);

                // Подключение
                socket = new Socket(SERVER_HOST, SERVER_PORT);
                Log.d(TAG, "✅ Connected");

                // Потоки
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Создаём и отправляем запрос
                ApiRequest request = new ApiRequest(command, data);
                String jsonRequest = gson.toJson(request);
                Log.d(TAG, "📤 Sending: " + jsonRequest);

                out.println(jsonRequest);

                // Читаем ответ
                String jsonResponse = in.readLine();
                Log.d(TAG, "📥 Received: " + jsonResponse);

                // Парсим ответ
                ApiResponse response = gson.fromJson(jsonResponse, ApiResponse.class);

                // Возвращаем результат в callback
                if (response != null && response.isSuccess()) {
                    callback.onSuccess(response);
                } else {
                    callback.onError(response != null ? response.getMessage() : "Empty response");
                }

            } catch (IOException e) {
                Log.e(TAG, "❌ Connection error: " + e.getMessage());
                callback.onError("Connection error: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "❌ Processing error: " + e.getMessage());
                callback.onError("Error: " + e.getMessage());
            } finally {
                // Закрываем сокет
                if (socket != null && !socket.isClosed()) {
                    try { socket.close(); } catch (IOException ignored) {}
                }
            }
        }).start();
    }
}