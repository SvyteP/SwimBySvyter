package com.example.swimbysvyter.ui.profile;


import static com.example.swimbysvyter.SwimApp.baseCustomer;
import static com.example.swimbysvyter.SwimApp.baseInventories;
import static com.example.swimbysvyter.SwimApp.baseQuestioner;
import static com.example.swimbysvyter.SwimApp.swimAPI;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.helpers.RVInventories;
import com.example.swimbysvyter.services.api.RequestCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileViewModel extends ViewModel {
    private final String TAG = "ProfileViewModel";
    private final MutableLiveData<String> name;
    private final MutableLiveData<String> email;

    private final MutableLiveData<String> age;
    private final MutableLiveData<String> countTrainOneWeek;
    private final MutableLiveData<String> countWeek;
    private final MutableLiveData<String> gender;
    private final MutableLiveData<String> lengthPool;
    private final MutableLiveData<String> timeTrain;
    private final MutableLiveData<String> complexity;

    private boolean isChangedInfo = false;

    private MutableLiveData<RVInventories> adapterRVInventories;
    private MutableLiveData<List<Inventory>> inventoriesCheckList;

    public ProfileViewModel() {
        this.name = new MutableLiveData<>(baseCustomer.getLogin());
        this.email = new MutableLiveData<>(baseCustomer.getEmail());
        this.age = new MutableLiveData<>("");
        this.countTrainOneWeek = new MutableLiveData<>("");
        this.countWeek = new MutableLiveData<>("");
        this.gender = new MutableLiveData<>("");
        this.lengthPool = new MutableLiveData<>("");
        this.timeTrain = new MutableLiveData<>("");
        this.complexity = new MutableLiveData<>("");

        this.adapterRVInventories = new MutableLiveData<>();
        this.inventoriesCheckList = new MutableLiveData<>();
        loadData();
        setInfoQuestioner(baseQuestioner);
    }

    private void setInfoQuestioner(Questioner q){
        age.setValue(String.valueOf(q.getAge()));
        countTrainOneWeek.setValue(String.valueOf(q.getCountTrainOneWeek()));
        countWeek.setValue(String.valueOf(q.getCountWeek()));
        gender.setValue(q.getGender());
        lengthPool.setValue(String.valueOf(q.getLengthPool()));
        timeTrain.setValue(String.valueOf(q.getTimeTrain()));
        complexity.setValue(q.getComplexity().getName());
    }

    private void loadData(){
        loadInventory();
    }


    public void fixingUpdateCustomerInfo(){
        if (!isChangedInfo) return;

        updateInventories();
        updateTrainings();

        isChangedInfo = false;
    }

    public MutableLiveData<RVInventories> getAdapterRVInventories(){
        adapterRVInventories.setValue(new RVInventories(inventoriesCheckList.getValue(), pos -> {
            isChangedInfo = true;
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

    public void updateInventories(){
        List<Inventory> inv= inventoriesCheckList.getValue();
        if (inv != null) {
            List<Long> inventoriesId = inventoriesCheckList.getValue().stream().filter(Inventory::getIsStock).map(Inventory::getId).collect(Collectors.toCollection(ArrayList::new));
            RequestCallBack callBack = new RequestCallBack() {
                @Override
                public void onSuccess(Object object) {
                    Log.i(TAG, "updateInventories success with id's: " + inventoriesId + " and result obj: " + object);
                }

                @Override
                public void onError(Object object) {
                    Log.e(TAG, "updateInventories error with id's: " + inventoriesId + " and result obj: " + object);
                }
            };
            swimAPI.updateInventories(inventoriesId, callBack);
            return;
        }
        Log.e(TAG,"updateInventories: inventoriesCheckList is null");
    }

    public void updateTrainings(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
            }

            @Override
            public void onError(Object object) {
            }
        };
        swimAPI.setTrainings(callBack);
    }

    public void loadQuestioner(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                Questioner q = (Questioner) object;
                if (q == null) return;
                setInfoQuestioner(q);
            }

            @Override
            public void onError(Object object) {
                Log.e(TAG,"loadQuestioner error with object: " + object);
            }
        };
        swimAPI.getQuestioner(callBack);
    }

    public ModelCallBack getCallBackForUpdateQuestioner() {
        return new ModelCallBack() {
            @Override
            public void success(Object o) {
                Questioner q = (Questioner) o;
                if (q != null) {
                    setInfoQuestioner(q);
                    return;
                }
                Log.e(TAG,"getCallBackForUpdateQuestioner error success questioner is null");
            }

            @Override
            public void error(Object o) {
                Log.e(TAG,"getCallBackForUpdateQuestioner error success questioner is null");
            }
        };
    }
}
