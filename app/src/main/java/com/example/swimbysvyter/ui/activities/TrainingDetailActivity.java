package com.example.swimbysvyter.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.TrainingDetailsActivityBinding;
import com.example.swimbysvyter.entity.Training;

public class TrainingDetailActivity extends Activity {
    private Intent intentArgs;
    private Training training;
    private TrainingDetailsActivityBinding binding;
    private TextView name,warmUp,main,hitch;
    private LinearLayout detailTrainingBackLL;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_details_activity);
        initView();
        updateView();
        updateListeners();
    }

    private void initView(){
        binding = TrainingDetailsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        name = binding.detailTrainingName;
        warmUp = binding.detailTrainingWarmUp;
        main = binding.detailTrainingMain;
        hitch = binding.detailTrainingHitch;
        detailTrainingBackLL =binding.detailTrainingBackLl;

        intentArgs = getIntent();
        if (intentArgs != null) {
            training = intentArgs.getParcelableExtra("trainingDetail");
        }
    }

    private void updateView(){
        if (training != null){
            name.setText(training.getName());
            warmUp.setText(training.getWarmUp());
            main.setText(training.getMain());
            hitch.setText(training.getHitch());
        }
    }

    private void updateListeners(){
        detailTrainingBackLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



}
