package com.example.swimbysvyter.services.api;

import com.example.swimbysvyter.dto.LoginDto;
import com.example.swimbysvyter.dto.ResponseRetrofitDto;
import com.example.swimbysvyter.entity.Questioner;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RequestsSwimAPI extends API{
    @POST("auth/login")
    Call<JSONObject> login(@Body LoginDto loginDto);

    @GET("questioner/{id}")
    Call<ResponseRetrofitDto<Questioner>> getQuestioner(@Path("id")Long userId);

}
