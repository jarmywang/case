package com.example.demo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.demo.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivityTabCase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab_case);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

//        TabLayout.Tab tab0 = tabs.newTab().setText("默认");
//        tabs.addTab(tab0);
        TabLayout.Tab tab0 = tabs.getTabAt(0);
        tab0.setText("默认");

//        TabLayout.Tab tab1 = tabs.newTab();
        TabLayout.Tab tab1 = tabs.getTabAt(1);
        tab1.setCustomView(R.layout.tab_custom);
        TextView tab1Title = tab1.getCustomView().findViewById(R.id.tv_tab_title);
        tab1Title.setText("自定义");
//        tabs.addTab(tab1);

        TabLayout.Tab tab2 = tabs.getTabAt(2);
        tab2.setCustomView(R.layout.tab_custom);
        TextView tab2Title = tab2.getCustomView().findViewById(R.id.tv_tab_title);
        tab2.getCustomView().setTag("123");
        tab2Title.setText("自定义2");

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) return;
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_tab_title);
                tvTitle.setTextColor(getResources().getColor(R.color.secondary_color));
                if (tab.getCustomView().getTag() != null)
                Toast.makeText(MainActivityTabCase.this, tab.getCustomView().getTag().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() == null) return;
                TextView tvTitle = tab.getCustomView().findViewById(R.id.tv_tab_title);
                tvTitle.setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        try {
            Handler handler = new Handler();
            String a = null;
            new Thread(() -> {
//                handler.post(() -> {
                    a.charAt(10);
//                });
            }).start();
        } catch (Exception e) {
            Log.e("", "eeeeeeeeeeee");
            e.printStackTrace();
        }
    }
}