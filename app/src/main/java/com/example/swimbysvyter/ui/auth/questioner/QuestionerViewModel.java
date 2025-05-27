package com.example.swimbysvyter.ui.auth.questioner;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.SwimApp.baseGenderNames;
import static com.example.swimbysvyter.SwimApp.swimAPI;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.services.api.RequestCallBack;

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

    public void regCustomer(Customer c,String pass, Questioner q, RequestCallBack callBackUI){
        if (q == null) return;
        RequestCallBack responseSetQuest = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                callBackUI.onSuccess(object);
            }

            @Override
            public void onError(Object object) {
                callBackUI.onError(object);
            }
        };

        RequestCallBack responseReg = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                //Отправка запроса на создание quesioner
                swimAPI.updateQuestioner(q,responseSetQuest);
            }

            @Override
            public void onError(Object object) {

            }
        };

        questioner.setValue(q);
        //Отправка запроса на регистрацию
        swimAPI.registration(c.getLogin(),c.getEmail(),pass,responseReg);

    }
}
