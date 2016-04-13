package com.mumu.alarm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mumu.alarm.Constants;
import com.mumu.alarm.R;
import com.mumu.alarm.bean.Alarm;
import com.mumu.alarm.utils.TimeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by acer on 2016/4/11.
 */
public class SetAlarmAdapter extends BaseAdapter {

    private Alarm alarm;
    private Context context;
    private LayoutInflater layoutInflater;

    private String[] arr;

    public SetAlarmAdapter(Context context, Alarm alarm) {
        this.alarm = alarm;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        arr = context.getResources().getStringArray(R.array.set_alarm_array);
    }

    @Override
    public int getCount() {
        return arr.length;
    }

    @Override
    public Object getItem(int i) {
        return arr[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.set_alarm_list_item, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        switch (position) {
            case 0:
                holder.description.setText(alarm.getName());
                break;
            case 1:
                byte[] flags = alarm.getAlarmCircle();
                holder.description.setText(TimeUtils.formatRemindCircle(flags));
                break;
            case 2:
                int remindMode = alarm.getRemindMode();
                String str;
                if (remindMode == Constants.ALARM_REMIND_RING) {
                    str = "响铃";
                } else if (remindMode == Constants.ALARM_REMIND_VIBRATE) {
                    str = "震动";
                } else {
                    str = "响铃加震动";
                }
                holder.description.setText(str);
                break;
            case 3:
                holder.description.setText(alarm.getRingName());
                break;
            case 4:
                holder.description.setText(alarm.getSleepy() + "分钟");
                break;
        }
        holder.title.setText(arr[position]);
        return view;
    }



    @Override
    public boolean isEnabled(int position) {
        if (position == 3 && alarm.getRemindMode() == Constants.ALARM_REMIND_VIBRATE) {
            return false;
        }
        return super.isEnabled(position);
    }

    public void setData(Alarm alarm) {
        this.alarm = alarm;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        @Bind(R.id.set_alarm_list_item_title)
        TextView title;
        @Bind(R.id.set_alarm_list_item_description)
        TextView description;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
