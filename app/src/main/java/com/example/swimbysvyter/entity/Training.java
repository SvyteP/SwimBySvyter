package com.example.swimbysvyter.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import lombok.Data;

@Data
public class Training {
    private Long id;
    private String name;
    private String warmUp;
    private String main;
    private String hitch;
    private List<Inventory> inventories;

    public Training() {
    }

    public Training(Long id, String name, String warmUp, String main, String hitch, List<Inventory> inventories) {
        this.id = id;
        this.name = name;
        this.warmUp = warmUp;
        this.main = main;
        this.hitch = hitch;
        this.inventories = inventories;
    }
    public String getInventoriesStr(){
        StringJoiner joiner = new StringJoiner(",");
        inventories.forEach(n ->{
            joiner.add(n.getName());
        });
        return joiner.toString();
    }
}
