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

    public Questioner(int age, int countTrainOneWeek, int countWeek, String gender, int lengthPool, int timeTrain, Long complexityId, String complexityName) {
        this.age = age;
        this.countTrainOneWeek = countTrainOneWeek;
        this.countWeek = countWeek;
        this.gender = gender;
        this.lengthPool = lengthPool;
        this.timeTrain = timeTrain;
        this.complexityId = complexityId;
        this.complexityName = complexityName;
    }
}
