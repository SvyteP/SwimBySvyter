package com.example.swimbysvyter.ui.profile.dialog;

import static com.example.swimbysvyter.SwimApp.complexities;
import static com.example.swimbysvyter.SwimApp.genderNames;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Questioner;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class EditProfileDialogViewModel extends ViewModel {
    private final MutableLiveData<List<String>> genderList;
    private final MutableLiveData<List<String>> complexityList;
    private final MutableLiveData<Questioner> questioner;


    public EditProfileDialogViewModel() {
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
        genderList.setValue(genderNames);
    }

    private void loadComplexityList(){
        List<String> complexitiesNames = new ArrayList<>();
        complexities.forEach((k,v) -> {
            complexitiesNames.add(k);
        });
        complexityList.setValue(complexitiesNames);
    }

    public void updateQuestioner(Questioner q){
        if (q == null) return;

        questioner.setValue(q);
        //Отправка запроса на обновление quesioner

    }

    public void serQuestioner(Questioner q){
        if (q == null) return;

    }

}
