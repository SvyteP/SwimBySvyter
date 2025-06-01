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
    private MutableLiveData<ClickItemListener> clickItem;
    private boolean isStartLoadTraining = false;

    public CompletedViewModel() {
        this.trainings = new MutableLiveData<>();
        this.adapterRVTrainings = new MutableLiveData<>();
        this.clickItem = new MutableLiveData<>();
    }

    /*Загружает выполненные тренировки*/
    public void loadTraining() {
        if (!isStartLoadTraining) {
            isStartLoadTraining = true;
            RequestCallBack callBack = new RequestCallBack() {
                @Override
                public void onSuccess(Object object) {
                    List<Training> trainingList = (List<Training>) object;
                    if (trainingList != null) {
                        trainings.setValue(trainingList);
                        adapterRVTrainings.setValue(new RVTrainings(trainings, clickItem.getValue()));
                    }
                    isStartLoadTraining = false;
                }

                @Override
                public void onError(Object object) {
                    isStartLoadTraining = false;
                }
            };
            swimAPI.getIsCompletedTrainings(callBack, true);
        }
    }

    /*Формирует адаптер для RecycleView*/
    public MutableLiveData<RVTrainings> getRVTrainingsAdapter(ClickItemListener clickItemListener) {
        clickItem.setValue(clickItemListener);
        adapterRVTrainings.setValue(new RVTrainings(trainings, clickItem.getValue()));
        return adapterRVTrainings;
    }

    /*
     * Метод обновляет измененную тренировку в TrainingDetailActivity на случай
     * если по возвращению на TrainingsFragment сервер не успеет обновить данные
     * */
    public void updateTraining(Training updated) {
        List<Training> current = trainings.getValue();
        if (current == null) return;

        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).getId().equals(updated.getId())) {
                current.set(i, updated);
                adapterRVTrainings.setValue(new RVTrainings(trainings, clickItem.getValue()));
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
                adapterRVTrainings.setValue(new RVTrainings(trainings, clickItem.getValue()));
                break;
            }
        }
        return trainings;
    }
}
