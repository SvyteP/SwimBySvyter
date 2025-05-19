package com.example.swimbysvyter.entity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.Serializable;

import lombok.Data;

@Data
public class Questioner implements Serializable {
    private int age;
    private int countTrainOneWeek;
    private int countWeek;
    private String gender;
    private int lengthPool;
    private int timeTrain;
    private Complexity complexity;


    public Questioner(int age, int countTrainOneWeek, int countWeek, String gender, int lengthPool, int timeTrain, Complexity complexity) {
        this.age = age;
        this.countTrainOneWeek = countTrainOneWeek;
        this.countWeek = countWeek;
        this.gender = gender;
        this.lengthPool = lengthPool;
        this.timeTrain = timeTrain;
        this.complexity = complexity;
    }
}
