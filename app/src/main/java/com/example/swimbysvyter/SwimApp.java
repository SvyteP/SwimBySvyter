package com.example.swimbysvyter;

import android.app.Application;
import android.content.Context;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.swimbysvyter.databinding.ContentMainBinding;
import com.example.swimbysvyter.entity.Complexity;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.services.api.SwimAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwimApp extends Application {
    public static SwimAPI swimAPI;
    public static SwimAPI JWTToken;
    public static Customer customer;
    public static Questioner questioner;
    public static Map<String, Integer> complexities;
    public static List<String> genderNames;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        customer = new Customer("login","email@mail.ru","token");
        questioner = new Questioner(18,1,4,"Man",25,45,new Complexity(1L,"Easy"));
        complexities = new HashMap<>();
        complexities.put("Easy", 1);
        complexities.put("Medium", 2);
        complexities.put("High", 3);

        genderNames = new ArrayList<>(List.of("Man","Women"));

        swimAPI = new SwimAPI("10.0.2.2:8080");

        context = getApplicationContext();
    }

    public static void disableBtn(LinearLayout layout){
        layout.setClickable(false);
        layout.setEnabled(false);
    }
    public static void enabledBtn(LinearLayout layout){
        layout.setClickable(true);
        layout.setEnabled(true);
    }

    public static void invalidEditText(EditText editText){
        editText.setBackgroundResource(R.drawable.edit_field_with_color_invalid);
    }
}
