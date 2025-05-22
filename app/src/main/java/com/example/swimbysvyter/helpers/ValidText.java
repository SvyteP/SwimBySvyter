package com.example.swimbysvyter.helpers;

import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidText {
    public static boolean emailValid(String  message){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }

    public static boolean sizeValid(EditText...editTexts){
        long i = Arrays.stream(editTexts).filter(editText ->{
            int type = editText.getInputType();
            if (type != InputType.TYPE_TEXT_VARIATION_PASSWORD || type != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                return editText.getText().length() > 0;
            }
            return editText.getText().length() > 5;
        }).count();
        return i == editTexts.length;
    }
}
