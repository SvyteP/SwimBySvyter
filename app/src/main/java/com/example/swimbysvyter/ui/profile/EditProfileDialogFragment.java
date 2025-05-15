package com.example.swimbysvyter.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.DialogEditProfileFragmentBinding;
import com.example.swimbysvyter.databinding.FragmentFavouriteBinding;

import java.util.ArrayList;
import java.util.List;

public class EditProfileDialogFragment extends DialogFragment {
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
            editGender,
            editComplexity;

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
        editGender = binding.dialogEditGender;
        editLengthPool = binding.dialogEditLengthPool;
        editTrainingTime = binding.dialogEditTimeTrain;
        editComplexity = binding.dialogEditComplexity;


        llSave = binding.dialogEditSaveBtn;
        closeBtn = binding.dialogEditClose;
    }

    private void updateView(){
        //Заменить данными из ViewMode(Создать ее)
        String[] genders = {"Мужской", "Женский"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_spinner,
                genders
        );
        adapter.setDropDownViewResource(R.layout.item_spiner_dropdown);
        editGender.setAdapter(adapter);
        editGender.setDropDownVerticalOffset(100);

        //Заменить данными из ViewMode(Создать ее)
        ArrayList<String> list = new ArrayList<>(List.of("Низкая", "Средняя", "Высокая"));
        ArrayAdapter<String> complexityAdapter = new ArrayAdapter<>(
                requireContext(),
                R.layout.item_spinner,
                list
        );
        complexityAdapter.setDropDownViewResource(R.layout.item_spiner_dropdown);
        editComplexity.setAdapter(complexityAdapter);
        editComplexity.setDropDownVerticalOffset(100);
    }

    private void updateListener(){
        llSave.setOnClickListener(this::clickSave);
        closeBtn.setOnClickListener(v -> dismiss());
    }

    private void clickSave(View v){
        //проверяем данные и отправляем их на сервер обновляться
    }

    private void clickClose(View v){
        dismiss();
    }
}
