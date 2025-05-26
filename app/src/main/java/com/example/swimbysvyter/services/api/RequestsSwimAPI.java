package com.example.swimbysvyter.services.api;

import com.example.swimbysvyter.dto.LoginDto;
import com.example.swimbysvyter.dto.QuestionerUpdateDtoRequest;
import com.example.swimbysvyter.dto.RequestRegistrationDto;
import com.example.swimbysvyter.dto.ResponseDto;
import com.example.swimbysvyter.dto.ResponseRegDto;
import com.example.swimbysvyter.dto.TrainingsGetDto;
import com.example.swimbysvyter.entity.Questioner;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RequestsSwimAPI extends API {
    @POST("auth/login")
    Call<ResponseBody> login(@Body LoginDto loginDto);

    @POST("auth/reg")
    Call<ResponseDto<ResponseRegDto>> registration(@Body RequestRegistrationDto requestRegistrationDto);

    @PUT("questioner")
    Call<ResponseDto<Questioner>> createQuestioner(@Body QuestionerUpdateDtoRequest questionerUpdateDtoRequest);

    @GET("questioner")
    Call<ResponseDto<Questioner>> getQuestioner();

    @PUT("questioner")
    Call<ResponseDto<Questioner>> updateQuestioner(@Body QuestionerUpdateDtoRequest questionerUpdateDtoRequest);

    @POST("customer/trainings")
    Call<ResponseDto<List<TrainingsGetDto>>> generateTrainings();

    @GET("/customer/trainings/one/{id}")
    Call<ResponseDto<TrainingsGetDto>> getInfoAboutTraining(@Path("id") long id);

    @PUT("/customer/trainings/like/{id}?isLike=true")
    Call<ResponseBody> isLikeTraining(@Query("isLike") boolean isLike, @Path("id") long trainingId);

    @PUT("/customer/trainings/complete/{id}?isCompl=true")
    Call<ResponseBody> isCompletedTraining(@Query("isCompl") boolean isCompl, @Path("id") long trainingId);
}
