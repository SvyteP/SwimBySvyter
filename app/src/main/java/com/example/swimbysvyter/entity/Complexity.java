package com.example.swimbysvyter.entity;

import org.json.JSONObject;

import java.io.Serializable;

import lombok.Data;

@Data
public class Complexity implements Serializable {
    private long id;
    private String name;

    public Complexity() {
    }

    public Complexity(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Complexity(JSONObject object) {
        this.id = object.optLong("id");
        this.name = object.optString("name");
    }


}
