package com.example.swimbysvyter.helpers;

public interface Convertors {
    String ConvertObjectToStringJSON(Object o);

    <T> T ConvertJSONToObject(String json, Class<T> o);
}
