package com.example.swimbysvyter.helpers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.entity.Questioner;

import java.util.List;

public class SpinnerUtils {

    public static void updateArrayAdapter(List<String> list,
                                    ArrayAdapter<String> adapter,
                                    Spinner spinner,
                                    Questioner questioner,
                                    SetterSelectionForSpinner setter,
                                    Context context){
        adapter = new ArrayAdapter<>(
                context,
                R.layout.item_spinner,
                list
        );
        adapter.setDropDownViewResource(R.layout.item_spiner_dropdown);
        spinner.setAdapter(adapter);

        if (setter == null || questioner == null) return;
        setter.startBlock(questioner,list,spinner);
    }
}
