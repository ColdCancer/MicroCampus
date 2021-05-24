package com.example.microcampus.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.microcampus.R;

public class MessageFragment extends Fragment {

    private MessageViewModel notificationsViewModel;
    private View root;
    private Button login, logout;
    private LinearLayout message_content;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MessageViewModel.class);
        root = inflater.inflate(R.layout.fragment_message, container, false);

        init();

        message_content.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.INVISIBLE);
                message_content.setVisibility(View.VISIBLE);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message_content.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
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