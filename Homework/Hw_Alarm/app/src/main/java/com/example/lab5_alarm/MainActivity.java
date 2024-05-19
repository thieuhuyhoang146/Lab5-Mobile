package com.example.lab5_alarm;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_TAG = "SharedPrefs";
    private static final String ALARM_TAG = "alarm";
    FloatingActionButton addAlarmBtn;

    ArrayList<Alarm> alarms;

    ListView alarmListView;

    Stack<Integer> daysOfWeek;

    Context mContext;
    AlarmListViewAdapter alarmListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addAlarmBtn = findViewById(R.id.addAlarmBtn);

        mContext = this;


        alarms = getDataFromSharedPreferences();



        alarmListViewAdapter = new AlarmListViewAdapter(alarms);


        alarmListView = findViewById(R.id.listAlarm);

        alarmListView.setAdapter(alarmListViewAdapter);

        daysOfWeek = new Stack<>();
        daysOfWeek.push(Calendar.SUNDAY);
        daysOfWeek.push(Calendar.SATURDAY);
        daysOfWeek.push(Calendar.FRIDAY);
        daysOfWeek.push(Calendar.THURSDAY);
        daysOfWeek.push(Calendar.WEDNESDAY);
        daysOfWeek.push(Calendar.TUESDAY);
        daysOfWeek.push(Calendar.MONDAY);


        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Alarm alarm = (Alarm) alarmListViewAdapter.getItem(i);
                Intent intent = new Intent(mContext, SetAlarmActivity.class);
                intent.putExtra("alarm", alarm);
                intent.putExtra("position", i);
                launchSetAlarmActivity.launch(intent);
            }
        });

        addAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                int hourNow = now.get(Calendar.HOUR);
                int minNow = now.get(Calendar.MINUTE);
                boolean amNow = now.get(Calendar.AM_PM) == 0 ? true : false;
                String desc = "";
                int dayNow = now.get(Calendar.DAY_OF_WEEK);
                //sunday = 1 -> 6 , saturday = 7 -> 5;

//                given = th = 5 delta = 3
//                2 Mo 0
//                3 Tu 1
//                4 We 2
//                5 Th 3
//                6 Fr 4
//                7 Sa 5
//                1 Su 6
                boolean[] dayOn = new boolean[7];
                for (int i=0;i<7;i++) {
                    if (dayNow == daysOfWeek.get(i)) {
                        dayOn[i] = true;
                    } else {
                        dayOn[i] = false;
                    }
                }
                Alarm alarm = new Alarm(hourNow, minNow, amNow, dayOn,desc );
                Intent intent = new Intent(mContext, SetAlarmActivity.class);
                intent.putExtra("alarm", alarm);
                intent.putExtra("position", -1);
                launchSetAlarmActivity.launch(intent);

            }
        });

    }

    ActivityResultLauncher<Intent> launchSetAlarmActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                    }
                }
            }
    );

    private ArrayList<Alarm> getDataFromSharedPreferences(){
        Gson gson = new Gson();
        ArrayList<Alarm> alarmListSaved;
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, this.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(ALARM_TAG, "");

        Type type = new TypeToken<ArrayList<Alarm>>() {}.getType();
        alarmListSaved = gson.fromJson(jsonPreferences, type);
        if (alarmListSaved == null) {
            alarmListSaved = new ArrayList<>();
        }
        return alarmListSaved;
    }

    private void setDataToSharedPreferences(Alarm alarm){
        Gson gson = new Gson();
        String jsonAlarm = gson.toJson(alarm);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String jsonSaved = sharedPref.getString(ALARM_TAG, "");
        String jsonAlarmToAdd = gson.toJson(alarm);

        JSONArray jsonArrayAlarm= new JSONArray();

        try {
            if(jsonSaved.length()!=0){
                jsonArrayAlarm = new JSONArray(jsonSaved);
            }
            jsonArrayAlarm.put(new JSONObject(jsonAlarmToAdd));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        editor.putString(ALARM_TAG, gson.toJson(jsonArrayAlarm));
        editor.commit();
    }

    private void overwriteArrayDataToSharedPreferences(ArrayList<Alarm> listAlarm) {
        Gson gson = new Gson();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(PREFS_TAG, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
//        editor.commit();
        JSONArray jsonArrayAlarm = new JSONArray(listAlarm);
        editor.putString(ALARM_TAG, gson.toJson(jsonArrayAlarm));
        editor.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (alarms.size()==0) {
            ((TextView) findViewById(R.id.textNoAlarm)).setVisibility(View.VISIBLE);
        } else {
            ((TextView) findViewById(R.id.textNoAlarm)).setVisibility(View.GONE);
        }
    }
}