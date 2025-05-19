package com.example.swimbysvyter.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;

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

}
