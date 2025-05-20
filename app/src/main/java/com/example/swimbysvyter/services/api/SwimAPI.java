package com.example.swimbysvyter.services.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.swimbysvyter.dto.LoginDto;
import com.example.swimbysvyter.dto.ResponseRetrofitDto;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.HttpNames;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwimAPI {
    private final String TAG = "SwimAPI";
    private final RequestsSwimAPI requestsSwimAPI;
    private final OkHttpClient clientWithToken;


    public SwimAPI(String swimServerAddresses) {
        String baseSwimURL = HttpNames.HTTP_PREFIX + swimServerAddresses;

        this.clientWithToken =  new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .build();
                return chain.proceed(request);
            }
        }).build();

        this.requestsSwimAPI = RetrofitSenderFactory.getAPIImpl(baseSwimURL,
                GsonConverterFactory.create(),
                clientWithToken,
                RequestsSwimAPI.class);
    }

    public void Login(String login, String pass, RequestCallBack callBack){
        requestsSwimAPI.login(new LoginDto(login,pass)).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<JSONObject> call, retrofit2.Response<JSONObject> response) {
                if (response.isSuccessful()){
                    JSONObject resultJSON = response.body();
                    Customer result = new Customer();

                    callBack.onSuccess(result);
                }
                else {
                    callBack.onError(call);
                    Log.e(TAG,String.format("Execution request login is failed with code: %s and body %s",response.code(),response.body()));
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG,String.format("Send request login is failed with exception: %s",t.getMessage()));
            }
        });
    }


    public void getQuestioner(Long customerId, RequestCallBack callBack){
        requestsSwimAPI.getQuestioner(customerId).enqueue(new Callback<ResponseRetrofitDto<Questioner>>() {
            @Override
            public void onResponse(Call<ResponseRetrofitDto<Questioner>> call, retrofit2.Response<ResponseRetrofitDto<Questioner>> response) {
                if(response.isSuccessful()){
                    Questioner result = response.body().getData();
                    if (result != null){
                        callBack.onSuccess(result);
                    }
                    else {
                        callBack.onError(call);
                        Log.e(TAG,String.format("Execution request login is failed with code: %s and body %s",response.code(),response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRetrofitDto<Questioner>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG,String.format("Send request login is failed with exception: %s",t.getMessage()));
            }
        });
    }

}
