package com.example.swimbysvyter.ui.trainings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.databinding.FragmentHomeBinding;

public class TrainingsFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView trainingsRec;
    private View mainView;
    TrainingsViewModel trainingsViewModel;


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
        trainingsViewModel.getRVTrainingsAdapter().observe(getViewLifecycleOwner(),trainingsRec::setAdapter);
        trainingsRec.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}