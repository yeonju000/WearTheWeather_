package org.techtown.weartheweather;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class alarmMainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String TAG = "MAIN";

    private TextView time_text;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedinstanceState) {

        super.onCreate(savedinstanceState);
        setContentView(R.layout.activity_alarm_main);

        time_text = findViewById(R.id.time_btn);
        Button time_btn = findViewById(R.id.time_btn);


        //시간 설정
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        //알람 취소 버튼 클릭 이벤트 핸들러 등록
        Button time_cancel_btn = findViewById(R.id.time_cancel_btn);
        time_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm(); // 기존의 알람 취소 동작 실행
            }
        });
        ImageButton alarm_main_backbutton = (ImageButton) findViewById(R.id.alarm_main_backbutton);
        alarm_main_backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setting_alarm.class);
                startActivity(intent);
            }
        });
    }

    //시간을 정하면 호출되는 메소드
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Log.d(TAG, "## onTimeSet ##");
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        //화면에 시간 지정
        updateTimeText(c);

        //알람 설정
        startAlarm(c);
    }

    //화면에 사용자가 선택한 시간을 보여주는 메소드
    private void updateTimeText(Calendar c) {

        Log.d(TAG, "## updateTimeText ##");
        String timeText = "알람 시간: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        time_text.setText(timeText);
    }

    //알람 시작
    private void startAlarm(Calendar c) {
        Log.d(TAG, "## startAlarm ##");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT); //약간 수정

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        //RTC_WAKE: 지정된 시간에 기기의 절전 모드를 해체하여 대기 중인 인텐트를 실행
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    // 알람 취소
    private void cancelAlarm() {
        Log.d(TAG, "## cancelAlarm ##");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

        // 데이터베이스에서 "alarmTime" 데이터를 0으로 업데이트
        dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("time", 0); // "time" 필드 값을 0으로 업데이트

        database.update("alarmTime", values, null, null);
        database.close();

        // 알림 취소 메시지 표시
        Toast.makeText(this, "알림이 취소되었습니다.", Toast.LENGTH_SHORT).show();

        // 시간 설정 버튼 텍스트 업데이트
        time_text.setText("시간 지정");
    }

}