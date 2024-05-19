package com.example.lab5_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.HashMap;
import java.util.Map;

public class SetAlarmActivity extends AppCompatActivity {
    Alarm alarm;

    CheckBox mo, tu, we, th, fr, sa, su;

    ImageButton back, save, delete;

    TimePicker timePicker;

    EditText desc;

    CheckBox[] checkBoxes;

    Map<Integer, Integer> maps;

    boolean[] onStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);
        Intent intent = getIntent();
        alarm = (Alarm) intent.getParcelableExtra("alarm");
        checkBoxes = new CheckBox[7];
        maps = new HashMap<>();
        onStates = alarm.days;
        bindVIew();
    }

    private void bindVIew() {

        mo = findViewById(R.id.checkMo); checkBoxes[0] = mo; maps.put(R.id.checkMo, 0);

        tu = findViewById(R.id.checkTu); checkBoxes[1] = tu; maps.put(R.id.checkTu, 1);

        we = findViewById(R.id.checkWe); checkBoxes[2] = we; maps.put(R.id.checkWe, 2);

        th = findViewById(R.id.checkTh); checkBoxes[3] = th; maps.put(R.id.checkTh, 3);

        fr = findViewById(R.id.checkFr); checkBoxes[4] = fr; maps.put(R.id.checkFr, 4);

        sa = findViewById(R.id.checkSa); checkBoxes[5] = sa; maps.put(R.id.checkSa, 5);

        su = findViewById(R.id.checkSu); checkBoxes[6] = su; maps.put(R.id.checkSu, 6);


        CompoundButton.OnCheckedChangeListener mDayCheckListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int position = maps.get(compoundButton.getId());
                alarm.getDays()[position] = b;
            }
        };

        back = findViewById(R.id.backToMainBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        delete = findViewById(R.id.deleteAlarm);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}