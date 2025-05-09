package com.example.swimbysvyter.ui.trainings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.RVTrainings;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Getter;
@Getter
public class TrainingsViewModel extends ViewModel implements Serializable {

    private MutableLiveData<ArrayList<Training>> trainings;
    private MutableLiveData<RVTrainings> adapterRVTrainings;

    public TrainingsViewModel() {
        this.trainings = new MutableLiveData<>(new ArrayList<>(setTrainings()));
        this.adapterRVTrainings = new MutableLiveData<>(new RVTrainings(trainings,pos -> {

        }));
    }

    private ArrayList<Training> setTrainings(){
        ArrayList<Training> trainings1 =  new ArrayList<>();
        ArrayList<Inventory> inventories = new ArrayList<>();
        inventories.add(new Inventory(1L,"name"));

        trainings1.add(new Training(1L,"name","warmUp","main","hitch",inventories));

        return trainings1;
    }

    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(ClickItemListener clickItemListener){
        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener));
        return adapterRVTrainings;
    }

}