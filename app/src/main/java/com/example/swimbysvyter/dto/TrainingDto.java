package com.example.swimbysvyter.dto;

import com.example.swimbysvyter.entity.Complexity;
import com.example.swimbysvyter.entity.Inventory;

import java.util.List;

public record TrainingDto(String name, String warmUp, String mainTraining, String hitch, List<Inventory> inventories, Complexity complexity) {
}
