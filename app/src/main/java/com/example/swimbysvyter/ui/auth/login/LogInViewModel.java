package com.example.swimbysvyter.ui.auth.login;

import static com.example.swimbysvyter.SwimApp.context;
import static com.example.swimbysvyter.SwimApp.customer;
import static com.example.swimbysvyter.SwimApp.questioner;
import static com.example.swimbysvyter.SwimApp.swimAPI;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.ConvertorJSON;
import com.example.swimbysvyter.helpers.Convertors;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.services.api.RequestCallBack;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import lombok.Getter;

@Getter
public class LogInViewModel extends ViewModel {

    private final String FILE_NAME = "secure_auth_prefs";
    private final MutableLiveData<String> login;
    private final MutableLiveData<String> pass;
    private final MutableLiveData<Boolean> isAuthorized;
    private final Convertors convertor;

    @SuppressWarnings("deprecation")
    private MasterKey masterKey;
    private SharedPreferences encSharedPreferences;
    private final SharedPreferences sharedPreferences;

    @SuppressWarnings("deprecation")
    public LogInViewModel() {
        this.isAuthorized = new MutableLiveData<>();
        this.login = new MutableLiveData<>();
        this.pass = new MutableLiveData<>();
        this.convertor = new ConvertorJSON();
        this.sharedPreferences = context.getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        try {
            this.masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
            this.encSharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    FILE_NAME,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Log.e("LogInViewModel","EncryptedSharedPreferences or MasterKey error: " + e.getMessage());
        }
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

                    sharedPreferences.edit().putString("questionerInfo",data.optString("questioner")).apply();
                    encSharedPreferences.edit().putString("customerInfo",convertor.ConvertObjectToStringJSON(customerInfo)).apply();
                    encSharedPreferences.edit().putString("authToken",data.optString("token")).apply();
                    checkAuthorized(customerInfo,questionerInfo);
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
        swimAPI.Login(login.getValue(),pass.getValue(),callBack);
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
                customer = convertor.ConvertJSONToObject(customerPref,Customer.class);
                questioner = convertor.ConvertJSONToObject(questionerPref,Questioner.class);
            } else {
                Log.e("LogInViewModel" , "checkAuthorized error about customerPref or questionerPref == null");
            }

        } else if (customerInfo != null && questionerInfo != null){
            customer = customerInfo;
            questioner = questionerInfo;
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
