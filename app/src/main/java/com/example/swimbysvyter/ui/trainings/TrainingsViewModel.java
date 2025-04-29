package com.example.swimbysvyter.ui.trainings;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TrainingsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Training>> trainings;
    private MutableLiveData<RVTrainings> adapterRVTrainings;

    public TrainingsViewModel() {
        this.trainings = new MutableLiveData<>(new ArrayList<>(getTrainings()));
        this.adapterRVTrainings = new MutableLiveData<>(new RVTrainings(trainings,pos -> {

        }));
    }

    public ArrayList<Training> getTrainings(){
        ArrayList<Training> trainings1 =  new ArrayList<>();
        ArrayList<Inventory> inventories = new ArrayList<>();
        inventories.add(new Inventory(1L,"name"));

        trainings1.add(new Training(1L,"name","warmUp","main","hitch",inventories));

        return trainings1;
    }

    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(){
        adapterRVTrainings.setValue(new RVTrainings(trainings,pos -> {

        }));
        return adapterRVTrainings;
    }


}