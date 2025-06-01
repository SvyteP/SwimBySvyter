package com.example.swimbysvyter.ui.completed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.databinding.FragmentCompletedBinding;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ClickItemListener;
import com.example.swimbysvyter.helpers.TrainingStatus;
import com.example.swimbysvyter.ui.activities.TrainingDetailActivity;

public class CompletedFragment extends Fragment {
    private FragmentCompletedBinding binding;
    private CompletedViewModel completedViewModel;
    private RecyclerView recCompletedTraining;
    private View mainView;

    private ActivityResultLauncher<Intent> trainingDetailLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        updateView(inflater,container,savedInstanceState);
        initView();
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
        completedViewModel.loadTraining();
    }

    private void updateView(@NonNull LayoutInflater inflater,
                            @Nullable ViewGroup container,
                            @Nullable Bundle savedInstanceState){
        binding = FragmentCompletedBinding.inflate(inflater,container,false);
        completedViewModel = new ViewModelProvider(this).get(CompletedViewModel.class);

        mainView = binding.getRoot();

        recCompletedTraining = binding.recCompletedTrainings;


        // Регистрируем коллбэк результата от Activity
        trainingDetailLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {

                        Intent data = result.getData();
                        Training training = data.getParcelableExtra("updatedTraining");
                        if (training != null){
                            if (data.getBooleanExtra("likedTraining",false) ){
                                completedViewModel.updateTraining(training);
                            }
                            if (data.getBooleanExtra("completedTraining",false)) {
                                if(completedViewModel.delTraining(training).getValue().isEmpty()){
                                    /*Добавить отображение текста*/
                                }
                            }
                        }
                    }
                });


    }

    private void initView(){
        completedViewModel.getRVTrainingsAdapter(pos -> {
            clickTraining(completedViewModel.getTrainings().getValue().get(pos));
        }).observe(getViewLifecycleOwner(),recCompletedTraining::setAdapter);
        recCompletedTraining.setLayoutManager(new LinearLayoutManager(getContext()));;
    }

    public void clickTraining(Training training){
        Intent intent = new Intent(getContext(), TrainingDetailActivity.class);
        intent.putExtra("trainingsView", TrainingStatus.COMPLETED);
        intent.putExtra("trainingDetail",training);
        trainingDetailLauncher.launch(intent);
    }

}
