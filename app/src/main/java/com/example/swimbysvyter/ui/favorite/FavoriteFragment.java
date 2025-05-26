package com.example.swimbysvyter.ui.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.databinding.FragmentFavouriteBinding;
import com.example.swimbysvyter.entity.Training;
import com.example.swimbysvyter.ui.activities.TrainingDetailActivity;

public class FavoriteFragment extends Fragment {

    private FragmentFavouriteBinding binding;
    private RecyclerView recFavTraining;
    private View mainView;
    private FavoriteViewModel favoriteViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        initView(inflater, container, savedInstanceState);
        updateView();
        return mainView;
    }

    private void initView(@NonNull LayoutInflater inflater,
                          ViewGroup container, Bundle savedInstanceState){
        favoriteViewModel =
                new ViewModelProvider(this).get(FavoriteViewModel.class);

        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        recFavTraining = binding.recFavTrainings;
    }

    private void updateView(){
        favoriteViewModel.getRVTrainingsAdapter(pos -> {
            favoriteViewModel.getTrainings().observe(getViewLifecycleOwner(),
                    t -> clickTraining(t.get(pos)));
        }).observe(getViewLifecycleOwner(),recFavTraining::setAdapter);
        recFavTraining.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void clickTraining(Training training){
        Intent intent = new Intent(getContext(), TrainingDetailActivity.class);
        intent.putExtra("trainingsView",false);
        intent.putExtra("trainingDetail",training);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}