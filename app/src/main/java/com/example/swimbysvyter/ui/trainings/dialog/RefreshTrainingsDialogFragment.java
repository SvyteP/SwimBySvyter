package com.example.swimbysvyter.ui.trainings.dialog;

import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.swimbysvyter.R;
import com.example.swimbysvyter.databinding.DialogEditProfileFragmentBinding;
import com.example.swimbysvyter.databinding.DialogRefreshTrainingsFragmentBinding;
import com.example.swimbysvyter.helpers.ModelCallBack;
import com.example.swimbysvyter.ui.trainings.TrainingsViewModel;

public class RefreshTrainingsDialogFragment extends DialogFragment{
    private final RefreshTrainingsDialogViewModel dialogViewModel;
    private DialogRefreshTrainingsFragmentBinding binding;
    private ModelCallBack callBack;
    private View mainView;
    private LinearLayout llUpdate;
    private TextView topMessage, bottomMessage;
    private ImageView closeImg;

    public RefreshTrainingsDialogFragment(ModelCallBack callBack) {
        this.callBack = callBack;
        this.dialogViewModel = new RefreshTrainingsDialogViewModel();
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
        binding = DialogRefreshTrainingsFragmentBinding.inflate(inflater, container, false);
        mainView = binding.getRoot();

        llUpdate = binding.dialogUpdateTrainingBtn;
        topMessage = binding.dialogTopMessage;
        bottomMessage = binding.dialogBottomMessage;
        closeImg = binding.dialogClose;

    }

    private void updateView(){
    }

    private void updateListener(){
        llUpdate.setOnClickListener(this::clickUpdate);
        closeImg.setOnClickListener(this::clickClose);
    }

    private void clickUpdate(View v){
        callBack.success("");
        dismiss();
    }

    private void clickClose(View v){
        Bundle result = new Bundle();
        result.putBoolean("clickCloseRefresh", true);
        getParentFragmentManager().setFragmentResult("resultRefresh", result);
        dismiss();
    }


}
