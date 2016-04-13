package com.mumu.alarm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mumu.alarm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by acer on 2016/4/11.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Bind(R.id.title_left)
    ImageView titleLeft;
    @Bind(R.id.title_title)
    TextView titleText;
    @Bind(R.id.title_right)
    TextView titleRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        titleLeft.setVisibility(isLeftVisible()? View.VISIBLE : View.GONE);
        titleRight.setVisibility(isRightVisible()? View.VISIBLE : View.GONE);
        titleText.setText(getTitleContent());
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 标题栏左边的返回按钮是否显示
     * @return
     */
    protected boolean isLeftVisible(){
        return true;
    }

    /**
     * 获取标题内容
     * @return
     */
    protected String getTitleContent(){
        return  getResources().getString(R.string.app_name);
    }

    /**
     * 标题栏右边的文字是否显示
     * @return
     */
    protected boolean isRightVisible(){
        return false;
    }

    protected abstract int getLayoutId();
}
