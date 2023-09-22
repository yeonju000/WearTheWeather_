package org.techtown.weartheweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class setting_alarm extends AppCompatActivity {

    //스위치버튼 상태 유지
    private SettingAlarmPlus alarmSettingAlarmPlus;

    private Switch alarmSwitch;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_alarm);



        alarmSettingAlarmPlus = new SettingAlarmPlus(this);

        alarmSwitch = findViewById(R.id.switch1);
        dbHelper = new DatabaseHelper(this);
        ImageView setting_alarm_2 = findViewById(R.id.setting_alarm_2);
        ImageButton setting_alarm_common_big_arrow__right = findViewById(R.id.setting_alarm_common_big_arrow__right);

        // 데이터베이스에 알람 시간이 저장되어 있는지 확인
        boolean alarmTimeExists = dbHelper.checkAlarmTimeExists();
        TextView textView2 = findViewById(R.id.textView2);

        // 알람 시간이 저장되어 있다면 스위치를 On으로 설정
        alarmSwitch.setChecked(alarmTimeExists);

        // 데이터베이스에서 저장된 알람 시간 밀리초 값 가져오기
        long alarmTimeInMillis = dbHelper.getAlarmTime();

        if (alarmTimeInMillis == 0) {
            // 데이터베이스의 알람 시간이 0일 경우
            textView2.setText(""); // 빈 텍스트 설정
            alarmSwitch.setChecked(false); // 스위치를 Off로 설정
        } else {
            setting_alarm_2.setVisibility(View.VISIBLE);
            setting_alarm_common_big_arrow__right.setVisibility(View.VISIBLE);
            // 밀리초 시간값을 시간 형태로 변환하여 출력
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(alarmTimeInMillis);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String formattedTime = dateFormat.format(calendar.getTime());

            // 변환된 시간 형태를 출력
            textView2.setText("푸쉬알림 시간  " + formattedTime);
        }

        alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 스위치 상태가 변경되었을 때의 동작 처리
                if (isChecked) {
                    textView2.setVisibility(View.VISIBLE);
                    // 스위치가 On 상태일 때의 처리
                    setting_alarm_2.setVisibility(View.VISIBLE);
                    setting_alarm_common_big_arrow__right.setVisibility(View.VISIBLE);

                    // 데이터베이스에서 저장된 알람 시간 밀리초 값 가져오기
                    long alarmTimeInMillis = dbHelper.getAlarmTime();

                    if (alarmTimeInMillis == 0) {
                        // 데이터베이스의 알람 시간이 0일 경우
                        textView2.setText("시간을 설정해주세요"); // 빈 텍스트 설정
                        alarmSwitch.setChecked(true); // 스위치를 Off로 설정
                    } else {
                        // 밀리초 시간값을 시간 형태로 변환하여 출력
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(alarmTimeInMillis);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String formattedTime = dateFormat.format(calendar.getTime());

                        // 변환된 시간 형태를 출력
                        textView2.setText("푸쉬알림 시간  " + formattedTime);
                    }
                } else {
                    textView2.setVisibility(View.INVISIBLE);
                    // 스위치가 Off 상태일 때의 처리
                    setting_alarm_2.setVisibility(View.INVISIBLE);
                    setting_alarm_common_big_arrow__right.setVisibility(View.INVISIBLE);
                }
            }
        });

        ImageButton setting_common_backbutton6 = (ImageButton) findViewById(R.id.setting_common_backbutton6);
        setting_common_backbutton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setting.class);
                startActivity(intent);
            }
        });

        ImageButton setting_alarm_common_big_arrow__right_button = (ImageButton) findViewById(R.id.setting_alarm_common_big_arrow__right);
        setting_alarm_common_big_arrow__right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), setting_alarm_time.class);
                Intent intent = new Intent(getApplicationContext(), alarmMainActivity.class);
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
    }
}
