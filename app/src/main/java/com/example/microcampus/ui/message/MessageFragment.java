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
import androidx.lifecycle.ViewModelProviders;

import com.example.microcampus.R;
import com.example.microcampus.demo.service.DataService;
import com.example.microcampus.demo.service.impl.DataServiceImpl;

import java.util.Map;
import java.util.Objects;

public class MessageFragment extends Fragment {
    private MessageViewModel notificationsViewModel;
    private DataService dataService;

    private View root;
    private Button login, logout;
    private LinearLayout message_content;
    private TextView message_name, message_account, message_college;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) { // RESULT_OK
                Toast.makeText(getActivity(), "账号登录成功！", Toast.LENGTH_SHORT).show();

                boolean flag = Objects.requireNonNull(data).getBooleanExtra("login", false);
                String account = data.getStringExtra("account");
                notificationsViewModel.setLoginFlag(flag);
                notificationsViewModel.setBaseInformation(dataService.getBaseInformation(account));

                loadingStudentInformation();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MessageViewModel.class);
        root = inflater.inflate(R.layout.fragment_message, container, false);

        initVar();

        message_content.setVisibility(View.INVISIBLE);
        loadingStudentInformation();

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
                Toast.makeText(getContext(), "账号登出成功！", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).
                        getSharedPreferences("student", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("autoLogin", false);
                editor.apply();
            }
        });

        return root;
    }

    private void loadingStudentInformation() {
        if (!notificationsViewModel.checkLogin()) return;

        login.setVisibility(View.INVISIBLE);
        message_content.setVisibility(View.VISIBLE);

        Map<String, String> information = notificationsViewModel.getBaseInformation();
        message_name.setText("姓名：" + information.get("name"));
        message_account.setText("学号：" + information.get("account"));
        message_college.setText("学院：" + information.get("college"));
    }


    private void initVar() {
        dataService = new DataServiceImpl();

        login = (Button) root.findViewById(R.id.login);
        logout = (Button) root.findViewById(R.id.logout);
        message_content = (LinearLayout) root.findViewById(R.id.message_content);

        message_name = root.findViewById(R.id.message_name);
        message_account = root.findViewById(R.id.message_account);
        message_college = root.findViewById(R.id.message_college);
    }
}