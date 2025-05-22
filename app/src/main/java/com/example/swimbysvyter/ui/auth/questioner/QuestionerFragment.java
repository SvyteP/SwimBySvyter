package com.example.swimbysvyter.ui.auth.questioner;

import static com.example.swimbysvyter.helpers.SpinnerUtils.updateArrayAdapter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.swimbysvyter.databinding.FragmentQuestionerBinding;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.EditTextUtils;
import com.example.swimbysvyter.helpers.ValidText;

public class QuestionerFragment extends Fragment implements TextWatcher {
    private final QuestionerViewModel questionerViewModel;
    private FragmentQuestionerBinding binding;
    private View mainView;
    private EditText
            editAge,
            editCountTrainingOneWeek,
            editCountWeek,
            editLengthPool,
            editTrainingTime;
    private LinearLayout llSave;
    private Spinner
            spinnerGender,
            spinnerComplexity;
    private ArrayAdapter<String> genderAdapter, complexityAdapter;

    public QuestionerFragment() {
        this.questionerViewModel = new QuestionerViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater,container,savedInstanceState);
        updateView();
        updateListeners();
        return mainView;
    }
    private void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionerBinding.inflate(inflater,container,false);
        mainView = binding.getRoot();

        editAge = binding.questAgeEdit;
        editCountTrainingOneWeek = binding.questCountTrainOneWeekEdit;
        editCountWeek = binding.questCountWeekEdit;
        spinnerGender = binding.questGenderSpinner;
        editLengthPool = binding.questLengthPoolEdit;
        editTrainingTime = binding.questTimeTrainEdit;
        spinnerComplexity = binding.questComplexitySinner;


        llSave = binding.questSaveBtn;
        llSave.setEnabled(false);
    }

    private void updateView() {
        //Заменить данными из ViewMode(Создать ее)
        questionerViewModel.getGenderList().observe(getViewLifecycleOwner(),g ->updateArrayAdapter(
                g,
                genderAdapter,
                spinnerGender,
               null, null, requireContext()));
        //Заменить данными из ViewMode(Создать ее)
        questionerViewModel.getComplexityList().observe(getViewLifecycleOwner(),c -> updateArrayAdapter(
                c,
                complexityAdapter,
                spinnerComplexity,
                null, null, requireContext()));

        spinnerGender.setDropDownVerticalOffset(100);
        spinnerComplexity.setDropDownVerticalOffset(100);
    }
    private void updateListeners() {
        llSave.setOnClickListener(this::clickSave);
    }

    private void clickSave(View v){
        //проверяем данные и отправляем их на сервер обновляться
        questionerViewModel.createQuestioner(null);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        llSave.setEnabled(ValidText.sizeValid(editAge,editCountTrainingOneWeek,editCountWeek,editLengthPool,editTrainingTime));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
