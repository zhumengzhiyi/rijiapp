package com.example.jump;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView tv_database;
    private String mDatabaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TimePicker timePicker = (TimePicker) findViewById(R.id.time);
        timePicker.setIs24HourView(true);
        Button button = (Button) findViewById(R.id.set);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置闹钟
                Intent intent = new Intent(MainActivity.this, AlarmActivty.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Calendar c = Calendar.getInstance();//获取日历
                c.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());

                c.set(Calendar.MINUTE, timePicker.getCurrentHour());

                alarm.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);//设置闹钟
                Toast.makeText(MainActivity.this, "闹钟设置成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
}