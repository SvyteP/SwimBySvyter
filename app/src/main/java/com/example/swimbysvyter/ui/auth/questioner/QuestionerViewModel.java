package com.example.swimbysvyter.ui.auth.questioner;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.SwimApp.baseGenderNames;
import static com.example.swimbysvyter.SwimApp.baseInventories;
import static com.example.swimbysvyter.SwimApp.swimAPI;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.swimbysvyter.SwimApp;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.RVInventories;
import com.example.swimbysvyter.services.api.RequestCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public class QuestionerViewModel extends ViewModel {
    private final String TAG = "QuestionerViewModel";
    private final MutableLiveData<List<String>> genderList;
    private final MutableLiveData<List<String>> complexityList;
    private final MutableLiveData<Questioner> questioner;

    private final MutableLiveData<RVInventories> adapterRVInventories;
    private final MutableLiveData<List<Inventory>> inventoriesCheckList;


    public QuestionerViewModel() {
        this.genderList = new MutableLiveData<>();
        this.complexityList = new MutableLiveData<>();
        this.questioner = new MutableLiveData<>();
        this.adapterRVInventories = new MutableLiveData<>();
        this.inventoriesCheckList = new MutableLiveData<>();
        updateData();
    }

    private void updateData(){
        loadGenderList();
        loadComplexityList();
        loadInventories();
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

    private void loadInventories(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                try {
                    List<Inventory> allInventories = (List<Inventory>) object;
                    if (allInventories != null) {
                        SwimApp.baseInventories = allInventories;
                        inventoriesCheckList.setValue(allInventories);
                        getAdapterRVInventories();
                    }
                } catch (ClassCastException e) {
                    Log.e(TAG,"loadInventories error:" + e.getMessage());
                }
            }

            @Override
            public void onError(Object object) {

            }
        };
        swimAPI.getAllInventories(callBack);
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
                updateInventories();
            }

            @Override
            public void onError(Object object) {

            }
        };

        questioner.setValue(q);
        //Отправка запроса на регистрацию
        swimAPI.registration(c.getLogin(),c.getEmail(),pass,responseReg);
    }

    public MutableLiveData<RVInventories> getAdapterRVInventories(){
        adapterRVInventories.setValue(new RVInventories(inventoriesCheckList.getValue(), pos -> {

        }));
        return adapterRVInventories;
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

}
