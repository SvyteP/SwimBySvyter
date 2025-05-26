package com.example.swimbysvyter.ui.trainings;

import static com.example.swimbysvyter.SwimApp.baseInventories;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.SwimApp;
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
        this.adapterRVTrainings = new MutableLiveData<>(new RVTrainings(trainings,pos -> {}));
    }

    private ArrayList<Training> setTrainings(){
        ArrayList<Training> trainings1 =  new ArrayList<>();
        ArrayList<Inventory> inventories = new ArrayList<>();
        inventories.add(baseInventories.get(1));
        inventories.add(baseInventories.get(3));

        trainings1.add(new Training(1L,"name","warmUp","main","hitch",inventories,false,false));
        trainings1.add(new Training(2L,"2name","2warmUp","2main","2hitch",inventories,false,true));

        return trainings1;
    }

    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(ClickItemListener clickItemListener){
        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener));
        return adapterRVTrainings;
    }

    public void updateTraining(Training updated) {
        ArrayList<Training> current = trainings.getValue();
        if (current == null) return;

        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).getId().equals(updated.getId())) {
                current.set(i, updated);
                adapterRVTrainings.getValue().notifyItemChanged(i);
                break;
            }
        }
        trainings.setValue(current);
    }

    public void delTraining(Training t) {
        ArrayList<Training> current = trainings.getValue();
        if (current == null) return;

       for(int i = 0; i<current.size(); i++){
           if (current.get(i).getId() == t.getId()){
               current.remove(t);
               adapterRVTrainings.getValue().notifyItemRemoved(i);
           }
        }

        trainings.setValue(current);
    }
}