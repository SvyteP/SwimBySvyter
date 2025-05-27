package com.example.swimbysvyter.ui.auth.login;

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
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.services.api.RequestCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import lombok.Getter;

@Getter
public class LogInViewModel extends ViewModel {
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
                try {
                    JSONObject data = new JSONObject(object.toString());
                    JSONObject questionerJSON = data.optJSONObject("questioner");
                    Questioner questionerInfo = null;
                    Customer customerInfo = new Customer(data.optString("login"),data.optString("email"),data.optString("token"));

                    if (questionerJSON != null){
                       questionerInfo = new Questioner(questionerJSON);
                    }

                    checkAuthorized(customerInfo,questionerInfo);
                    encSharedPreferences.edit().putString("authToken",data.optString("token")).apply();
                    isAuthorized.setValue(true);

                    modelCallBack.success(object);
                } catch (JSONException e) {
                    Log.e("sendLogInInfo","RequestCallBack error: " + e.getMessage());
                    modelCallBack.error(object);
                }
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
        if (isAuthorized.getValue()){

            String customerPref = encSharedPreferences.getString("customerInfo",null);
            String questionerPref = sharedPreferences.getString("questionerInfo",null);

            if (customerPref != null && questionerPref != null){
                baseCustomer = convertorJSON.ConvertJSONToObject(customerPref,Customer.class);
                baseQuestioner = convertorJSON.ConvertJSONToObject(questionerPref,Questioner.class);
            } else {
                Log.e("LogInViewModel" , "checkAuthorized error about customerPref or questionerPref == null");
            }

        } else if (customerInfo != null && questionerInfo != null){
            SwimApp.updateQuestionerForApp(questionerInfo);
            SwimApp.updateCustomerForApp(customerInfo);
        } else {
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



}
