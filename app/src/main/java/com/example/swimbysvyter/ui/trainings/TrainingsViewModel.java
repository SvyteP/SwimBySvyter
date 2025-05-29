package com.example.swimbysvyter.ui.trainings;

import static com.example.swimbysvyter.SwimApp.swimAPI;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.helpers.RVTrainings;
import com.example.swimbysvyter.services.api.RequestCallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
@Getter
public class TrainingsViewModel extends ViewModel implements Serializable, ModelCallBack {

    private MutableLiveData<List<Training>> trainings;
    private MutableLiveData<RVTrainings> adapterRVTrainings;
    private MutableLiveData<ClickItemListener> clickItemListener;
    private boolean isStartLoadTraining = false;

    public TrainingsViewModel() {
        this.trainings = new MutableLiveData<>(new ArrayList<>());
        this.adapterRVTrainings = new MutableLiveData<>(new RVTrainings(trainings,pos -> {}));
        this.clickItemListener = new MutableLiveData<>();
        loadData();
    }

    private void loadData(){
        loadTrainings();
    }

    public void loadTrainings(){
        if (!isStartLoadTraining) {
            isStartLoadTraining = true;
            RequestCallBack callBack = new RequestCallBack() {
                @Override
                public void onSuccess(Object object) {
                    List<Training> trainingList = (List<Training>) object;
                    if (trainingList != null) {
                        trainings.setValue(trainingList);
                        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener.getValue()));
                    }
                    isStartLoadTraining = false;
                }

                @Override
                public void onError(Object object) {
                    isStartLoadTraining = false;
                }
            };
            swimAPI.setTrainings(callBack);
        }
    }

    public void reloadTrainings(){
        if (!isStartLoadTraining) {
            isStartLoadTraining = true;
            RequestCallBack callBack = new RequestCallBack() {
                @Override
                public void onSuccess(Object object) {
                    List<Training> trainingList = (List<Training>) object;
                    if (trainingList != null) {
                        trainings.setValue(trainingList);
                        adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener.getValue()));
                    }
                    isStartLoadTraining = false;
                }

                @Override
                public void onError(Object object) {
                    isStartLoadTraining = false;
                }
            };
            swimAPI.getIsCompletedTrainings(callBack, false);
        }
    }

    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(ClickItemListener listener){
        adapterRVTrainings.setValue(new RVTrainings(trainings,listener));
        clickItemListener.setValue(listener);
        return adapterRVTrainings;
    }

    public void updateTraining(Training updated) {
        List<Training> current = trainings.getValue();
        if (current == null) return;

        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).getId().equals(updated.getId())) {
                current.set(i, updated);
                adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener.getValue()));
                break;
            }
        }
        trainings.setValue(current);
    }

    public MutableLiveData<List<Training>> delTraining(Training t) {
        List<Training> current = trainings.getValue();
        if (current == null) return null;
        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).getId().equals(t.getId())) {
                current.remove(i);
                adapterRVTrainings.setValue(new RVTrainings(trainings,clickItemListener.getValue()));
                break;
            }
        }
        return trainings;
    }

    @Override
    public void success(Object o) {
        loadTrainings();
    }

    @Override
    public void error(Object o) {

    }
}