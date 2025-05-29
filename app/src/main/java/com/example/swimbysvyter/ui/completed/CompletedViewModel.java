package com.example.swimbysvyter.ui.completed;

import static com.example.swimbysvyter.SwimApp.swimAPI;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.RVTrainings;
import com.example.swimbysvyter.services.api.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Getter
public class CompletedViewModel extends ViewModel {
    private MutableLiveData<List<Training>> trainings;
    private MutableLiveData<RVTrainings> adapterRVTrainings;


    public CompletedViewModel() {
        this.trainings = new MutableLiveData<>();
        this.adapterRVTrainings = new MutableLiveData<>(new RVTrainings(trainings,pos -> {}));
    }

    private void setTrainings(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onError(Object object) {

            }
        };
    }

    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(ClickItemListener clickItemListener){
        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener));
        return adapterRVTrainings;
    }
}
