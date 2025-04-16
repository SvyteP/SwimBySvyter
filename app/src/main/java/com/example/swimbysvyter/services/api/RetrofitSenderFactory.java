package com.example.swimbysvyter.services.api;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RetrofitSenderFactory{

    public static <T extends API> T getAPIImpl(String baseURL, Converter.Factory convertorFactory, OkHttpClient client, Class<T> tClass){
        Retrofit retrofit;
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(convertorFactory);
        if (client != null){
            retrofitBuilder.client(client);
        }
        retrofit = retrofitBuilder.build();

        return retrofit.create(tClass);
    }

}
