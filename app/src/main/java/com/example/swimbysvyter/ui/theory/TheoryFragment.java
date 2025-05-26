package com.example.swimbysvyter.ui.theory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.swimbysvyter.databinding.FragmentTheoryBinding;

public class TheoryFragment extends Fragment {

    private FragmentTheoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TheoryViewModel theoryViewModel =
                new ViewModelProvider(this).get(TheoryViewModel.class);

        binding = FragmentTheoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}