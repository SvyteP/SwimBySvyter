package com.example.swimbysvyter.ui.favorite;

import static com.example.swimbysvyter.SwimApp.swimAPI;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.RVTrainings;
import com.example.swimbysvyter.services.api.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class FavoriteViewModel extends ViewModel {
    private MutableLiveData<List<Training>> trainings;
    private MutableLiveData<RVTrainings> adapterRVTrainings;
    private boolean isStartLoadTraining = false;
    private MutableLiveData<ClickItemListener> clickItem;

    public FavoriteViewModel() {
        this.trainings = new MutableLiveData<>();
        this.adapterRVTrainings = new MutableLiveData<>();
        this.clickItem = new MutableLiveData<>();

    }

    public void loadTraining(){
        if (!isStartLoadTraining) {
            isStartLoadTraining = true;
            RequestCallBack callBack = new RequestCallBack() {
                @Override
                public void onSuccess(Object object) {
                    List<Training> trainingList = (List<Training>) object;
                    if (trainingList != null) {
                        trainings.setValue(trainingList);
                        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItem.getValue()));
                    }
                    isStartLoadTraining = false;
                }

                @Override
                public void onError(Object object) {
                    isStartLoadTraining = false;
                }
            };
            swimAPI.getIsLikedTrainings(true, callBack);
        }
    }

    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(ClickItemListener clickItemListener){
        clickItem.setValue(clickItemListener);
        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItem.getValue()));
        return adapterRVTrainings;
    }


}