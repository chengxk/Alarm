package com.mumu.alarm.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.mumu.alarm.R;

import butterknife.Bind;

public class AboutActivity extends BaseActivity {

    @Bind(R.id.about_text)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView.setText(String.format(getResources().getString(R.string.about), getResources().getString(R.string.app_name)));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected String getTitleContent() {
        return "关于";
    }
}
