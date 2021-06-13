package com.example.microcampus.ui.gadget;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.microcampus.R;
import com.example.microcampus.demo.service.DataService;
import com.example.microcampus.demo.service.impl.DataServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity {
    private Spinner semester;
    private ListView item_list;
    private DataService dataService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        initVar();

        semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;

                List<Map<String, Object>> data = dataService.getScoresBySemester(position);
                SimpleAdapter simpleAdapter = new SimpleAdapter(ScoreActivity.this, data, R.layout.score_item,
                        new String[]{"name", "score"}, new int[]{R.id.name, R.id.score});
                item_list.setAdapter(simpleAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    private void initVar() {
        semester = findViewById(R.id.semester);
        item_list = findViewById(R.id.item_list);
        dataService = new DataServiceImpl(this);
    }

}
