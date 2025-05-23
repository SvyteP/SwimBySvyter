package com.example.swimbysvyter.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private T data;
    private LocalDateTime dateTime;

}
