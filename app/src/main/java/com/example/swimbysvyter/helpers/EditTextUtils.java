package com.example.swimbysvyter.helpers;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.swimbysvyter.R;

public class EditTextUtils {
    public static void clickEyeImg(View v, EditText passField){
        ImageView eyeImg = (ImageView) v;
        int  cursorPos = passField.getSelectionEnd();
        if (passField.getInputType() ==
                (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passField.setInputType(
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            eyeImg.setImageResource(R.drawable.ic_eye_on);
        } else {
            passField.setInputType(
                    InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            eyeImg.setImageResource(R.drawable.ic_eye_off);
        }
        // Обновление позиции курсора
        passField.setSelection(cursorPos);
    }

}
