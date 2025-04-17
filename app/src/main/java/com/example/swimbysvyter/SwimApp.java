package com.example.swimbysvyter;

import android.app.Application;

import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.services.api.SwimAPI;

public class SwimApp extends Application {
    public static SwimAPI swimAPI;
    public static SwimAPI JWTToken;
    public static Customer customer;
    public static Questioner questioner;

    @Override
    public void onCreate() {
        super.onCreate();
        customer = new Customer("name","email@mail.ru","token");
        questioner = new Questioner(18,1,4,"Man",25,45,1L,"Easy");
    }
}
