package com.example.myapplication;

import com.google.gson.JsonObject;

public class ApiResponse {
    private boolean success;
    private String message;
    private JsonObject data;

    public ApiResponse() {}

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public JsonObject getData() { return data; }
    public boolean hasData() { return data != null && !data.isJsonNull(); }
}