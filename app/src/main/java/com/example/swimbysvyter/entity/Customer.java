package com.example.swimbysvyter.entity;

import org.json.JSONObject;

import lombok.Data;

@Data
public class Customer {
    private String name;
    private String email;
    private String token;

    public Customer() {}

    public Customer(String name, String email, String token) {
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public Customer(JSONObject object) {
        this.name = object.optString("login");
        this.email = object.optString("email");
        this.token = object.optString("token");
    }
}
