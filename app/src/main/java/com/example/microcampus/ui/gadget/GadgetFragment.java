package com.example.microcampus.ui.gadget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.microcampus.MainViewModel;
import com.example.microcampus.R;

import java.util.Objects;

public class GadgetFragment extends Fragment {
    private MainViewModel mainViewModel;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        root = inflater.inflate(R.layout.fragment_gadget, container, false);

        initVar();

        return root;
    }

    private void initVar() {

    }
}