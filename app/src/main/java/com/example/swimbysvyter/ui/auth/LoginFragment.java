package com.example.swimbysvyter.ui.auth;

import static com.example.swimbysvyter.SwimApp.disableBtn;
import static com.example.swimbysvyter.SwimApp.enabledBtn;
import static com.example.swimbysvyter.SwimApp.invalidEditText;
import static com.example.swimbysvyter.SwimApp.swimAPI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.swimbysvyter.MainActivity;
import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.FragmentLoginBinding;
import com.example.swimbysvyter.helpers.EditTextUtils;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.helpers.ValidText;


public class LoginFragment extends Fragment {
    private final LogInViewModel logInViewModel;
    private FragmentLoginBinding binding;
    private View mainView;
    private EditText login;
    private EditText pass;
    private TextView forgetPass;
    private TextView linkSingUp;
    private LinearLayout logInBtn;
    private LinearLayout loginEyeLL;
    private NavController navController;
    private ImageView loginEyeImg;

    public LoginFragment() {
        this.logInViewModel = new LogInViewModel();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initView(inflater,container,savedInstanceState);
        updateView();
        updateListener();


        return mainView;
    }

    private void initView(@NonNull LayoutInflater inflater,
                          @Nullable ViewGroup container,
                          @Nullable Bundle savedInstanceState){
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        mainView = binding.getRoot();
        login = binding.loginLoginEdit;
        pass = binding.loginPassEdit;
        forgetPass = binding.loginForgetPassTxt;
        linkSingUp = binding.loginLinkSingUpTxt;
        logInBtn = binding.loginLogInBtn;
        loginEyeLL = binding.loginEyeLl;
        loginEyeImg = binding.loginEyeImg;

    }

    private void updateView(){
        navController =  NavHostFragment.findNavController(this);
    }

    private void updateListener(){
        linkSingUp.setOnClickListener(v -> clickLinkSingUp(v, navController));

        forgetPass.setOnClickListener(v ->{

        });

        logInBtn.setOnClickListener(v -> clickLogInBtn(v));
        logInBtn.setEnabled(false);

        TextWatcher validWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (login.length() > 0 &&
                        pass.length() > 0 &&
                        ValidText.emailValid(login.getText().toString())){
                    enabledBtn(logInBtn);
                } else {
                    disableBtn(logInBtn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        login.addTextChangedListener(validWatcher);
        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                logInViewModel.setLogin(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        pass.addTextChangedListener(validWatcher);
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                logInViewModel.setPass(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginEyeImg.setOnClickListener(v -> EditTextUtils.clickEyeImg(v,pass));
    }

    private void clickLinkSingUp(View v, NavController controller){
        controller.navigate(R.id.action_loginFragment_to_regFragment);
    }

    private void clickLogInBtn(View v){
        logInViewModel.sendLogInInfo(new ModelCallBack() {
            @Override
            public void success(Object o) {
                startMainMenu();
            }

            @Override
            public void error(Object o) {
                Toast.makeText(requireContext(),o.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startMainMenu(){
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }


}
