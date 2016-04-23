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
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class SettingsActivity extends BaseActivity {

    @Bind(R.id.setting_list_view)
    ListView listView;

    private ArrayAdapter<String> adapter;

    private String[] settings = {"背景设置", "建议与反馈", "用户分享", "用户帮助", "检查更新", "关于"};

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
                        showShare();
                        break;
                    case 3:
                        intent = new Intent(SettingsActivity.this, HelpActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        UmengUpdateAgent.update(SettingsActivity.this);
                        break;
                    case 5:
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

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享内容标题");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

}
