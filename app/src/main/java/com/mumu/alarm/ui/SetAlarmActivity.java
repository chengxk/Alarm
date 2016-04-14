package com.mumu.alarm.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mumu.alarm.App;
import com.mumu.alarm.Constants;
import com.mumu.alarm.R;
import com.mumu.alarm.adapter.SetAlarmAdapter;
import com.mumu.alarm.bean.Alarm;
import com.mumu.alarm.ui.widget.CircleTextView;
import com.mumu.alarm.utils.TimeUtils;

import butterknife.Bind;

/**
 * 添加或修改闹钟
 */
public class SetAlarmActivity extends BaseActivity implements View.OnClickListener {

    public static final int ADD_ALARM_TYPE = 0x98;
    public static final int EDIT_ALARM_TYPE = 0x99;

    //    0=编辑闹钟名称，1=闹钟周期，2=提醒方式，4=贪睡
    public static final int EDIT_ALARM_INVALID = -1;
    public static final int EDIT_ALARM_NAME = 0x100;
    public static final int EDIT_ALARM_CIRLCE = 0x101;
    public static final int EDIT_ALARM_REMIND = 0x102;
    public static final int EDIT_ALARM_RING = 0x103;
    public static final int EDIT_ALARM_SLEEPY = 0x104;


    @Bind(R.id.set_alarm_time_info)
    TextView timeInfo;
    @Bind(R.id.set_alarm_time_picker)
    TimePicker timePicker;
    @Bind(R.id.set_alarm_list_view)
    ListView listView;

    //dialog的view
    private EditText editText;
    private ListView singleChoiceListView;
    private LinearLayout remindCircleLayout;
    private TextView dialogTitle;
    private TextView confirm;
    private TextView cancle;

    private CircleTextView zero, one, two, three, four, five, six;

    private Alarm alarm;
    private AlertDialog alertDialog;
    private SetAlarmAdapter setAlarmAdapter;

    private String[] remindModeArray;
    private String[] sleepyArray;

    private int activityType = ADD_ALARM_TYPE;
    private int type = EDIT_ALARM_INVALID;//表示当前显示的dialog的数据是哪一项

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        timePicker.setIs24HourView(true);

        remindModeArray = getResources().getStringArray(R.array.remind_mode_array);
        sleepyArray = getResources().getStringArray(R.array.sleepy_array);

        Intent intent = getIntent();
        if (intent.hasExtra("alarm")) {
            activityType = EDIT_ALARM_TYPE;
            alarm = (Alarm) intent.getSerializableExtra("alarm");
            timePicker.setCurrentHour(alarm.getAlarmHour());
            timePicker.setCurrentMinute(alarm.getAlarmMinute());
        } else {
            activityType = ADD_ALARM_TYPE;
            alarm = new Alarm();
            alarm.setName("自定义");
            alarm.setSleepy(5);
            alarm.setRingName(Constants.DEFAULT_ALARM_NAME);
            alarm.setRingPath(Constants.DEFAULT_ALARM_PATH);
            alarm.setRingType(Constants.ASSERTS_RING_TYPE);
            alarm.setRemindMode(Constants.ALARM_REMIND_RING);
            alarm.setAlarmHour(timePicker.getCurrentHour());
            alarm.setAlarmMinute(timePicker.getCurrentMinute());
            alarm.setCloseAlarmMode(0);
            alarm.setAlarmCircle(new byte[]{0, 1, 1, 1, 1, 1, 0});

            App.getApp().getDaoSession().getAlarmDao().insertOrReplace(alarm);
        }

        setAlarmAdapter = new SetAlarmAdapter(this, alarm);
        listView.setAdapter(setAlarmAdapter);
        Integer currentHour = timePicker.getCurrentHour();
        Integer currentMinute = timePicker.getCurrentMinute();

