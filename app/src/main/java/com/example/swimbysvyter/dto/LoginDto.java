package com.example.swimbysvyter.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class LoginDto {
    @NonNull
    String login;
    @NonNull
    String password;

    public LoginDto(String login, String password) {
        this.login = login;
        this.password = password;
    }
}

