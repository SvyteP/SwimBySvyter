package com.example.swimbysvyter.entity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import lombok.Data;

@Data
public class Questioner {
    private int age;
    private int countTrainOneWeek;
    private int countWeek;
    private String gender;
    private int lengthPool;
    private int timeTrain;
    private Long complexityId;
    private String complexityName;
}
