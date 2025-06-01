package com.example.swimbysvyter.ui.auth.questioner;

import static com.example.swimbysvyter.SwimApp.baseComplexities;
import static com.example.swimbysvyter.SwimApp.transitionActivity;
import static com.example.swimbysvyter.helpers.SpinnerUtils.updateArrayAdapter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swimbysvyter.MainActivity;
import com.example.swimbysvyter.databinding.FragmentQuestionerBinding;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.entity.Questioner;
import com.example.swimbysvyter.helpers.ValidText;
import com.example.swimbysvyter.services.api.RequestCallBack;

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
    private RecyclerView recInventory;

    public QuestionerFragment() {
        this.questionerViewModel = new QuestionerViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater, container, savedInstanceState);
        updateView();
        updateListeners();
        return mainView;
    }

    private void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentQuestionerBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        editAge = binding.questAgeEdit;
        editCountTrainingOneWeek = binding.questCountTrainOneWeekEdit;
        editCountWeek = binding.questCountWeekEdit;
        spinnerGender = binding.questGenderSpinner;
        editLengthPool = binding.questLengthPoolEdit;
        editTrainingTime = binding.questTimeTrainEdit;
        spinnerComplexity = binding.questComplexitySinner;
        recInventory = binding.questRecInventory;


        llSave = binding.questSaveBtn;
        llSave.setEnabled(false);
    }

    private void updateView() {

        questionerViewModel.getAdapterRVInventories().observe(getViewLifecycleOwner(), adapter -> recInventory.setAdapter(adapter));
        recInventory.setLayoutManager(new LinearLayoutManager(getContext()));

        //Заменить данными из ViewMode(Создать ее)
        questionerViewModel.getGenderList().observe(getViewLifecycleOwner(), g -> updateArrayAdapter(
                g,
                genderAdapter,
                spinnerGender,
                null, null, requireContext()));
        //Заменить данными из ViewMode(Создать ее)
        questionerViewModel.getComplexityList().observe(getViewLifecycleOwner(), c -> updateArrayAdapter(
                c,
                complexityAdapter,
                spinnerComplexity,
                null, null, requireContext()));

        spinnerGender.setDropDownVerticalOffset(100);
        spinnerComplexity.setDropDownVerticalOffset(100);

    }

    private void updateListeners() {
        editAge.addTextChangedListener(this);
        editCountWeek.addTextChangedListener(this);
        editLengthPool.addTextChangedListener(this);
        editTrainingTime.addTextChangedListener(this);
        editCountTrainingOneWeek.addTextChangedListener(this);


        spinnerComplexity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llSave.setEnabled(ValidText.sizeValid(editAge, editCountTrainingOneWeek, editCountWeek, editLengthPool, editTrainingTime));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llSave.setEnabled(ValidText.sizeValid(editAge, editCountTrainingOneWeek, editCountWeek, editLengthPool, editTrainingTime));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        llSave.setOnClickListener(this::clickSave);
    }

    private void clickSave(View v) {
        Customer customerInfo = getArguments().getParcelable("customer");
        String pass = getArguments().getString("pass");
        if (customerInfo == null || pass == null) return;

        //проверяем данные и отправляем их на сервер
        RequestCallBack callBackUI = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                transitionActivity(requireContext(),requireActivity(), MainActivity.class);
            }

            @Override
            public void onError(Object object) {
                Toast.makeText(requireContext(),object.toString(),Toast.LENGTH_LONG).show();
            }
        };
        questionerViewModel.regCustomer(customerInfo,pass,createQuestioner(), callBackUI);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        llSave.setEnabled(ValidText.sizeValid(editAge, editCountTrainingOneWeek, editCountWeek, editLengthPool, editTrainingTime));
    }

    @Override
    public void afterTextChanged(Editable s) {

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
}
