package com.example.microcampus;

import android.os.Bundle;

import com.example.microcampus.demo.service.DataService;
import com.example.microcampus.demo.service.impl.DataServiceImpl;
import com.example.microcampus.demo.util.SharedHander;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private DataService dataService;
    private SharedHander sharedHander;
    private MainViewModel mainViewModel;

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

        initAutoLogin();

    }

    @Override
    protected void onDestroy() {
        if (!sharedHander.getBoolean("autoLogin")) {
            dataService = new DataServiceImpl(this);
            dataService.deleteAllInformation();
            dataService.closeDB();
        }
        super.onDestroy();
    }

    private void initAutoLogin() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        sharedHander = new SharedHander(this, "student");

        mainViewModel.setLoginFlag(sharedHander.getBoolean("autoLogin"));
    }

}