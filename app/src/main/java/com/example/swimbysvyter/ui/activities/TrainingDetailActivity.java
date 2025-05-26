package com.example.swimbysvyter.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.TrainingDetailsActivityBinding;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.RVTrainingDetailInventory;

public class TrainingDetailActivity extends AppCompatActivity {
    private Intent intentArgs;
    private Training training;
    private TrainingDetailsActivityBinding binding;
    private TextView name,warmUp,main,hitch;
    private LinearLayout detailTrainingBackLL;
    private RecyclerView recInventory;
    private ImageButton favoriteBtn;
    private LinearLayout completedBtn;
    private Intent result;
    private Boolean isTrainingsFragment;

    private OnBackPressedCallback onBackPressed;
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
        recInventory = binding.recDetailInv;
        favoriteBtn = binding.favoriteButton;
        completedBtn = binding.detailTrainingCompletedBtn;
        result = new Intent();

        intentArgs = getIntent();
        if (intentArgs != null) {
            training = intentArgs.getParcelableExtra("trainingDetail");
            isTrainingsFragment = intentArgs.getBooleanExtra("trainingsView",true);
        } else {
            isTrainingsFragment = true;
        }
    }

    private void updateView(){
        if (training != null){
            name.setText(training.getName());
            warmUp.setText(training.getWarmUp());
            main.setText(training.getMain());
            hitch.setText(training.getHitch());

            recInventory.setAdapter(new RVTrainingDetailInventory(training.getInventories(), pos -> {

            }));
            recInventory.setLayoutManager(new LinearLayoutManager(this));

            favoriteBtn.setImageResource(training.getFavoriteIcon());

            onBackPressed = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {

                    result.putExtra("updatedTraining", training);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            };
            getOnBackPressedDispatcher().addCallback(this,onBackPressed);
        }
    }

    private void updateListeners(){
        detailTrainingBackLL.setOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        favoriteBtn.setOnClickListener(this::clickFavoriteBtn);
        if (!isTrainingsFragment){
            completedBtn.setVisibility(View.GONE);
        }
        completedBtn.setOnClickListener(this::clickCompletedBtn);
    }

    private void clickFavoriteBtn(View v){
        result.putExtra("likedTraining", true);
        training.setLike(!training.isLike());
        favoriteBtn.setImageResource(training.getFavoriteIcon());
        updateFavorite();
    }

    private void clickCompletedBtn(View v){
        result.putExtra("completedTraining", true);
        getOnBackPressedDispatcher().onBackPressed();
    }

    private void updateFavorite(){

    }

    private void updateCompleted(){

    }

}
