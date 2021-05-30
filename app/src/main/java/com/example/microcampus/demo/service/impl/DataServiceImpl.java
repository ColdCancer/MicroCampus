package com.example.microcampus.demo.service.impl;

import com.example.microcampus.demo.bean.Lesson;
import com.example.microcampus.demo.service.DataService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataServiceImpl implements DataService {

    @Override
    public boolean login(String username, String password) {
        return "student".equals(username) && "student".equals(password);
    }

    @Override
    public Map<String, String> getBaseInformation(String username) {
        Map<String, String> result = new HashMap<>();
        result.put("account", "201820800124");
        result.put("name", "汪星人");
        result.put("college", "信息工程学院");
        return result;
    }

    @Override
    public List<Lesson> getShceduleByDate(Date date) {

        return null;
    }
}
