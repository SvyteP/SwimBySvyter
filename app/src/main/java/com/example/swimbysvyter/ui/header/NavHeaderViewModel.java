package com.example.swimbysvyter.ui.header;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

        return new MutableLiveData<>("name");
    }
    private MutableLiveData<String> requestForEmail(){

        return new MutableLiveData<>("email");
    }
}
