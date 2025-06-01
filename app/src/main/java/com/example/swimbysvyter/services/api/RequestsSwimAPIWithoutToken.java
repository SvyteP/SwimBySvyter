package com.example.swimbysvyter.services.api;

import com.example.swimbysvyter.dto.AuthDto;
import com.example.swimbysvyter.dto.LoginDto;
import com.example.swimbysvyter.dto.RegistrationDto;
import com.example.swimbysvyter.dto.ResponseDto;
import com.example.swimbysvyter.entity.Inventory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestsSwimAPIWithoutToken extends API{
    @POST("auth/login")
    Call<ResponseDto<AuthDto>> login(@Body LoginDto loginDto);

    @POST("auth/reg")
    Call<ResponseDto<AuthDto>> registration(@Body RegistrationDto registrationDto);

    @GET("/inventory/get")
    Call<ResponseDto<List<Inventory>>> getInventoryForCustomer();
}
