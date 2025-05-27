package com.example.swimbysvyter.dto;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;

import java.util.List;

public record AuthDto(String login, String email, List<Inventory> inventory, Questioner questioner, String token) {
}
