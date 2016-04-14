package com.mumu.alarm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mumu.alarm.R;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;

public class SettingsActivity extends BaseActivity {

    @Bind(R.id.setting_list_view)
    ListView listView;

    private ArrayAdapter<String> adapter;

    private String[] settings = {"背景设置", "建议与反馈", "用户帮助", "检查更新", "关于"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, settings);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                switch (i) {
                    case 0:
                        Toast.makeText(SettingsActivity.this, "尚未实现", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        FeedbackAgent agent = new FeedbackAgent(SettingsActivity.this);
                        agent.startFeedbackActivity();
                        break;
                    case 2:
                        intent = new Intent(SettingsActivity.this, HelpActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        UmengUpdateAgent.update(SettingsActivity.this);
                        break;
                    case 4:
                        intent = new Intent(SettingsActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected String getTitleContent() {
        return "设置";
    }

}
