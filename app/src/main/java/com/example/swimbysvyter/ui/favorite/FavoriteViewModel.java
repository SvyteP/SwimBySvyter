package com.example.swimbysvyter.ui.favorite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.RVTrainings;

import java.util.ArrayList;

import lombok.Getter;

@Getter
public class FavoriteViewModel extends ViewModel {


    private MutableLiveData<ArrayList<Training>> trainings;
    private MutableLiveData<RVTrainings> adapterRVTrainings;

    public FavoriteViewModel() {
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