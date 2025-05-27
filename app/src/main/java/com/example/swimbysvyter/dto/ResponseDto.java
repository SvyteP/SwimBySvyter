package com.example.swimbysvyter.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

public record ResponseDto<T> (T data, String dateTime){
}
