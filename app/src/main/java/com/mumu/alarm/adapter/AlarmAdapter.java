package com.mumu.alarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mumu.alarm.R;
import com.mumu.alarm.bean.Alarm;
import com.mumu.alarm.utils.TimeUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by acer on 2016/4/12.
 */
public class AlarmAdapter extends BaseAdapter {

    private List<Alarm> list;
    private Context context;
    private LayoutInflater inflater;

    public AlarmAdapter(List<Alarm> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.alarm_item_layout, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Alarm alarm = list.get(i);
        viewHolder.alarmTimeText.setText(TimeUtils.formatTime(alarm.getAlarmHour() , alarm.getAlarmMinute()));
        viewHolder.alarmDescriptionText.setText(TimeUtils.formatRemindCircle(alarm.getAlarmCircle()));
        return view;
    }

    public static class ViewHolder {
        @Bind(R.id.alarm_time)
        TextView alarmTimeText;
        @Bind(R.id.alarm_description)
        TextView alarmDescriptionText;
//        @Bind(R.id.toggle_button)
//        ToggleButton toggleButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
