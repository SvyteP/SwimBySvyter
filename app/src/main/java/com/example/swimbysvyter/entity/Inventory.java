package com.example.swimbysvyter.entity;

import lombok.Data;

@Data
public class Inventory {
    private Long id;
    private String name;

    public Inventory() {
    }

    public Inventory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
