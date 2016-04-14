package com.mumu.alarm.ui;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.mumu.alarm.R;

import butterknife.Bind;

public class HelpActivity extends BaseActivity {

    @Bind(R.id.help_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String text = "注意事项:\n1.软件不要安装在SD上\n2.不要使用耳机，否则声音不能外放\n3.重启手机后，需要重新启动闹钟程序\n4.为防止闹钟被杀死，导致闹钟功能不能正常使用，请把闹钟加入到安全软件白名单或者保护应用列表中";
        textView.setText(text);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected String getTitleContent() {
        return "用户帮助";
    }
}
