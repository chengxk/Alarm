package com.mumu.alarm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mumu.alarm.App;
import com.mumu.alarm.R;
import com.mumu.alarm.bean.Alarm;

import java.util.List;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.add_alarm)
    Button addAlarmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Alarm> list = App.getApp().getDaoSession().getAlarmDao().queryBuilder().build().list();
                Intent intent = new Intent(MainActivity.this, SetAlarmActivity.class);
                if (list != null && !list.isEmpty()) {
                    Toast.makeText(MainActivity.this , "size:"+list.size() ,Toast.LENGTH_SHORT).show();
                    intent.putExtra("alarm" , list.get(list.size()-1));
                }
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
