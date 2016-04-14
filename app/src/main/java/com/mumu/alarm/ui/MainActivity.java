package com.mumu.alarm.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.mumu.alarm.App;
import com.mumu.alarm.R;
import com.mumu.alarm.adapter.AlarmAdapter;
import com.mumu.alarm.bean.Alarm;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_list_view)
    ListView listView;
    @Bind(R.id.main_add_alarm)
    ImageView addAlarm;

    private List<Alarm> list;
    private AlarmAdapter alarmAdapter;

    private AlertDialog alertDialog;

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
                intent.putExtra("alarm", alarm);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Alarm alarm = (Alarm) adapterView.getAdapter().getItem(i);

                showDialog(alarm);
                return true;
            }
        });

        titleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        //检查更新
        UmengUpdateAgent.update(this);
        //用户反馈
        FeedbackAgent agent = new FeedbackAgent(this);
        agent.sync();

    }

    private void showDialog(final Alarm alarm) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this).setTitle("删除").setMessage("删除该闹钟").create();
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    App.getApp().getDaoSession().getAlarmDao().delete(alarm);
                    list = App.getApp().getDaoSession().getAlarmDao().queryBuilder().build().list();

                    alarmAdapter.setData(list);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }

        alertDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        list = App.getApp().getDaoSession().getAlarmDao().queryBuilder().build().list();
        if (list != null && !list.isEmpty()) {
            alarmAdapter = new AlarmAdapter(list, this);
            listView.setAdapter(alarmAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
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

    @Override
    protected boolean isRightVisible() {
        return true;
    }
}
