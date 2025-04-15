package com.example.swimbysvyter.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;

public class ProfileViewModel extends ViewModel {
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
        this.name = new MutableLiveData<>();
        this.email = new MutableLiveData<>();
        this.age = new MutableLiveData<>();
        this.countTrainOneWeek = new MutableLiveData<>();
        this.countWeek = new MutableLiveData<>();
        this.gender = new MutableLiveData<>();
        this.lengthPool = new MutableLiveData<>();
        this.timeTrain = new MutableLiveData<>();
        this.cmplexity = new MutableLiveData<>();
    }

}
