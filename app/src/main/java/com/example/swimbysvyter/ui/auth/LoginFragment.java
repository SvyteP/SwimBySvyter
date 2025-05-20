package com.example.swimbysvyter.ui.auth;

import static com.example.swimbysvyter.SwimApp.swimAPI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.swimbysvyter.MainActivity;
import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.FragmentLoginBinding;
import com.example.swimbysvyter.services.api.RequestCallBack;
import com.example.swimbysvyter.services.api.SwimAPI;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private View mainView;
    private EditText login;
    private EditText pass;
    private TextView forgetPass;
    private TextView linkSingUp;
    private LinearLayout logInBtn;
    private NavController navController;



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
    }

    private void updateView(){
        navController =  NavHostFragment.findNavController(this);
    }

    private void updateListener(){
        linkSingUp.setOnClickListener(v -> clickLinkSingUp(v, navController));

        forgetPass.setOnClickListener(v ->{

        });

        logInBtn.setOnClickListener(v -> clickLogInBtn(v, login, pass));
    }

    private void clickLinkSingUp(View v, NavController controller){
        controller.navigate(R.id.action_loginFragment_to_regFragment);
    }

    private void clickLogInBtn(View v,EditText editLogin, EditText editPass){
        String login = editLogin.getText().toString();
        String pass = editPass.getText().toString();

        if (login.isBlank() || pass.isBlank()) {
            editLogin.setError("Поле обязательно для заполнения");
        }

        RequestCallBack callBack = new RequestCallBack() {
            @Override
            public void onSuccess(Object object) {
                startMainMenu();
            }

            @Override
            public void onError(Object object) {
                Log.e("LoginFragment","clickLogInBtn onError: " + object.toString());
            }
        };

        //swimAPI.Login(login,pass,callBack);
    }

    private void startMainMenu(){
        Intent intent = new Intent(requireContext(), MainActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }


}
