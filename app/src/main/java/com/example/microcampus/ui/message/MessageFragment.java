package com.example.microcampus.ui.message;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.microcampus.R;

import java.util.Objects;

public class MessageFragment extends Fragment {
    private MessageViewModel notificationsViewModel;

    private View root;
    private Button login, logout;
    private LinearLayout message_content;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) { // RESULT_OK
                login.setVisibility(View.INVISIBLE);
                message_content.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                boolean flag = Objects.requireNonNull(data).getBooleanExtra("login", false);
                notificationsViewModel.setLoginFlag(flag);
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MessageViewModel.class);

        root = inflater.inflate(R.layout.fragment_message, container, false);

        init();

        message_content.setVisibility(View.INVISIBLE);

        if (notificationsViewModel.checkLogin()) {
            login.setVisibility(View.INVISIBLE);
            message_content.setVisibility(View.VISIBLE);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_content.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
                notificationsViewModel.setLoginFlag(false);
                Toast.makeText(getContext(), "Logout Success", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).
                        getSharedPreferences("student", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("autoLogin", false);
                editor.apply();
            }
        });

        return root;
    }

    private void init() {
        login = (Button) root.findViewById(R.id.login);
        logout = (Button) root.findViewById(R.id.logout);
        message_content = (LinearLayout) root.findViewById(R.id.message_content);
    }
}