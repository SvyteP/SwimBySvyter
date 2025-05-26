package com.example.swimbysvyter.ui.auth.questioner;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.SwimApp.baseGenderNames;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Questioner;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class QuestionerViewModel extends ViewModel {
    private final MutableLiveData<List<String>> genderList;
    private final MutableLiveData<List<String>> complexityList;
    private final MutableLiveData<Questioner> questioner;


    public QuestionerViewModel() {
        this.genderList = new MutableLiveData<>();
        this.complexityList = new MutableLiveData<>();
        this.questioner = new MutableLiveData<>();
        updateData();
    }

    private void updateData(){
        loadGenderList();
        loadComplexityList();
    }

    private void loadGenderList(){
        genderList.setValue(baseGenderNames);
    }

    private void loadComplexityList(){
        List<String> complexitiesNames = new ArrayList<>();
        baseComplexities.forEach((k, v) -> {
            complexitiesNames.add(k);
        });
        complexityList.setValue(complexitiesNames);
    }

    public void createQuestioner(Questioner q){
        if (q == null) return;

        questioner.setValue(q);
        //Отправка запроса на создание quesioner

    }
}
