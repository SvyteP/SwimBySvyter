package com.example.swimbysvyter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDto {
    @NonNull
    String login;
    @NonNull
    String pass;

    public LoginDto(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }
}

