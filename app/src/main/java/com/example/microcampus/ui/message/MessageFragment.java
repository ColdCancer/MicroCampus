package com.example.microcampus.ui.message;

import android.app.Activity;
import android.content.Intent;
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
import com.example.microcampus.demo.util.DatabaseHelper;
import com.example.microcampus.demo.util.SharedHander;

import java.util.Map;
import java.util.Objects;

public class MessageFragment extends Fragment {
    private MessageViewModel messageViewModel;
    private DataService dataService;
    private SharedHander sharedHander;

    private View root;
    private Button login, logout;
    private LinearLayout message_content;
    private TextView message_name, message_account, message_college;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) { // RESULT_OK
                Toast.makeText(getActivity(), "账号登录成功！", Toast.LENGTH_SHORT).show();

                // 登录成功，获取用户基本信息并显示
                messageViewModel.setLoginFlag(true);
                messageViewModel.setBaseInformation(dataService.getBaseInformation(
                        sharedHander.getString("account")));

                dataService.updataAllInformation();
                showingStudentInformation();
            }
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel =
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
                messageViewModel.setLoginFlag(false);
                sharedHander.putBoolean("autoLogin", false);
                dataService.deleteAllInformation();
                Toast.makeText(getContext(), "账号登出成功！", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void loadingStudentInformation() {
        // 判断用户是否选择自动登录
        if (!messageViewModel.checkLogin()) return;

        // 判断用户账号密码是否可再登录
        String account = sharedHander.getString("account");
        String password = sharedHander.getString("password");
        if (!dataService.login(account, password)) return;

        // 获取用户的基本信息并保存在 ViewModel 中
        messageViewModel.setBaseInformation(dataService.getBaseInformation(
                sharedHander.getString("account")));

        showingStudentInformation();
    }

    private void showingStudentInformation() {
        login.setVisibility(View.INVISIBLE);
        message_content.setVisibility(View.VISIBLE);

        Map<String, String> information = messageViewModel.getBaseInformation();
        message_name.setText("姓名：" + information.get("name"));
        message_account.setText("学号：" + information.get("account"));
        message_college.setText("学院：" + information.get("college"));
    }


    private void initVar() {
        dataService = new DataServiceImpl(getActivity());
        sharedHander = new SharedHander(getActivity(), "student");

        login = root.findViewById(R.id.login);
        logout = root.findViewById(R.id.logout);
        message_content = root.findViewById(R.id.message_content);
        message_name = root.findViewById(R.id.message_name);
        message_account = root.findViewById(R.id.message_account);
        message_college = root.findViewById(R.id.message_college);
    }
}