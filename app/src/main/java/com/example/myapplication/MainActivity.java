package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.ApiClient;
import com.example.myapplication.ApiResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextLogin;
    private TextInputEditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;

    private ApiClient apiClient;

    private void initViews() {
        editTextLogin = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextSecondName);
        buttonLogin = findViewById(R.id.ButtonNext);
        buttonRegister = findViewById(R.id.buttonRegister);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        initViews();
        apiClient = new ApiClient();

        buttonLogin.setOnClickListener(v -> {
            String login = editTextLogin.getText().toString().trim();
            String password = editTextPassword.getText().toString();

            if (login.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            buttonLogin.setEnabled(false);
            buttonLogin.setText("Вход...");

            JsonObject data = new JsonObject();
            data.addProperty("login", login);
            data.addProperty("password", password);

            apiClient.sendRequest("auth", data, new ApiClient.Callback() {
                @Override
                public void onSuccess(ApiResponse response) {
                    runOnUiThread(() -> {
                        buttonLogin.setEnabled(true);
                        buttonLogin.setText("Войти");

                        Toast.makeText(MainActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        buttonLogin.setEnabled(true);
                        buttonLogin.setText("Войти");
                        Toast.makeText(MainActivity.this, "Ошибка: " + error, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}