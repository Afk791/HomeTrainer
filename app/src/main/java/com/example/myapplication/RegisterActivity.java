package com.example.myapplication;

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

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText editTextLogin;
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextRepeatPassword;
    private Button buttonRegister;
    private Button buttonBack;

    private ApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register_activity);
        initViews();
        apiClient = new ApiClient();


        buttonBack.setOnClickListener(v -> {
            finish();
        });

        // 📝 Кнопка "Зарегистрироваться"
        buttonRegister.setOnClickListener(v -> {
            String login = editTextLogin.getText().toString().trim();
            String password = editTextPassword.getText().toString();
            String repeatPassword = editTextRepeatPassword.getText().toString();

            // ✅ Валидация
            if (login.isEmpty()) {
                editTextLogin.setError("Введите логин");
                editTextLogin.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Введите пароль");
                editTextPassword.requestFocus();
                return;
            }
            if (repeatPassword.isEmpty()) {
                editTextRepeatPassword.setError("Повторите пароль");
                editTextRepeatPassword.requestFocus();
                return;
            }
            if (!password.equals(repeatPassword)) {
                editTextRepeatPassword.setError("Пароли не совпадают");
                editTextRepeatPassword.requestFocus();
                return;
            }
            if (password.length() < 5) {
                editTextPassword.setError("Минимум 5 символов");
                editTextPassword.requestFocus();
                return;
            }

            // 🔄 Показываем загрузку
            setButtonsEnabled(false);
            buttonRegister.setText("Регистрация...");

            JsonObject data = new JsonObject();
            data.addProperty("login", login);
            data.addProperty("password", password);
            data.addProperty("repeatPassword", repeatPassword);

            apiClient.sendRequest("register", data, new ApiClient.Callback() {
                @Override
                public void onSuccess(ApiResponse response) {
                    runOnUiThread(() -> {
                        setButtonsEnabled(true);
                        buttonRegister.setText("Зарегистрироваться");

                        Toast.makeText(RegisterActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                        if (response.isSuccess()) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.putExtra("name", login);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        setButtonsEnabled(true);
                        buttonRegister.setText("Зарегистрироваться");
                        Toast.makeText(RegisterActivity.this, "Ошибка: " + error, Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }

    // 🔍 Инициализация View
    private void initViews() {
        editTextLogin = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextSecondName);
        editTextRepeatPassword = findViewById(R.id.editTextThirdName);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonBack = findViewById(R.id.buttonBack);
    }

    private void setButtonsEnabled(boolean enabled) {
        buttonRegister.setEnabled(enabled);
        buttonBack.setEnabled(enabled);
        editTextLogin.setEnabled(enabled);
        editTextPassword.setEnabled(enabled);
        editTextRepeatPassword.setEnabled(enabled);
    }
}