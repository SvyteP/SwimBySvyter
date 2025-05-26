package com.example.swimbysvyter.dto;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;

import java.util.List;

public record CustomersGetDto(String login, String email, List<Inventory> inventory, Questioner questioner) {
}
