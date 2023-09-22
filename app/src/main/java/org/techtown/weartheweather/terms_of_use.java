package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class terms_of_use extends AppCompatActivity {
    long delay = 0;

    //체크박스
    private CheckBox allCheckBtn;
    private CheckBox firstCheckBtn;
    private CheckBox secondCheckBtn;
    private CheckBox thirdCheckBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        ImageButton terms_of_use_back_button = findViewById(R.id.terms_of_use_button1);
        terms_of_use_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_login.class);
                startActivity(intent);
            }
        });


        Button terms_of_use_button5 = findViewById(R.id.terms_of_use_button5);
        terms_of_use_button5.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                terms_of_use_button5.setBackgroundResource(R.drawable.add_icon2_button3);
                // 체크박스들이 모두 체크되었는지 확인
                if (allCheckBtn.isChecked() && firstCheckBtn.isChecked() && secondCheckBtn.isChecked() && thirdCheckBtn.isChecked()) {
                    Intent intent = new Intent(getApplicationContext(), enter_email.class);
                    startActivity(intent);
                } else {
                    // 체크되지 않은 항목이 있는 경우에 대한 처리
                    Toast.makeText(getApplicationContext(), "모든 항목에 동의해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });

        //체크박스
        allCheckBtn = findViewById(R.id.terms_of_use_agree_all);
        firstCheckBtn = findViewById(R.id.terms_of_use_agree_1);
        secondCheckBtn = findViewById(R.id.terms_of_use_agree_2);
        thirdCheckBtn = findViewById(R.id.terms_of_use_agree_3);

        // 각 체크박스의 상태 변경에 대한 동작
        allCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    firstCheckBtn.setChecked(true);
                    secondCheckBtn.setChecked(true);
                    thirdCheckBtn.setChecked(true);
                }
                else {
                    firstCheckBtn.setChecked(false);
                    secondCheckBtn.setChecked(false);
                    thirdCheckBtn.setChecked(false);
                }
            }
        });

        // 각 체크박스의 상태 변경에 대한 동작
        firstCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 첫번째 체크박스 상태 변경
                updateAllCheckButton();
            }
        });

        // 두번째 체크
        secondCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 두번째 체크박스 상태 변경
                updateAllCheckButton();
            }
        });

        // 세번째 체크
        thirdCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 세번째 체크박스 상태 변경
                updateAllCheckButton();
            }
        });

        // "전체 동의" 체크박스 상태 변경
        allCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // "전체 동의" 체크박스를 클릭하여 상태 변경된 경우
                // 각 체크박스의 상태를 모두 동일하게 변경
                firstCheckBtn.setChecked(isChecked);
                secondCheckBtn.setChecked(isChecked);
                thirdCheckBtn.setChecked(isChecked);
            }
        });


    }
    // 각 체크박스 상태에 따라 "전체 동의" 체크박스 상태 업데이트
    private void updateAllCheckButton() {
        if (firstCheckBtn.isChecked() && secondCheckBtn.isChecked() && thirdCheckBtn.isChecked()) {
            allCheckBtn.setChecked(true);
        } else {
            allCheckBtn.setChecked(false);
        }
    }

}
