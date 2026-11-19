package com.example.myapplication;

import com.google.gson.JsonObject;

public class ApiRequest {
    private String command;
    private JsonObject data;

    public ApiRequest() {}

    public ApiRequest(String command, JsonObject data) {
        this.command = command;
        this.data = data;
    }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }
    public JsonObject getData() { return data; }
    public void setData(JsonObject data) { this.data = data; }
}