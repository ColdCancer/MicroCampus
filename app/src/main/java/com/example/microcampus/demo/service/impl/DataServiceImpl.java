package com.example.microcampus.demo.service.impl;

import com.example.microcampus.demo.service.DataService;

public class DataServiceImpl implements DataService {
    private String account, password;

    @Override
    public boolean login(String username, String password) {
        this.account = username;
        this.password = password;
        return "student".equals(username) && "student".equals(password);
    }

}
