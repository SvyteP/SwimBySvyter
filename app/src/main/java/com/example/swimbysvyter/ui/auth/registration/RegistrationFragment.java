package com.example.swimbysvyter.ui.auth.registration;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.FragmentRegistrationBinding;
import com.example.swimbysvyter.entity.Customer;
import com.example.swimbysvyter.helpers.EditTextUtils;
import com.example.swimbysvyter.helpers.ValidText;

public class RegistrationFragment extends Fragment implements TextWatcher{
    private final RegistrationViewModel registrationViewModel;
    private FragmentRegistrationBinding binding;
    private NavController navController;
    private View mainView;

    private EditText editLogin, editPass, editEmail, editPassRepeat;
    private CheckBox checkConfid;
    private ImageView eyeImgRepeat, eyeImg;
    private LinearLayout createAccountBtn;


    public RegistrationFragment() {
        this.registrationViewModel = new RegistrationViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater,container,savedInstanceState);
        updateView();
        updateListeners();
        return mainView;
    }

    private void initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = FragmentRegistrationBinding.inflate(inflater,container,false);
        mainView = binding.getRoot();

        editEmail = binding.regEmailEdit;
        editLogin = binding.regLoginEdit;
        editPass = binding.regPassEdit;
        editPassRepeat = binding.regPassEditRepeat;

        eyeImg = binding.regEyeImg;
        eyeImgRepeat = binding.regEyeImgRepeat;

        checkConfid = binding.regCheckBox;

        createAccountBtn = binding.regCreateAccountBtn;
        createAccountBtn.setEnabled(false);

        navController = NavHostFragment.findNavController(this);
    }

    private void updateView(){


    }

    private void updateListeners(){
        editPass.addTextChangedListener(this);
        editPassRepeat.addTextChangedListener(this);
        editLogin.addTextChangedListener(this);
        editEmail.addTextChangedListener(this);

        mainView.findViewById(R.id.reg_link_sign_in_txt).setOnClickListener(v -> {
            navController.navigate(R.id.action_regFragment_to_loginFragment);
        });

        checkConfid.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkFieldsFilled();
        });



        eyeImg.setOnClickListener(v -> EditTextUtils.clickEyeImg(v,editPass));
        eyeImgRepeat.setOnClickListener(v -> EditTextUtils.clickEyeImg(v,editPassRepeat));

        createAccountBtn.setOnClickListener(this::clickCreateAccount);
    }

    private void clickCreateAccount(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("customer",new Customer(editLogin.getText().toString(),editEmail.getText().toString()));
        bundle.putString("pass",editPass.getText().toString());
        navController.navigate(R.id.action_regFragment_to_questionerFragment,bundle);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
       checkFieldsFilled();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void checkFieldsFilled(){
        createAccountBtn.setEnabled(ValidText.sizeValid(editEmail,editLogin,editPass) &&
                editPass.getText().toString().equals(editPassRepeat.getText().toString()) &&
                ValidText.emailValid(editEmail.getText().toString()) &&
                checkConfid.isChecked());
    }
}
