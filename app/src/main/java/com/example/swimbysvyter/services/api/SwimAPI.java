package com.example.swimbysvyter.services.api;

import static com.example.swimbysvyter.SwimApp.encSharedPreferences;
import static com.example.swimbysvyter.SwimApp.secFileShared;
import static com.example.swimbysvyter.SwimApp.updateCustomerForApp;
import static com.example.swimbysvyter.SwimApp.updateQuestionerForApp;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.swimbysvyter.dto.LoginDto;
import com.example.swimbysvyter.dto.QuestionerUpdateDtoRequest;
import com.example.swimbysvyter.dto.RegistrationDto;
import com.example.swimbysvyter.dto.ResponseDto;
import com.example.swimbysvyter.dto.AuthDto;
import com.example.swimbysvyter.dto.TrainingsGetDto;
import com.example.swimbysvyter.entity.Complexity;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.entity.Training;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

public class SwimAPI {
    private final String TAG = "SwimAPI";
    private final RequestsSwimAPI requestsSwimAPI;
    private final OkHttpClient clientWithToken;
    private final OkHttpClient clientWithoutToken;
    private final RequestsSwimAPIWithoutToken requestsSwimAPIWithoutToken;


    public SwimAPI(String swimServerAddresses) {
        String baseSwimURL = "http://" + swimServerAddresses;

        this.clientWithToken = new OkHttpClient().newBuilder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                       String token = encSharedPreferences.getString(secFileShared,"");
                        Request request = chain.request().newBuilder()
                                .addHeader("Authorization","Bearer " + token)
                                .build();
                        return chain.proceed(request);
                    }
                }).build();


        this.requestsSwimAPI = RetrofitSenderFactory.getAPIImpl(baseSwimURL,
                GsonConverterFactory.create(),
                clientWithToken,
                RequestsSwimAPI.class);


        this.clientWithoutToken = new OkHttpClient().newBuilder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                }).build();

        this.requestsSwimAPIWithoutToken = RetrofitSenderFactory.getAPIImpl(baseSwimURL,
                GsonConverterFactory.create(),
                clientWithoutToken,
                RequestsSwimAPIWithoutToken.class);

    }

    public void login(String login, String pass, RequestCallBack callBack) {
        LoginDto loginDto = new LoginDto(
                Base64.encodeToString(login.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP),
                Base64.encodeToString(pass.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP)
        );
        requestsSwimAPIWithoutToken.login(loginDto).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<AuthDto>> call, retrofit2.Response<ResponseDto<AuthDto>> response) {
               if (response.body() != null) {
                   if (response.isSuccessful()) {
                       AuthDto data = response.body().data();
                       encSharedPreferences.edit().putString(secFileShared, data.token()).apply();
                       callBack.onSuccess(data);
                   } else {
                       callBack.onError(call);
                       Log.e(TAG, String.format("Execution request login is failed with code: %s and body %s", response.code(), response.body()));
                   }
               }else {
                   callBack.onError(call);
                   Log.e(TAG, String.format("Execution request login is failed with code: %s and body %s", response.code(), response.body()));
               }
            }

            @Override
            public void onFailure(Call<ResponseDto<AuthDto>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, String.format("Send request login is failed with exception: %s", t.getMessage()));
            }
        });
    }


    public void registration(String login, String email, String pass, RequestCallBack callBack) {
        RegistrationDto regDto = new RegistrationDto(
                Base64.encodeToString(login.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP),
                Base64.encodeToString(email.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP),
                Base64.encodeToString(pass.getBytes(StandardCharsets.UTF_8), Base64.NO_WRAP)
        );
        requestsSwimAPIWithoutToken.registration(regDto).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<AuthDto>> call, retrofit2.Response<ResponseDto<AuthDto>> response) {
                if (response.isSuccessful()) {
                    AuthDto data;
                    data = response.body().data();
                    updateCustomerForApp(new Customer(data.login(), data.email(), data.token()));
                    encSharedPreferences.edit().putString(secFileShared,data.token()).apply();
                    callBack.onSuccess(data);
                } else {
                    callBack.onError(call);
                    Log.e(TAG, String.format("Execution request registration is failed with code: %s and body %s", response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<AuthDto>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, String.format("Send request registration is failed with exception: %s", t.getMessage()));
            }
        });
    }


    public void getQuestioner(RequestCallBack callBack) {
        requestsSwimAPI.getQuestioner().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<Questioner>> call, retrofit2.Response<ResponseDto<Questioner>> response) {
                if (response.isSuccessful()) {

                    Questioner result = response.body().data();
                    if (result != null) {
                        callBack.onSuccess(result);
                    } else {
                        callBack.onError(call);
                        Log.e(TAG, String.format("Execution request login is failed with code: %s and body %s", response.code(), response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<Questioner>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, String.format("Send request login is failed with exception: %s", t.getMessage()));
            }
        });
    }

    public void updateQuestioner(Questioner questioner, RequestCallBack callBack) {
        QuestionerUpdateDtoRequest updateDto = new QuestionerUpdateDtoRequest(questioner);
        requestsSwimAPI.updateQuestioner(updateDto).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<Questioner>> call,
                                   retrofit2.Response<ResponseDto<Questioner>> response) {
                if (response.isSuccessful()) {
                    Questioner quest = response.body().data();
                    if (quest != null) {
                        updateQuestionerForApp(quest);
                        callBack.onSuccess(quest);
                    } else {
                        callBack.onError(call);
                        Log.e(TAG, String.format("Execution request updateQuestioner is failed with code: %s and body %s", response.code(), response.body()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<Questioner>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, "UpdateQuestioner failed with message: " + t.getMessage());
            }
        });
    }

    public void setTrainings(RequestCallBack callBack) {
        requestsSwimAPI.generateTrainings().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<List<TrainingsGetDto>>> call, retrofit2.Response<ResponseDto<List<TrainingsGetDto>>> response) {
               if (response.body() != null) {
                   List<TrainingsGetDto> data = response.body().data();

                   if (data != null) {
                       List<Training> trainings = new ArrayList<>();
                       data.forEach(t -> trainings.add(new Training(t.id(), t.trainingsDTO(), t.likeTrain(), t.completed())));
                       callBack.onSuccess(trainings);
                   } else {
                       callBack.onError(response.body());
                       Log.e(TAG, String.format("Execution request generateTrainings is failed with code: %s and body %s", response.code(), response.body()));
                   }
               } else {
                   callBack.onError(call);
                   Log.e(TAG, String.format("Execution request generateTrainings is failed with code: %s and body %s", response.code(), response.body()));
               }
            }

            @Override
            public void onFailure(Call<ResponseDto<List<TrainingsGetDto>>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, "setTrainings failed with message: " + t.getMessage());
            }
        });
    }

    public void getTraining(long id, RequestCallBack callBack) {
        requestsSwimAPI.getInfoAboutTraining(id).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<TrainingsGetDto>> call, retrofit2.Response<ResponseDto<TrainingsGetDto>> response) {
                TrainingsGetDto getDto = response.body().data();
                if (getDto != null) {
                    Training training = new Training(getDto.id(), getDto.trainingsDTO(), getDto.likeTrain(), getDto.completed());
                    callBack.onSuccess(training);
                } else {
                    callBack.onError(response.body());
                    Log.e(TAG, String.format("Execution request getTraining is failed with code: %s and body %s", response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<TrainingsGetDto>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, "getTraining failed with message: " + t.getMessage());
            }
        });
    }

    public void changeLike(boolean isLike, long trainingId, RequestCallBack callBack) {
        requestsSwimAPI.isLikeTraining(trainingId, isLike).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                    return;
                }
                callBack.onError(response.body());
                Log.e(TAG, String.format("Execution request changeLike is failed with code: %s and body %s", response.code(), response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, "changeLike failed with message: " + t.getMessage());
            }
        });
    }

    public void changeComplete(boolean isComplete, long trainingId, RequestCallBack callBack) {
        requestsSwimAPI.isCompletedTraining(trainingId, isComplete).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body());
                    return;
                }
                callBack.onError(response.body());
                Log.e(TAG, String.format("Execution request isCompletedTraining is failed with code: %s and body %s", response.code(), response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, "changeLike failed with message: " + t.getMessage());
            }
        });
    }

    public void getAllInventories(RequestCallBack callBack){
        requestsSwimAPI.getInventory().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Inventory>>> call, retrofit2.Response<ResponseDto<List<Inventory>>> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        callBack.onSuccess(response.body().data());
                        return;
                    }
                }
                callBack.onError(response.body());
                Log.e(TAG, String.format("Execution request getAllInventories is failed with code: %s and body %s", response.code(), response.body()));
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Inventory>>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG, "getAllInventories failed with message: " + t.getMessage());
            }
        });
    }

    public void getAllComplexities(RequestCallBack callBack){
        requestsSwimAPI.getComplexity().enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Complexity>>> call, retrofit2.Response<ResponseDto<List<Complexity>>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        callBack.onSuccess(response.body().data());
                        return;
                    }
                }
                callBack.onError(response.body());
                Log.e(TAG,String.format("Execution request getAllComplexity is failed with code: %s and body %s", response.code(), response.body()));
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Complexity>>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG,"getAllComplexity failed with message: " + t.getMessage());
            }
        });
    }


    public void getIsCompletedTrainings(RequestCallBack callBack, boolean isCompleted) {
        requestsSwimAPI.getIsCompletedTraining(isCompleted).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<List<TrainingsGetDto>>> call, retrofit2.Response<ResponseDto<List<TrainingsGetDto>>> response) {
                if (response.body() != null) {
                    List<TrainingsGetDto> data = response.body().data();

                    if (data != null) {
                        List<Training> trainings = new ArrayList<>();
                        data.forEach(t -> trainings.add(new Training(t.id(), t.trainingsDTO(), t.likeTrain(), t.completed())));
                        callBack.onSuccess(trainings);
                    } else {
                        callBack.onError(response.body());
                        Log.e(TAG, String.format("Execution request getIsCompletedTrainings is failed with code: %s and body %s", response.code(), response.body()));
                    }
                } else {
                    callBack.onError(call);
                    Log.e(TAG, String.format("Execution request getIsCompletedTrainings is failed with code: %s and body %s", response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<List<TrainingsGetDto>>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG,"getIsCompletedTrainings failed with message: " + t.getMessage());
            }
        });
    }

    public void getIsLikedTrainings(boolean isLike,RequestCallBack callBack) {
        requestsSwimAPI.getIsLikedTraining(isLike).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<List<TrainingsGetDto>>> call, retrofit2.Response<ResponseDto<List<TrainingsGetDto>>> response) {
                if (response.body() != null) {
                    List<TrainingsGetDto> data = response.body().data();

                    if (data != null) {
                        List<Training> trainings = new ArrayList<>();
                        data.forEach(t -> trainings.add(new Training(t.id(), t.trainingsDTO(), t.likeTrain(), t.completed())));
                        callBack.onSuccess(trainings);
                    } else {
                        callBack.onError(response.body());
                        Log.e(TAG, String.format("Execution request getIsLikedTrainings is failed with code: %s and body %s", response.code(), response.body()));
                    }
                } else {
                    callBack.onError(call);
                    Log.e(TAG, String.format("Execution request getIsLikedTrainings is failed with code: %s and body %s", response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<List<TrainingsGetDto>>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG,"getIsLikedTrainings failed with message: " + t.getMessage());
            }
        });
    }

    public void updateInventories(List<Long> inventoriesId,RequestCallBack callBack) {
        requestsSwimAPI.setInventories(inventoriesId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseDto<List<Long>>> call, retrofit2.Response<ResponseDto<List<Long>>> response) {
                if (response.body() != null) {
                    callBack.onSuccess(response.body().data());
                } else {
                    callBack.onError(call);
                    Log.e(TAG, String.format("Execution request updateInventories is failed with code: %s and body %s", response.code(), response.body()));
                }
            }

            @Override
            public void onFailure(Call<ResponseDto<List<Long>>> call, Throwable t) {
                callBack.onError(call);
                Log.e(TAG,"updateInventories failed with message: " + t.getMessage());
            }
        });
    }


}