        timeInfo.setText(TimeUtils.formatTime(currentHour, currentMinute));
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                alarm.setAlarmHour(i);
                alarm.setAlarmMinute(i1);
                App.getApp().getDaoSession().getAlarmDao().insertOrReplace(alarm);
                timeInfo.setText(TimeUtils.formatTime(i, i1));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SetAlarmActivity.this, "i:" + i, Toast.LENGTH_SHORT).show();
                if (i == 0) {
                    type = EDIT_ALARM_NAME;
                    showCustomDialog(EDIT_ALARM_NAME);
                } else if (i == 1) {
                    type = EDIT_ALARM_CIRLCE;
                    showCustomDialog(EDIT_ALARM_CIRLCE);
                } else if (i == 2) {
                    type = EDIT_ALARM_REMIND;
                    showCustomDialog(EDIT_ALARM_REMIND);
                } else if (i == 3) {
                    type = EDIT_ALARM_RING;
                    Intent intent1 = new Intent(SetAlarmActivity.this, RingActivity.class);
                    intent1.putExtra("alarm", alarm);
                    startActivity(intent1);
                } else if (i == 4) {
                    type = EDIT_ALARM_SLEEPY;
                    showCustomDialog(EDIT_ALARM_SLEEPY);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        long id = alarm.getId();
        alarm = App.getApp().getDaoSession().getAlarmDao().load(id);
        setAlarmAdapter.setData(alarm);
    }


    /**
     * 显示不同的dialog
     * 0=编辑闹钟名称，1=闹钟周期，2=提醒方式，4=贪睡
     *
     * @param type
     */
    public void showCustomDialog(int type) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this).create();

            View dialogRoot = LayoutInflater.from(this).inflate(R.layout.set_alarm_dialog, null);
            dialogRoot.setLayoutParams(new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            alertDialog.setView(dialogRoot);
            editText = (EditText) dialogRoot.findViewById(R.id.dialog_edit_name);
            singleChoiceListView = (ListView) dialogRoot.findViewById(R.id.dialog_list_view);
            remindCircleLayout = (LinearLayout) dialogRoot.findViewById(R.id.dialog_remind_circle_layout);

            zero = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_zero);
            one = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_one);
            two = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_two);
            three = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_three);
            four = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_four);
            five = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_five);
            six = (CircleTextView) dialogRoot.findViewById(R.id.dialog_remind_circle_six);

            dialogTitle = (TextView) dialogRoot.findViewById(R.id.dialog_title);
            confirm = (TextView) dialogRoot.findViewById(R.id.dialog_confirm);
            cancle = (TextView) dialogRoot.findViewById(R.id.dialog_cancle);
            confirm.setOnClickListener(this);
            cancle.setOnClickListener(this);

        }

        if (type == EDIT_ALARM_NAME) {//闹钟名称
            editText.setVisibility(View.VISIBLE);
            singleChoiceListView.setVisibility(View.GONE);
            remindCircleLayout.setVisibility(View.GONE);
            dialogTitle.setText("闹钟名称");
        } else if (type == EDIT_ALARM_CIRLCE) {//闹钟周期
            editText.setVisibility(View.GONE);
            singleChoiceListView.setVisibility(View.GONE);
            remindCircleLayout.setVisibility(View.VISIBLE);

            byte[] circle = alarm.getAlarmCircle();
            zero.setChecked(circle[0] == 1 ? true : false);
            one.setChecked(circle[1] == 1 ? true : false);
            two.setChecked(circle[2] == 1 ? true : false);
            three.setChecked(circle[3] == 1 ? true : false);
            four.setChecked(circle[4] == 1 ? true : false);
            five.setChecked(circle[5] == 1 ? true : false);
            six.setChecked(circle[6] == 1 ? true : false);

            dialogTitle.setText("闹钟周期");
        } else if (type == EDIT_ALARM_REMIND) {//提醒方式
            editText.setVisibility(View.GONE);
            singleChoiceListView.setVisibility(View.VISIBLE);
            remindCircleLayout.setVisibility(View.GONE);
            dialogTitle.setText("提醒方式");
            singleChoiceListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, remindModeArray));

            Integer remindMode = alarm.getRemindMode();
            if (remindMode == Constants.ALARM_REMIND_RING) {
                singleChoiceListView.setItemChecked(0, true);
            } else if (remindMode == Constants.ALARM_REMIND_VIBRATE) {
                singleChoiceListView.setItemChecked(1, true);
            } else {
                singleChoiceListView.setItemChecked(3, true);
            }

        } else if (type == EDIT_ALARM_SLEEPY) {//贪睡
            dialogTitle.setText("贪睡");
            editText.setVisibility(View.GONE);
            remindCircleLayout.setVisibility(View.GONE);
            singleChoiceListView.setVisibility(View.VISIBLE);
            singleChoiceListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, sleepyArray));
            int index = 0;
            switch (alarm.getSleepy()) {
                case 1:
                    index = 0;
                    break;
                case 2:
                    index = 1;
                    break;
                case 3:
                    index = 2;
                    break;
                case 5:
                    index = 3;
                    break;
                case 10:
                    index = 4;
                    break;
                case 15:
                    index = 5;
                    break;
                case 30:
                    index = 6;
                    break;
            }
            singleChoiceListView.setItemChecked(index, true);
        }
        alertDialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_alarm;
    }


    @Override
    protected String getTitleContent() {
        return "编辑闹钟";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog = null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_confirm:
                int position = singleChoiceListView.getCheckedItemPosition();
                switch (type) {
                    case EDIT_ALARM_NAME:
                        String name = editText.getText().toString();
                        if (TextUtils.isEmpty(name)) {
                            alarm.setName("自定义");
                        } else {
                            alarm.setName(name);
                        }
                        break;
                    case EDIT_ALARM_CIRLCE:
                        byte[] circle = new byte[7];
                        circle[0] = (byte) (zero.isChecked() ? 1 : 0);
                        circle[1] = (byte) (one.isChecked() ? 1 : 0);
                        circle[2] = (byte) (two.isChecked() ? 1 : 0);
                        circle[3] = (byte) (three.isChecked() ? 1 : 0);
                        circle[4] = (byte) (four.isChecked() ? 1 : 0);
                        circle[5] = (byte) (five.isChecked() ? 1 : 0);
                        circle[6] = (byte) (six.isChecked() ? 1 : 0);
                        alarm.setAlarmCircle(circle);
                        break;
                    case EDIT_ALARM_REMIND:
                        if (position == 0) {
                            alarm.setRemindMode(Constants.ALARM_REMIND_RING);
                        } else if (position == 1) {
                            alarm.setRemindMode(Constants.ALARM_REMIND_VIBRATE);
                        } else {
                            alarm.setRemindMode(Constants.ALARM_REMIND_RING_VIBRATE);
                        }
                        setAlarmAdapter.notifyDataSetChanged();

                        break;
                    case EDIT_ALARM_SLEEPY:
                        String str = sleepyArray[position];
                        // 1 分钟 分隔取出数字
                        alarm.setSleepy(Integer.parseInt(str.split(" ")[0]));
                        break;
                    default:
                        break;
                }
                App.getApp().getDaoSession().getAlarmDao().insertOrReplace(alarm);
                setAlarmAdapter.notifyDataSetChanged();
                alertDialog.dismiss();
                break;
            case R.id.dialog_cancle:
                alertDialog.dismiss();
                break;

        }
    }
}
