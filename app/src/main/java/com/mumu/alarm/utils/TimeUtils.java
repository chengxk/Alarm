package com.mumu.alarm.utils;

/**
 * Created by acer on 2016/4/13.
 */
public class TimeUtils {

    public static String formatTime(int i, int i1) {
        StringBuilder builder = new StringBuilder();
        if (i < 10) {
            builder.append("0" + i + ":");
        } else {
            builder.append(i + ":");
        }

        if (i1 < 10) {
            builder.append("0" + i1);
        } else {
            builder.append("" + i1);
        }
        return builder.toString();
    }

    public static String formatRemindCircle( byte[] flags) {
        StringBuilder builder = new StringBuilder();
        if (flags != null) {
            for (int index = 0; index < flags.length; index++) {
                if (flags[index] == 1) {
                    if (index == 0) {
                        builder.append("周日 ");
                    } else if (index == 1) {
                        builder.append("周一 ");
                    } else if (index == 2) {
                        builder.append("周二 ");
                    } else if (index == 3) {
                        builder.append("周三 ");
                    } else if (index == 4) {
                        builder.append("周四 ");
                    } else if (index == 5) {
                        builder.append("周五 ");
                    } else {
                        builder.append("周六");
                    }
                }
            }
        }
        return builder.toString();
    }
}
