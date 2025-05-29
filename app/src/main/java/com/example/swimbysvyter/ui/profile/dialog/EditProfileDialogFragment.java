package com.example.swimbysvyter.ui.profile.dialog;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.helpers.SpinnerUtils.updateArrayAdapter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.DialogEditProfileFragmentBinding;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.ValidText;

public class EditProfileDialogFragment extends DialogFragment implements TextWatcher {
    private EditProfileDialogViewModel dialogViewModel;
    private DialogEditProfileFragmentBinding binding;
    private View mainView;
    private EditText
            editAge,
            editCountTrainingOneWeek,
            editCountWeek,
            editLengthPool,
            editTrainingTime;
    private LinearLayout llSave;
    private ImageView closeBtn;
    private Spinner
            spinnerGender,
            spinnerComplexity;
    private ArrayAdapter<String> genderAdapter, complexityAdapter;
    private Questioner profileQuestioner;

    public EditProfileDialogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container, savedInstanceState);
        updateView();
        updateListener();

        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.dialogStyle);
    }

    private void initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        dialogViewModel = new ViewModelProvider(this).get(EditProfileDialogViewModel.class);
        binding = DialogEditProfileFragmentBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        editAge = binding.dialogEditAge;
        editCountTrainingOneWeek = binding.dialogEditCountTrainOneWeek;
        editCountWeek = binding.dialogEditCountWeek;
        spinnerGender = binding.dialogEditGender;
        editLengthPool = binding.dialogEditLengthPool;
        editTrainingTime = binding.dialogEditTimeTrain;
        spinnerComplexity = binding.dialogEditComplexity;


        llSave = binding.dialogEditSaveBtn;
        closeBtn = binding.dialogEditClose;

        profileQuestioner = (Questioner) getArguments().getSerializable("profileQuestioner");
    }

    private void updateView(){
        if (profileQuestioner != null){
            editAge.setText(String.valueOf(profileQuestioner.getAge()));
            editCountTrainingOneWeek.setText(String.valueOf(profileQuestioner.getCountTrainOneWeek()));
            editCountWeek.setText(String.valueOf(profileQuestioner.getCountWeek()));
            editLengthPool.setText(String.valueOf(profileQuestioner.getLengthPool()));
            editTrainingTime.setText(String.valueOf(profileQuestioner.getTimeTrain()));
        }

        //Заменить данными из ViewMode(Создать ее)
        dialogViewModel.getGenderList().observe(getViewLifecycleOwner(), g ->updateArrayAdapter(
                g,
                genderAdapter,
                spinnerGender,
                profileQuestioner, (questioner, list, spinner) -> {
                    if (questioner.getGender() == null) return;

                    for (int i = 0; i < list.size(); i++){
                        if (list.get(i).equals(questioner.getGender())) {
                            spinner.setSelection(i);
                        }
                    }
                }, requireContext()));
        //Заменить данными из ViewMode(Создать ее)
        dialogViewModel.getComplexityList().observe(getViewLifecycleOwner(), c -> updateArrayAdapter(
                c,
                complexityAdapter,
                spinnerComplexity,
                profileQuestioner, (questioner, list, spinner) -> {
            if (questioner.getComplexity().getName() == null) return;

            for (int i = 0; i < list.size(); i++){
                if (list.get(i).equals(questioner.getComplexity().getName())) {
                    spinner.setSelection(i);
                }
            }
        }, requireContext()));

        spinnerGender.setDropDownVerticalOffset(100);
        spinnerComplexity.setDropDownVerticalOffset(100);
    }

    private void updateListener(){
        editAge.addTextChangedListener(this);
        editCountTrainingOneWeek.addTextChangedListener(this);
        editCountWeek.addTextChangedListener(this);
        editLengthPool.addTextChangedListener(this);
        editTrainingTime.addTextChangedListener(this);

        llSave.setOnClickListener(this::clickSave);
        closeBtn.setOnClickListener(this::clickClose);
    }

    private void clickSave(View v){
            dialogViewModel.updateQuestioner(createQuestioner());
    }


    private Questioner createQuestioner(){
        return new Questioner(Integer.parseInt(editAge.getText().toString()),
                Integer.parseInt(editCountTrainingOneWeek.getText().toString()),
                Integer.parseInt(editCountWeek.getText().toString()),
                spinnerGender.getSelectedItem().toString(),
                Integer.parseInt(editLengthPool.getText().toString()),
                Integer.parseInt(editTrainingTime.getText().toString()),
                baseComplexities.get(spinnerComplexity.getSelectedItem()));
    }

    private void clickClose(View v){
        dismiss();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        llSave.setEnabled(ValidText.sizeValid(editAge,editCountTrainingOneWeek,editCountWeek,
                editLengthPool,editTrainingTime));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
