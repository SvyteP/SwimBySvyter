package com.example.swimbysvyter.ui.trainings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.swimbysvyter.databinding.FragmentTrainingsBinding;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.ui.activities.TrainingDetailActivity;
import com.example.swimbysvyter.ui.trainings.dialog.RefreshTrainingsDialogFragment;

import java.util.List;

public class TrainingsFragment extends Fragment {

    private FragmentTrainingsBinding binding;
    private RecyclerView trainingsRec;
    private View mainView;
    private TrainingsViewModel trainingsViewModel;
    private ActivityResultLauncher<Intent> trainingDetailLauncher;
    private RefreshTrainingsDialogFragment refreshDialog;
    private LinearLayout llUpdate, btnUpdate;
    private TextView notTrainingsTxt;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initViews(inflater,container,savedInstanceState);
        updateViews();
        updateListeners();

        return mainView;
    }

    private void initViews(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState){
        trainingsViewModel = new ViewModelProvider(this)
                .get(TrainingsViewModel.class);

        binding = FragmentTrainingsBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        trainingsRec = binding.recTrainings;
        llUpdate = binding.trainingsLlUpdate;
        btnUpdate = binding.trainingsBtnUpdate;
        notTrainingsTxt = binding.notTrainingsTxt;

    }

    private void updateViews(){
       trainingsViewModel.getTrainings().observe(getViewLifecycleOwner(),t -> {
           if (t.isEmpty()){
               notTrainingsTxt.setVisibility(View.VISIBLE);
               llUpdate.setVisibility(View.VISIBLE);
           } else {
               notTrainingsTxt.setVisibility(View.GONE);
               llUpdate.setVisibility(View.GONE);
           }
       });

        isUpdated();
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
                                if(trainingsViewModel.delTraining(training).getValue().isEmpty()){
                                    startRefreshDialog();
                                }
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

    @Override
    public void onStart() {
        super.onStart();
        trainingsViewModel.reloadTrainings();
    }

    private void updateListeners(){
        btnUpdate.setOnClickListener(this::clickUpdate);
    }

    private void clickUpdate(View view) {
        trainingsViewModel.loadTrainings();
    }

    private void clickTraining(Training training){
        Intent intent = new Intent(getContext(), TrainingDetailActivity.class);
        intent.putExtra("trainingDetail",training);
        trainingDetailLauncher.launch(intent);
    }

    private void startRefreshDialog(){
        ModelCallBack callBack = new ModelCallBack() {
            @Override
            public void success(Object o) {
                trainingsViewModel.loadTrainings();
            }

            @Override
            public void error(Object o) {

            }
        };

        refreshDialog = new RefreshTrainingsDialogFragment(callBack);
        refreshDialog.show(requireActivity().getSupportFragmentManager(),"RefreshDialog");
    }

    private void isUpdated(){
        requireActivity().getSupportFragmentManager().setFragmentResultListener(
                "resultRefresh",
                getViewLifecycleOwner(),
                (requestKey, result) -> {
                    boolean clickCloseRefresh = result.getBoolean("clickCloseRefresh");
                    if (clickCloseRefresh || trainingsViewModel.getTrainings().getValue().isEmpty()){
                        notTrainingsTxt.setVisibility(View.VISIBLE);
                        llUpdate.setVisibility(View.VISIBLE);
                    }
                }
        );
    }



}