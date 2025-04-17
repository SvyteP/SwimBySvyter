package com.example.swimbysvyter.dto;

import lombok.Data;

@Data
public class ResponseRetrofitDto<T>{
    public T data;
    public String date;
}
