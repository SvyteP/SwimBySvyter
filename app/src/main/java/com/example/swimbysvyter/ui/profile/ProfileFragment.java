package com.example.swimbysvyter.ui.profile;

import static com.example.swimbysvyter.SwimApp.complexities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.swimbysvyter.databinding.FragmentProfileBinding;
import com.example.swimbysvyter.entity.Complexity;
import com.example.swimbysvyter.entity.Questioner;

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
    private EditProfileDialogFragment editDialogFragment;
    private LinearLayout editBtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container,savedInstanceState);
        updateView();
        updateListeners();
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
        editBtn = binding.editBtn;
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

        editDialogFragment = new EditProfileDialogFragment();
    }

    private void updateListeners(){
        editBtn.setOnClickListener(this::clickEditBtn);
    }

    private void clickEditBtn(View v){
        Bundle args = new Bundle();
        args.putSerializable("profileQuestioner",getProfileQuestioner());
        editDialogFragment.setArguments(args);
        editDialogFragment.show(requireActivity().getSupportFragmentManager(),"Edit_Dialog");
    }

    public Questioner getProfileQuestioner(){
        String complexityName = complexityText.getText().toString();
        Integer complexityId =  complexities.get(complexityName);
        if (complexityId == null)
            return null;
        return new Questioner(
                Integer.parseInt(ageText.getText().toString()),
                Integer.parseInt(countTrainOneWeekText.getText().toString()),
                Integer.parseInt(countWeekText.getText().toString()),
                genderText.getText().toString(),
                Integer.parseInt(lengthPoolText.getText().toString()),
                Integer.parseInt(timeTrainText.getText().toString()),
                new Complexity(
                        complexityId,
                        complexityText.getText().toString()
                )
        );
    }
}
