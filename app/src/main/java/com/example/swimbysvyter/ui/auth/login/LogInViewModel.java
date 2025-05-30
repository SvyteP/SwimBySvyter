package com.example.swimbysvyter.ui.auth.login;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.SwimApp.convertorJSON;
import static com.example.swimbysvyter.SwimApp.baseCustomer;
import static com.example.swimbysvyter.SwimApp.encSharedPreferences;
import static com.example.swimbysvyter.SwimApp.baseQuestioner;
import static com.example.swimbysvyter.SwimApp.sharedPreferences;
import static com.example.swimbysvyter.SwimApp.swimAPI;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.swimbysvyter.SwimApp;
import com.example.swimbysvyter.dto.AuthDto;
import com.example.swimbysvyter.entity.Complexity;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.services.api.RequestCallBack;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import lombok.Getter;

@Getter
public class LogInViewModel extends ViewModel {
    private final String TAG = "LogInViewModel";
    private final MutableLiveData<String> login;
    private final MutableLiveData<String> pass;
    private final MutableLiveData<Boolean> isAuthorized;


    public LogInViewModel() {
        this.isAuthorized = new MutableLiveData<>();
        this.login = new MutableLiveData<>();
        this.pass = new MutableLiveData<>();
        updateViewModel();
    }

    private void updateViewModel(){
        updateIsAuthorized();
    }

    public void sendLogInInfo(ModelCallBack modelCallBack){

        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                    AuthDto data = (AuthDto) object;
                    Customer customerInfo = new Customer(data.login(), data.email(), data.token());
                    Questioner questionerInfo = data.questioner();

                    checkAuthorized(customerInfo,questionerInfo);
                    encSharedPreferences.edit().putString("authToken",data.token()).apply();
                    isAuthorized.setValue(true);
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

        swimAPI.login(login.getValue(),pass.getValue(),callBack);
    }


    public void setLogin(String l){
        login.setValue(l);
    }

    public void setPass(String p){
        pass.setValue(p);
    }

    public boolean checkAuthorized(Customer customerInfo, Questioner questionerInfo){
        if (isAuthorized.getValue() != null) {
            updateAppInfo(customerInfo,questionerInfo);
        } else {
            Log.e("LogInViewModel" , "checkAuthorized error about isAuthorized == null");
        }

        return isAuthorized.getValue();
    }

    private void updateAppInfo(Customer customerInfo, Questioner questionerInfo){
        if (isAuthorized.getValue() != null) {
            if (isAuthorized.getValue()) {
                String customerPref = encSharedPreferences.getString("customerInfo", null);
                String questionerPref = sharedPreferences.getString("questionerInfo", null);

                if (customerPref != null && questionerPref != null) {
                    baseCustomer = convertorJSON.ConvertJSONToObject(customerPref, Customer.class);
                    baseQuestioner = convertorJSON.ConvertJSONToObject(questionerPref, Questioner.class);
                } else {
                    Log.e("LogInViewModel", "checkAuthorized error about customerPref or questionerPref == null");
                    return;
                }

            } else if (customerInfo != null && questionerInfo != null) {
                SwimApp.updateQuestionerForApp(questionerInfo);
                SwimApp.updateCustomerForApp(customerInfo);
            } else {
                Log.e("LogInViewModel", "checkAuthorized error about customerPref or questionerPref == null");
                return;
            }
            loadComplexities();
            loadInventories();
        }else {
            Log.e("LogInViewModel" , "checkAuthorized error about customerPref or questionerPref == null");
        }
    }

    private void updateIsAuthorized(){
        String token = encSharedPreferences.getString("authToken",null);
        if (token != null && !token.isBlank()){
            isAuthorized.setValue(true);
            return;
        }
        isAuthorized.setValue(false);
    }

    private void loadComplexities(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                try {
                    List<Complexity> allComplexity = (List<Complexity>) object;
                    allComplexity.forEach(compl -> {
                        baseComplexities.put(compl.getName(),compl);
                    });
                } catch (ClassCastException e) {
                    Log.e(TAG,"loadComplexity error:" + e.getMessage());
                }
            }

            @Override
            public void onError(Object object) {

            }
        };
        swimAPI.getAllComplexities(callBack);
    }

    private void loadInventories(){
        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                try {
                    List<Inventory> allInventories = (List<Inventory>) object;
                    if (allInventories != null) {
                        SwimApp.baseInventories = allInventories;
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



}
