package com.example.lab5_alarm;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import com.example.lab5_alarm.Alarm;

public class AlarmListViewAdapter extends BaseAdapter {


    final ArrayList<Alarm> alarms;

    public AlarmListViewAdapter(ArrayList<Alarm> alarms) {
        this.alarms = alarms;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int i) {
        return alarms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alarms.get(i).uuid;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        View alarmView;
        if (view == null) {
            alarmView = View.inflate(viewGroup.getContext(), R.layout.single_alarm_view, null);
        } else alarmView = view;
        final TextView[] days = {
                alarmView.findViewById(R.id.daysMonday),
                alarmView.findViewById(R.id.daysTuesday),
                alarmView.findViewById(R.id.daysWednesday),
                alarmView.findViewById(R.id.daysThursday),
                alarmView.findViewById(R.id.daysFriday),
                alarmView.findViewById(R.id.daysSatruday),
                alarmView.findViewById(R.id.daysSunday),
        };
        Alarm alarm = (Alarm) getItem(pos);

        ((TextView) alarmView.findViewById(R.id.alarmDescText)).setText(alarm.getDesc());
        String hour = String.format("%d:%d", alarm.getHour(), alarm.getMin());
        ((TextView) alarmView.findViewById(R.id.alarmTimeText)).setText(hour);
        ((TextView) alarmView.findViewById(R.id.alarmAMText)).setText(alarm.am?"AM":"PM");
        for(int i=0;i<8;i++) {
            if(alarm.getDays()[i]) {
                days[i].setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.red));
            }else {
                days[i].setTextColor(ContextCompat.getColor(viewGroup.getContext(), R.color.grey));
            }
        }

        ((CheckBox) alarmView.findViewById(R.id.checkAble)).setChecked(alarm.isAble());


        return alarmView;
    }
}
