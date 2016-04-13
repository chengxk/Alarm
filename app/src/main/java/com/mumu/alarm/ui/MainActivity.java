package com.mumu.alarm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mumu.alarm.App;
import com.mumu.alarm.R;
import com.mumu.alarm.adapter.AlarmAdapter;
import com.mumu.alarm.bean.Alarm;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_list_view)
    ListView listView;
    @Bind(R.id.main_add_alarm)
    ImageView addAlarm;

    private List<Alarm> list;
    private AlarmAdapter alarmAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                Alarm alarm = (Alarm) adapterView.getAdapter().getItem(i);
                intent.putExtra("alarm"  , alarm);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        list = App.getApp().getDaoSession().getAlarmDao().queryBuilder().build().list();
        if (list != null && !list.isEmpty()) {
            alarmAdapter = new AlarmAdapter(list , this);
            listView.setAdapter(alarmAdapter);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isLeftVisible() {
        return false;
    }
}
