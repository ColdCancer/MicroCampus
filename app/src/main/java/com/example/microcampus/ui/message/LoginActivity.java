package com.example.microcampus.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.microcampus.MainActivity;
import com.example.microcampus.R;
import com.example.microcampus.demo.service.DataService;
import com.example.microcampus.demo.service.impl.DataServiceImpl;
import com.example.microcampus.demo.util.SharedHander;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button login, cancel;
    private CheckBox remember, autoLogin;
//    private SharedPreferences sharedPreferences;
    SharedHander sharedHander;
    private DataService dataService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVar();

        if (sharedHander.getBoolean("remember")) {
            username.setText(sharedHander.getString("account"));
            password.requestFocus();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                if (dataService.login(strUsername, strPassword)) {
                    // TODO: 2021/5/26 add file save faction
                    sharedHander.putString("account", strUsername);
                    sharedHander.putString("password", strPassword);
                    sharedHander.putBoolean("remember", remember.isChecked());
                    sharedHander.putBoolean("autoLogin", autoLogin.isChecked());

                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号、密码错误！", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_CANCELED);
        dataService.closeDB();
        finish();
        super.onDestroy();
    }

    private void initVar() {
//        sharedPreferences = getSharedPreferences("student", Context.MODE_PRIVATE);
        sharedHander = new SharedHander(this, "student");
        dataService = new DataServiceImpl(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        cancel = findViewById(R.id.cancel);
        remember = findViewById(R.id.remember);
        autoLogin = findViewById(R.id.autoLogin);
    }
}
