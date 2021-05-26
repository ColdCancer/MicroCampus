package com.example.microcampus.ui.message;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.microcampus.MainActivity;
import com.example.microcampus.R;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button login, cancel;
    private CheckBox remember, autoLogin;
//    private boolean rememberFlag, autoLoginFlag;
    private MutableLiveData<Boolean> mLoginFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                if ("student".equals(strUsername) && "student".equals(strPassword)) {
                    // TODO: 2021/5/26 add file save faction
                    SharedPreferences sharedPreferences = getSharedPreferences("student", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("account", strUsername);
                    editor.putString("password", strPassword);
                    editor.putBoolean("remember", remember.isChecked());
                    editor.putBoolean("autoLogin", autoLogin.isChecked());
                    editor.apply();

                    intent.putExtra("login", true);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号、密码错误！", Toast.LENGTH_SHORT).show();
                    password.setText("");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        setResult(RESULT_CANCELED);
        finish();
        super.onDestroy();
    }

    private void init() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        cancel = (Button) findViewById(R.id.cancel);
        remember = (CheckBox) findViewById(R.id.remember);
        autoLogin = (CheckBox) findViewById(R.id.autoLogin);
    }
}
