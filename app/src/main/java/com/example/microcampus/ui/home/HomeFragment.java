package com.example.microcampus.ui.home;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.microcampus.R;

import org.w3c.dom.Text;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private LinearLayout schedule_col, schedule_row;
    private LayoutInflater layoutInflater;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        init();
        buildLayout();

//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//                System.out.println("the home is running.");
//            }
//
//
//        });
        return root;
    }

    private void buildLayout() {
        Resources resources = getResources();
        String[] days = resources.getStringArray(R.array.days);

        for (int i = 0; i < 7; i++) {
            View schedule_day = layoutInflater.inflate(R.layout.schedule_day, null, false);
            TextView content = schedule_day.findViewById(R.id.content);
            content.setText(days[i]);
            schedule_col.addView(schedule_day);
        }

        days = resources.getStringArray(R.array.cases);
        for (int i = 0; i < 6; i++) {
            View schedule_case = layoutInflater.inflate(R.layout.schedule_case, null, false);
            TextView content = schedule_case.findViewById(R.id.content);
            content.setText(days[i]);
            schedule_row.addView(schedule_case);
        }
    }

    private void init() {
        schedule_col = (LinearLayout) root.findViewById(R.id.schedule_col);
        schedule_row = (LinearLayout) root.findViewById(R.id.schedule_row);
        layoutInflater = LayoutInflater.from(root.getContext());
    }

}