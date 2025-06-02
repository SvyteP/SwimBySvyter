package com.example.swimbysvyter.ui.header;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.SwimApp;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NavHeaderViewModel extends ViewModel {
    private MutableLiveData<String> name;
    private MutableLiveData<String> email;

    public NavHeaderViewModel() {
        this.name = requestForName();
        this.email = requestForEmail();
    }

    private MutableLiveData<String> requestForName(){

        return new MutableLiveData<>(SwimApp.baseCustomer.getLogin());
    }
    private MutableLiveData<String> requestForEmail(){

        return new MutableLiveData<>(SwimApp.baseCustomer.getEmail());
    }
}
