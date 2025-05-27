package com.example.swimbysvyter.ui.profile.dialog;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.SwimApp.baseGenderNames;
import static com.example.swimbysvyter.SwimApp.swimAPI;
import static com.example.swimbysvyter.SwimApp.updateQuestionerForApp;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.services.api.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class EditProfileDialogViewModel extends ViewModel {
    private final MutableLiveData<List<String>> genderList;
    private final MutableLiveData<List<String>> complexityList;
    private final MutableLiveData<Questioner> questionerDialog;


    public EditProfileDialogViewModel() {
        this.genderList = new MutableLiveData<>();
        this.complexityList = new MutableLiveData<>();
        this.questionerDialog = new MutableLiveData<>();
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

    public void updateQuestioner(Questioner q){
        if (q == null) return;
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                Log.i("EditProfileDialogViewModel","updateQuestioner success: " + object.toString());
            }

            @Override
            public void onError(Object object) {
                Log.e("EditProfileDialogViewModel","updateQuestioner error: " + object.toString());
            }
        };
        swimAPI.updateQuestioner(q, callBack);
        questionerDialog.setValue(q);
        updateQuestionerForApp(q);
    }

    public void serQuestioner(Questioner q){
        if (q == null) return;

    }

}
