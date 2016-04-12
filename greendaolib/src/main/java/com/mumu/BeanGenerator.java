package com.mumu;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class BeanGenerator {
    public static void main(String[] args) {

        Schema schema = new Schema(1, "com.mumu.alarm.bean");
        schema.setDefaultJavaPackageDao("com.mumu.alarm.bean.dao");
        createAlarm(schema);

        try {
            new DaoGenerator().generateAll(schema, ".\\app\\src\\main\\java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public long alarmId;
//    //创建或者更新闹钟时间
//    public long createOrUpdate ;
//    public String name;
//
//    //当闹钟周期 0=周日，1=周一，6=周日
//    public int [] alarmCircle;
//    //提醒方式：响铃=0，震动=1，响铃加震动=2
//    public int remindMode;
//    //贪睡时间 单位:分钟
//    public int sleepy;
//    //关闭闹钟方式:0=默认，1=脑筋急转弯，2=回答自定义问题，3=说话
//    public int closeAlarmMode;
//    //铃声url
//    public String ringUrl;
    public static void createAlarm(Schema schema) {
        Entity alarm = schema.addEntity("Alarm");
        alarm.implementsSerializable();
        alarm.addIdProperty().primaryKey().autoincrement();
        alarm.addStringProperty("name");
        alarm.addStringProperty("ringPath");
        alarm.addStringProperty("ringName");
        alarm.addIntProperty("ringType");
        alarm.addIntProperty("remindMode");
        alarm.addIntProperty("sleepy");
        alarm.addIntProperty("closeAlarmMode");
        alarm.addByteArrayProperty("alarmCircle");
    }
}
