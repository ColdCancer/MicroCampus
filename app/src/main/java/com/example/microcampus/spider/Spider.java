package com.example.microcampus.spider;

import com.example.microcampus.demo.bean.Lesson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Can't use multiple thread
public class Spider {

    public Spider(String account, String password) {
        init(account, password);
    }

    public void init(String account, String password) {
        SpiderSession.account = account;
        SpiderSession.password = password;
    }

    public boolean login(String account, String password, String code) {
        init(account, password);

        return false;
    }

    public List<Lesson> getLessonsByDate(Date date) {
        List<Lesson> lessons = new ArrayList<>();
        // add lesson

        return lessons;
    }
}
