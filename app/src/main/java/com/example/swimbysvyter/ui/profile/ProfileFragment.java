package com.example.swimbysvyter.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.swimbysvyter.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private  ProfileViewModel profileViewModel;
    private TextView nameText;
    private TextView emailText;
    private TextView ageText;
    private TextView countTrainOneWeekText;
    private TextView countWeekText;
    private TextView genderText;
    private TextView lengthPoolText;
    private TextView timeTrainText;
    private TextView complexityText;
    private View mainView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container,savedInstanceState);
        updateView();
        return mainView;
    }

    private void initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        mainView = binding.getRoot();

        nameText = binding.nameText;
        emailText = binding.emailText;

        ageText = binding.ageText;
        countTrainOneWeekText = binding.countTrainOneWeekText;
        countWeekText = binding.countWeekText;
        genderText = binding.genderText;
        lengthPoolText = binding.lengthPoolText;
        timeTrainText = binding.timeTrainText;
        complexityText = binding.complexityText;
    }

    private void updateView(){
        profileViewModel.getName().observe(getViewLifecycleOwner(),nameText::setText);
        profileViewModel.getEmail().observe(getViewLifecycleOwner(),emailText::setText);
        profileViewModel.getAge().observe(getViewLifecycleOwner(),ageText::setText);
        profileViewModel.getCountTrainOneWeek().observe(getViewLifecycleOwner(),countTrainOneWeekText::setText);

        profileViewModel.getCountWeek().observe(getViewLifecycleOwner(),countWeekText::setText);
        profileViewModel.getGender().observe(getViewLifecycleOwner(),genderText::setText);
        profileViewModel.getLengthPool().observe(getViewLifecycleOwner(),lengthPoolText::setText);
        profileViewModel.getTimeTrain().observe(getViewLifecycleOwner(),timeTrainText::setText);
        profileViewModel.getComplexity().observe(getViewLifecycleOwner(),complexityText::setText);
    }
}
