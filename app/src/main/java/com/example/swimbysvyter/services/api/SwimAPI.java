package com.example.swimbysvyter.services.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.swimbysvyter.helpers.HttpNames;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwimAPI {
    private final String TAG = "SwimAPI";
    private final RequestsSwimAPI requestsSwimAPI;
    private final OkHttpClient clientWithToken;
    private String swimServerAddresses;



    public SwimAPI(String swimServerAddresses) {
        this.swimServerAddresses = HttpNames.HTTP_PREFIX + swimServerAddresses;

        this.clientWithToken =  new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("","")
                        .build();
                return chain.proceed(request);
            }
        }).build();

        this.requestsSwimAPI = RetrofitSenderFactory.getAPIImpl(swimServerAddresses,
                GsonConverterFactory.create(),
                clientWithToken,
                RequestsSwimAPI.class);
    }

    private void createRequest(String url, String method, String token, RequestResult result, Context ctx){
        ExecutorHttpSender httpSender = new ExecutorHttpSender(url, method, token, result, ctx);
        httpSender.sendRequest();
    }


    public void Login(String login, String pass, Context ctx, RequestCallBack callBack){
        String url = swimServerAddresses + "/login/user";
    /*
            try {
                url = String.format(url + "?")
            }*/

        RequestResult requestResult = result -> {

        };

        createRequest(url,HttpNames.POST_METHOD,null,requestResult,ctx);
    }


    public void getQuestioner(Long customerId){

    }


}
