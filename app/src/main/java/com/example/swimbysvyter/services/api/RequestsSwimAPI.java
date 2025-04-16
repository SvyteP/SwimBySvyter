package com.example.swimbysvyter.services.api;

import com.example.swimbysvyter.entity.Customers;

import retrofit2.Call;
import retrofit2.http.POST;

public interface RequestsSwimAPI extends API{
    @POST("login/user")
    Call<Customers> login();
}
