package com.example.swimbysvyter.ui.auth;

import static com.example.swimbysvyter.SwimApp.swimAPI;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.services.api.RequestCallBack;

import lombok.Getter;

@Getter
public class LogInViewModel extends ViewModel {
    private final MutableLiveData<String> login;
    private final MutableLiveData<String> pass;

    public LogInViewModel() {
        this.login = new MutableLiveData<>();
        this.pass = new MutableLiveData<>();
    }
    public void sendLogInInfo(ModelCallBack modelCallBack){

        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                modelCallBack.success(object);
            }

            @Override
            public void onError(Object object) {
                modelCallBack.error(object.toString());
                Log.e("LoginFragment","clickLogInBtn onError: " + object);
            }
        };
        if (login.getValue() == null || pass.getValue() == null){
            Log.e("LogInViewModel" , "sendLogInInfo error about login or pass == null");
            return;
        }
        swimAPI.Login(login.getValue(),pass.getValue(),callBack);
    }
    public void setLogin(String l){
        login.setValue(l);
    }

    public void setPass(String p){
        pass.setValue(p);
    }

}
