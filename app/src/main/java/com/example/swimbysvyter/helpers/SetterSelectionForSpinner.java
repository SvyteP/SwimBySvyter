package com.example.swimbysvyter.helpers;

import android.widget.Spinner;

import com.example.swimbysvyter.entity.Questioner;

import java.util.List;

public interface SetterSelectionForSpinner {
    void startBlock(Questioner questioner, List<String> list, Spinner spinner);
}
