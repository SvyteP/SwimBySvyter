package com.example.swimbysvyter.ui.auth.registration;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import lombok.Getter;

@Getter
public class RegistrationViewModel extends ViewModel {
    private final MutableLiveData<String> login;
    private final MutableLiveData<String> pass;
    private final MutableLiveData<String> email;

    public RegistrationViewModel() {
        this.login = new MutableLiveData<>();
        this.pass = new MutableLiveData<>();
        this.email = new MutableLiveData<>();
    }



    public void setLogin(String l){
        login.setValue(l);
    }
    public void setPass(String p){
        pass.setValue(p);
    }
    public void setEmail(String e){
        email.setValue(e);
    }
}
