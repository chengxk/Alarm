package com.mumu.alarm.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mumu.alarm.Constants;
import com.mumu.alarm.R;
import com.mumu.alarm.adapter.FragmentsAdapter;
import com.mumu.alarm.bean.Alarm;
import com.mumu.alarm.ui.fragment.RingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RingActivity extends BaseActivity implements RingFragment.NotifyRingSelectedListener {

    @Bind(R.id.ring_tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.ring_view_pager)
    ViewPager viewPager;

    Fragment systemFragment;
    Fragment userRingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Alarm alarm = (Alarm) getIntent().getSerializableExtra("alarm");
        systemFragment = RingFragment.newInstance(alarm, Constants.SYSTEM_RING_TYPE);
        userRingFragment = RingFragment.newInstance(alarm, Constants.USER_RING_TYPE);

        List<Fragment> list = new ArrayList<>();
        list.add(systemFragment);
        list.add(userRingFragment);
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

    @Override
    public void onNotifyRingSelected(int position, int type) {
        if (type == Constants.USER_RING_TYPE) {//用户操作本地铃声时触发

        } else {

        }
    }
}
