package com.mumu.alarm;

/**
 * Created by acer on 2016/4/11.
 */
public class Constants {
    //安卓自带铃声位置
    public static final String ANDROID_ALARM = "/system/media/audio/ringtones";
    //默认铃声
    public static final String DEFAULT_ALARM_PATH = "default.ogg";
    public static final String DEFAULT_ALARM_NAME = "默认铃声";

    public static final int ASSERTS_RING_TYPE = 0x50;
    public static final int SYSTEM_RING_TYPE = 0x51;
    public static final int USER_RING_TYPE = 0x52;

    public static final int ALARM_REMIND_RING = 0x53;
    public static final int ALARM_REMIND_VIBRATE = 0x54;
    public static final int ALARM_REMIND_RING_VIBRATE = 0x55;
}
