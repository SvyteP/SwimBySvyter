package com.example.swimbysvyter.services.api;

import android.content.Context;
import android.util.Log;

import com.example.swimbysvyter.helpers.HttpNames;

public class SwimAPI {
    private final String TAG = "SwimAPI";

    private String swimServerAddresses;

    public SwimAPI(String swimServerAddresses) {
        this.swimServerAddresses = HttpNames.HTTP_PREFIX + swimServerAddresses + "/";
    }

    private void createRequest(String url, String method, String token, RequestResult result, Context ctx){
        ExecutorHttpSender httpSender = new ExecutorHttpSender(url, method, token, result, ctx);
        httpSender.sendRequest();
    }


}
