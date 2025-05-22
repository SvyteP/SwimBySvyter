package com.example.swimbysvyter.helpers;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class ConvertorJSON implements Convertors{
    private final Gson gson;

    public ConvertorJSON() {
        this.gson = new Gson();
    }

    @Override
    public String ConvertObjectToStringJSON(Object o) {
        return gson.toJson(o);
    }

    @Override
    public <T> T ConvertJSONToObject(String json, Class<T> o) {
        return gson.fromJson(json, o);
    }
}
