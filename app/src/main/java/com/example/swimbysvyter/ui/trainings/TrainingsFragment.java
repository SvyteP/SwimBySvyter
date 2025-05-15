package com.example.swimbysvyter.ui.trainings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.databinding.FragmentHomeBinding;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.ui.activities.TrainingDetailActivity;

public class TrainingsFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView trainingsRec;
    private View mainView;
    private TrainingsViewModel trainingsViewModel;
    private ActivityResultLauncher<Intent> trainingDetailLauncher;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initViews(inflater,container,savedInstanceState);
        updateViews();
        return mainView;
    }

    private void initViews(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState){
        trainingsViewModel = new ViewModelProvider(this)
                .get(TrainingsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        trainingsRec = binding.recTrainings;
    }

    private void updateViews(){

        trainingsViewModel.getRVTrainingsAdapter(pos -> {
            clickTraining(trainingsViewModel.getTrainings().getValue().get(pos));
        }).observe(getViewLifecycleOwner(), trainingsRec::setAdapter);
        trainingsRec.setLayoutManager(new LinearLayoutManager(getContext()));


        // Регистрируем коллбэк результата от Activity
        trainingDetailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {

                        Intent data = result.getData();
                        Training training = data.getParcelableExtra("updatedTraining");
                        if (training != null){
                            if (data.getBooleanExtra("likedTraining",false) ){
                                trainingsViewModel.updateTraining(training);
                            }
                            if (data.getBooleanExtra("completedTraining",false)) {
                                trainingsViewModel.delTraining(training);
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void clickTraining(Training training){
        Intent intent = new Intent(getContext(), TrainingDetailActivity.class);
        intent.putExtra("trainingDetail",training);
        trainingDetailLauncher.launch(intent);
    }
}