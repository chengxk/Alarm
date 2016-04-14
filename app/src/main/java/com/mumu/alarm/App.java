package com.mumu.alarm;

import android.app.Application;

import com.mumu.alarm.bean.Ring;
import com.mumu.alarm.bean.dao.DaoMaster;
import com.mumu.alarm.bean.dao.DaoSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/4/11.
 */
public class App extends Application {

    private DaoMaster daoMaster;
    private DaoSession daoSession;

    private static App app;

    private List<Ring> ringList = new ArrayList<>();
    private List<Ring> userRingList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mumu-db", null);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public static App getApp() {
        return app;
    }

    public List<Ring> getRingList() {
        return ringList;
    }

    public List<Ring> getUserRingList() {
        return userRingList;
    }
}
