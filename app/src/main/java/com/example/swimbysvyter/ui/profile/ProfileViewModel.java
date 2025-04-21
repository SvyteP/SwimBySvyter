package com.example.swimbysvyter.ui.profile;


import static com.example.swimbysvyter.SwimApp.customer;
import static com.example.swimbysvyter.SwimApp.questioner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import lombok.Getter;

@Getter
public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> name;
    private final MutableLiveData<String> email;

    private final MutableLiveData<String> age;
    private final MutableLiveData<String> countTrainOneWeek;
    private final MutableLiveData<String> countWeek;
    private final MutableLiveData<String> gender;
    private final MutableLiveData<String> lengthPool;
    private final MutableLiveData<String> timeTrain;
    private final MutableLiveData<String> complexity;

    public ProfileViewModel() {
        this.name = new MutableLiveData<>(customer.getName());
        this.email = new MutableLiveData<>(customer.getEmail());
        this.age = new MutableLiveData<>(String.valueOf(questioner.getAge()));
        this.countTrainOneWeek = new MutableLiveData<>(String.valueOf(questioner.getCountTrainOneWeek()));
        this.countWeek = new MutableLiveData<>(String.valueOf(questioner.getCountWeek()));
        this.gender = new MutableLiveData<>(questioner.getGender());
        this.lengthPool = new MutableLiveData<>(String.valueOf(questioner.getLengthPool()));
        this.timeTrain = new MutableLiveData<>(String.valueOf(questioner.getTimeTrain()));
        this.complexity = new MutableLiveData<>(questioner.getComplexityName());
    }
}
