package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class setting_alarm_time extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alarm_time);


        ImageButton setting_common_backbutton7 = (ImageButton) findViewById(R.id.setting_common_backbutton7);
        setting_common_backbutton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setting_alarm.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_weather.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), recommended_music.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calender.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_user.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setting.class);
                startActivity(intent);
            }
        });

        Button setting_alarm_button1 = findViewById(R.id.setting_alarm_button1);
        setting_alarm_button1.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setting_alarm_button1.setBackgroundResource(R.drawable.setting_alarm_button2);
                Intent intent = new Intent(getApplicationContext(), setting_alarm.class);
                startActivity(intent);

                //버튼 클릭
                int hour, hour_24, minute;
                String am_pm;

                final TimePicker picker = (TimePicker) findViewById(R.id.timePicker);
                picker.setIs24HourView(true);

                //버튼 클릭시 데이터 피커의 시간을 받아옴
                if (Build.VERSION.SDK_INT >= 23) {
                    hour_24 = picker.getHour();
                    minute = picker.getMinute();
                } else {
                    hour_24 = picker.getCurrentHour();
                    minute = picker.getCurrentMinute();
                }
                if (hour_24 > 12) {
                    am_pm = "오후";
                    hour = hour_24 - 12;
                } else {
                    am_pm = "오전";
                    hour = hour_24;
                }

                //설정한 시간으로 calendar 변수에 날짜 넣어줌
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour_24);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);

                //이미 지난 시간을 지정했다면 다음날 같은 시간으로 설정
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                Date currentDateTime = calendar.getTime();
                String date_text = new SimpleDateFormat("a hh시 mm분", Locale.getDefault()).format(currentDateTime);
                Toast.makeText(getApplicationContext(), "다음 알람은 " + date_text + "으로 알람이 설정되었습니다.", Toast.LENGTH_SHORT).show();

                //Preference에 설정한 값 저장
                SharedPreferences.Editor editor = getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
                editor.putLong("nextNotifyTime", (long) calendar.getTimeInMillis());
                editor.apply();


                diaryNotification(calendar);
            }

            return false;

        });


    }


    //알람설정
    void diaryNotification(Calendar calendar)
    {
        Boolean dailyNotify = true; //매일 반복되는 알람의 조건 변수(항상 울림)

        PackageManager pm = this.getPackageManager();
        Intent alarmIntent = new Intent(this, setting_alarm_time.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //매일 울리기 위한 부분
        if (dailyNotify)
        {
            if (alarmManager != null) {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);}
            }
        }

    }

}