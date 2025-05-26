package com.example.swimbysvyter.ui.profile;


import static com.example.swimbysvyter.SwimApp.baseCustomer;
import static com.example.swimbysvyter.SwimApp.baseInventories;
import static com.example.swimbysvyter.SwimApp.baseQuestioner;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.RVInventories;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    private MutableLiveData<RVInventories> adapterRVInventories;
    private MutableLiveData<List<Inventory>> inventoriesCheckList;

    public ProfileViewModel() {
        this.name = new MutableLiveData<>(baseCustomer.getLogin());
        this.email = new MutableLiveData<>(baseCustomer.getEmail());
        this.age = new MutableLiveData<>(String.valueOf(baseQuestioner.getAge()));
        this.countTrainOneWeek = new MutableLiveData<>(String.valueOf(baseQuestioner.getCountTrainOneWeek()));
        this.countWeek = new MutableLiveData<>(String.valueOf(baseQuestioner.getCountWeek()));
        this.gender = new MutableLiveData<>(baseQuestioner.getGender());
        this.lengthPool = new MutableLiveData<>(String.valueOf(baseQuestioner.getLengthPool()));
        this.timeTrain = new MutableLiveData<>(String.valueOf(baseQuestioner.getTimeTrain()));
        this.complexity = new MutableLiveData<>(baseQuestioner.getComplexity().getName());

        this.adapterRVInventories = new MutableLiveData<>();
        this.inventoriesCheckList = new MutableLiveData<>();
        loadData();
    }

    private void loadData(){
        loadInventory();
    }


    public MutableLiveData<RVInventories> getAdapterRVInventories(){
        adapterRVInventories.setValue(new RVInventories(inventoriesCheckList.getValue(), pos -> {
            inventoriesCheckList.getValue().get(pos);
            ArrayList<Inventory> inventoriesTest = (ArrayList<Inventory>) inventoriesCheckList.getValue();
            Log.i("TestProfileView",inventoriesTest.toString());
        }));
        return adapterRVInventories;
    }

    private void loadInventory(){
        if (baseInventories != null){
            inventoriesCheckList.setValue(baseInventories);
        } else {
            // request get inventories and save
        }
    }

}
