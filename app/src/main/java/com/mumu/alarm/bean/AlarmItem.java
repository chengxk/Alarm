package com.mumu.alarm.bean;

/**
 * Created by acer on 2016/4/11.
 */
public class AlarmItem {
    //显示名称
    private String name;
    //多媒体路径
    private String path;
    //贪睡时长，分钟
    private int sleepy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSleepy() {
        return sleepy;
    }

    public void setSleepy(int sleepy) {
        this.sleepy = sleepy;
    }
}
