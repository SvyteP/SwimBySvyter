package com.example.swimbysvyter.ui.profile;


import static com.example.swimbysvyter.SwimApp.swimAPI;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.SwimApp;
import com.example.swimbysvyter.entity.Customers;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.services.api.RequestCallBack;
import com.example.swimbysvyter.services.api.SwimAPI;

public class ProfileViewModel extends ViewModel {
    private Customers customers;
    private Questioner questioner;

    private final MutableLiveData<String> name;
    private final MutableLiveData<String> email;

    private final MutableLiveData<String> age;
    private final MutableLiveData<String> countTrainOneWeek;
    private final MutableLiveData<String> countWeek;
    private final MutableLiveData<String> gender;
    private final MutableLiveData<String> lengthPool;
    private final MutableLiveData<String> timeTrain;
    private final MutableLiveData<String> cmplexity;

    public ProfileViewModel() {
        this.name = customers.getName();
        this.email = customers.getEmail();
        this.age = questioner.getAge();
        this.countTrainOneWeek = questioner.;
        this.countWeek = ;
        this.gender = ;
        this.lengthPool = ;
        this.timeTrain = ;
        this.cmplexity = ;
    }

    private void getQuestioner(){

    }

    private void getCustomer(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onError(Object object) {

            }
        };
        /*swimAPI.get*/
    }

}
