package com.example.swimbysvyter.ui.profile;

import android.os.Bundle;
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

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.DialogEditProfileFragmentBinding;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.SetterSelectionForSpinner;

import java.util.List;

public class EditProfileDialogFragment extends DialogFragment {
    private final EditProfileDialogViewModel editViewModel;
    private DialogEditProfileFragmentBinding binding;
    private View mainView;
    private EditText
            editAge,
            editNumTrainingWeek,
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
        this.editViewModel = new EditProfileDialogViewModel();
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
        binding = DialogEditProfileFragmentBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        editAge = binding.dialogEditAge;
        editNumTrainingWeek = binding.dialogEditCountTrainOneWeek;
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
            editNumTrainingWeek.setText(String.valueOf(profileQuestioner.getCountTrainOneWeek()));
            editCountWeek.setText(String.valueOf(profileQuestioner.getCountWeek()));
            editLengthPool.setText(String.valueOf(profileQuestioner.getLengthPool()));
            editTrainingTime.setText(String.valueOf(profileQuestioner.getTimeTrain()));
        }

        //Заменить данными из ViewMode(Создать ее)
        editViewModel.getGenderList().observe(getViewLifecycleOwner(),g ->updateArrayAdapter(
                g,
                genderAdapter,
                spinnerGender,
                profileQuestioner, ((questioner, list, spinner) -> {
                    if (questioner.getGender() == null) return;

                    for (int i = 0; i < list.size(); i++){
                        if (list.get(i).equals(questioner.getGender())) {
                            spinner.setSelection(i);
                        }
                    }
                })));
        //Заменить данными из ViewMode(Создать ее)
        editViewModel.getComplexityList().observe(getViewLifecycleOwner(),c -> updateArrayAdapter(
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
        }));

        spinnerGender.setDropDownVerticalOffset(100);
        spinnerComplexity.setDropDownVerticalOffset(100);
    }

    private void updateListener(){
        llSave.setOnClickListener(this::clickSave);
        closeBtn.setOnClickListener(this::clickClose);
    }

    private void clickSave(View v){
        //проверяем данные и отправляем их на сервер обновляться

    }

    private void clickClose(View v){
        dismiss();
    }

    private void updateArrayAdapter(List<String> list, ArrayAdapter<String> adapter, Spinner spinner, Questioner questioner, SetterSelectionForSpinner setter){
        adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_spinner,
                list
        );
        adapter.setDropDownViewResource(R.layout.item_spiner_dropdown);
        spinner.setAdapter(adapter);

        setter.startBlock(questioner,list,spinner);
    }

}
