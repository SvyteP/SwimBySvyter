package com.example.swimbysvyter.entity;

import org.json.JSONObject;

import lombok.Data;

@Data
public class Customer {
    private String login;
    private String email;
    private String token;

    public Customer() {}

    public Customer(String login, String email, String token) {
        this.login = login;
        this.email = email;
        this.token = token;
    }

    public Customer(JSONObject object) {
        this.login = object.optString("login");
        this.email = object.optString("email");
        this.token = object.optString("token");
    }
}
