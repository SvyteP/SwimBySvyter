package com.example.swimbysvyter;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.swimbysvyter.entity.Complexity;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Inventory;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.ConvertorJSON;
import com.example.swimbysvyter.helpers.Convertors;
import com.example.swimbysvyter.services.api.RequestCallBack;
import com.example.swimbysvyter.services.api.SwimAPI;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwimApp extends Application {
    private final String TAG = "SwimApp";
    public static final String secFileShared = "secure_auth_prefs";
    public static final String loginShared = "loginPref";
    public static Convertors convertorJSON;
    public static SwimAPI swimAPI;
    public static Customer baseCustomer;
    public static Questioner baseQuestioner;
    public static List<Inventory> baseInventories;
    public static Map<String,Complexity> baseComplexities;
    public static List<String> baseGenderNames;
    public static SharedPreferences encSharedPreferences;
    public static SharedPreferences sharedPreferences;


    @SuppressWarnings("deprecation")
    public static MasterKey masterKey;

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate() {
        super.onCreate();

        swimAPI = new SwimAPI(getString(R.string.swim_api_server_address));
        context = getApplicationContext();
        try {
            masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();
        } catch (GeneralSecurityException | IOException e) {
            Log.e("SwimApp","Constructor error: " + e.getMessage());
        }

        convertorJSON = new ConvertorJSON();

        sharedPreferences = context.getSharedPreferences(loginShared, Context.MODE_PRIVATE);
        try {
            encSharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    secFileShared,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        } catch (GeneralSecurityException | IOException e) {
            Log.e("LogInViewModel","EncryptedSharedPreferences or MasterKey error: " + e.getMessage());
        }
        baseInventories = new ArrayList<>();
        baseComplexities = new HashMap<>();
        baseGenderNames = new ArrayList<>(List.of("Man","Women"));
        baseCustomer = new Customer("login","email","token");
    }



    public static void disableBtn(LinearLayout layout){
        layout.setClickable(false);
        layout.setEnabled(false);
    }
    public static void enabledBtn(LinearLayout layout){
        layout.setClickable(true);
        layout.setEnabled(true);
    }

 /*   public static void invalidEditText(EditText editText){
        editText.setBackgroundResource(R.drawable.edit_field_with_color_invalid);
    }*/
    public static void updateQuestionerForApp(Questioner q){
        sharedPreferences.edit().putString("questionerInfo",convertorJSON.ConvertObjectToStringJSON(q)).apply();
        baseQuestioner = q;
    }
    public static void updateCustomerForApp(Customer c){
        encSharedPreferences.edit().putString("customerInfo",convertorJSON.ConvertObjectToStringJSON(c)).apply();
        baseCustomer = c;
    }

    public static void transitionActivity(Context context, FragmentActivity from, Class to){
        Intent intent = new Intent(context, to);
        from.startActivity(intent);
        from.finish();
    }


}
