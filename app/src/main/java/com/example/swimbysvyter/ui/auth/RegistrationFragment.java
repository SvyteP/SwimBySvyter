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
import com.example.swimbysvyter.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {
    private FragmentRegistrationBinding binding;
    private View mainView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView(inflater,container,savedInstanceState);

        mainView.findViewById(R.id.reg_link_sign_in_txt).setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_regFragment_to_loginFragment);
        });
        return mainView;
    }

    private void initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = FragmentRegistrationBinding.inflate(inflater,container,false);
        mainView = binding.getRoot();
    }
}
