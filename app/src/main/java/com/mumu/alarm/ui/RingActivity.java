package com.mumu.alarm.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mumu.alarm.R;
import com.mumu.alarm.adapter.FragmentsAdapter;
import com.mumu.alarm.bean.Alarm;
import com.mumu.alarm.ui.fragment.SystemRingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RingActivity extends BaseActivity {

    @Bind(R.id.ring_tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.ring_view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Alarm alarm = (Alarm) getIntent().getSerializableExtra("alarm");
        Fragment fragment1 = SystemRingFragment.newInstance(alarm);
        Fragment fragment2 = SystemRingFragment.newInstance(alarm);

        List<Fragment> list = new ArrayList<>();
        list.add(fragment1);
        list.add(fragment2);
        List<String> titleList = new ArrayList<>();
        titleList.add("系统铃声");
        titleList.add("本地铃声");
        FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager(), list, titleList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ring;
    }

    @Override
    protected String getTitleContent() {
        return "铃声设置";
    }
}
