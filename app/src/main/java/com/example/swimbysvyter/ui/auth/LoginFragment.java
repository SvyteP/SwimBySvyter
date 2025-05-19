package com.example.swimbysvyter.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private View mainView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initView(inflater,container,savedInstanceState);

        mainView.findViewById(R.id.login_link_sing_up_txt).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_regFragment);
        });

        return mainView;
    }

    private void initView(@NonNull LayoutInflater inflater,
                          @Nullable ViewGroup container,
                          @Nullable Bundle savedInstanceState){
        binding = FragmentLoginBinding.inflate(inflater,container,false);
        mainView = binding.getRoot();
    }

}
