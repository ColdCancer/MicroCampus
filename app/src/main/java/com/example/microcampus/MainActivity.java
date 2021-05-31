package com.example.microcampus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.microcampus.demo.util.DatabaseHelper;
import com.example.microcampus.demo.util.SharedHander;
import com.example.microcampus.ui.gadget.GadgetFragment;
import com.example.microcampus.ui.gadget.GadgetViewModel;
import com.example.microcampus.ui.home.HomeViewModel;
import com.example.microcampus.ui.message.MessageViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hind activity top fence
//        Objects.requireNonNull(this.getSupportActionBar()).hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_gadget, R.id.navigation_message)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        final DatabaseHelper databaseHelper = new DatabaseHelper(this, "microcampus", null, 1);

        initAutoLogin();

    }

    private void initAutoLogin() {
        MessageViewModel messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        SharedHander sharedHander = new SharedHander(this, "student");

        messageViewModel.setLoginFlag(sharedHander.getBoolean("autoLogin"));
    }

}