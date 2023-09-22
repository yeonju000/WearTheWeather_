package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class setting_password extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);

        dbHelper = new DatabaseHelper(this);

        EditText emailEditText = findViewById(R.id.setting_password1);
        EditText oldPasswordEditText = findViewById(R.id.setting_password_bg_1);
        EditText newPasswordEditText = findViewById(R.id.setting_password2);
        EditText confirmNewPasswordEditText = findViewById(R.id.setting_password3);
        Button changePasswordButton = findViewById(R.id.setting_password_button2);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String oldPassword = oldPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmNewPasswordEditText.getText().toString();

                // 기존 비밀번호가 맞는지 확인
                if (dbHelper.checkEmailPassword(email, oldPassword)) {
                    // 새로운 비밀번호가 8~20자 사이이며, 문자와 숫자의 조합인지 확인
                    if (!isValidPassword(newPassword)) {
                        Toast.makeText(getApplicationContext(), "비밀번호는 8~20자 사이이며, 문자와 숫자의 조합이어야 합니다.", Toast.LENGTH_SHORT).show();
                    } else if (!newPassword.equals(confirmPassword)) {
                        Toast.makeText(getApplicationContext(), "비밀번호와 확인 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else if (oldPassword.equals(newPassword)) {
                        Toast.makeText(getApplicationContext(), "새 비밀번호와 이전 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 데이터베이스에서 비밀번호 업데이트
                        boolean passwordUpdated = dbHelper.updatePassword(email, newPassword);
                        if (passwordUpdated) {
                            Toast.makeText(getApplicationContext(), "비밀번호가 성공적으로 변경되었습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "비밀번호 변경을 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "기존 비밀번호가 잘못되었습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });




        ImageButton setting_common_backbutton3 = (ImageButton) findViewById(R.id.setting_common_backbutton3);
        setting_common_backbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setting.class);
                startActivity(intent);
            }
        });


        Button setting_password_button1 = findViewById(R.id.setting_password_button1);
        setting_password_button1.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setting_password_button1.setBackgroundResource(R.drawable.setting_password_button1);
            }

            return false;
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






        Button settingPasswordButton = findViewById(R.id.setting_password_button1);
        settingPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 이메일을 가져와서 계정으ㅜ 존재 여부 확인
                String email = emailEditText.getText().toString();
                if (dbHelper.checkEmailExist(email)) {
                    // 계정이 존재하는 경우


                    int desiredBackgroundResource = R.drawable.setting_password_button1;

                    if (settingPasswordButton.getBackground().getConstantState()
                            == getResources().getDrawable(desiredBackgroundResource).getConstantState()) {
                        TextView setting_password_text = (TextView)findViewById(R.id.setting_password_text);
                        setting_password_text.setVisibility(View.VISIBLE);
                        ImageView setting_password_6 = (ImageView)findViewById(R.id.setting_password_6);
                        setting_password_6.setVisibility(View.VISIBLE);
                        ImageView setting_password_2 = (ImageView)findViewById(R.id.setting_password_2);
                        setting_password_2.setVisibility(View.VISIBLE);
                        ImageView setting_password_3 = (ImageView)findViewById(R.id.setting_password_3);
                        setting_password_3.setVisibility(View.VISIBLE);
                        ImageView setting_password_4 = (ImageView)findViewById(R.id.setting_password_4);
                        setting_password_4.setVisibility(View.VISIBLE);
                        ImageView setting_password_5 = (ImageView)findViewById(R.id.setting_password_5);
                        setting_password_5.setVisibility(View.VISIBLE);
                        Button setting_password_button2 = (Button)findViewById(R.id.setting_password_button2);
                        setting_password_button2.setVisibility(View.VISIBLE);
                        EditText setting_password2 = (EditText)findViewById(R.id.setting_password2);
                        setting_password2.setVisibility(View.VISIBLE);
                        EditText setting_password3 = (EditText)findViewById(R.id.setting_password3);
                        setting_password3.setVisibility(View.VISIBLE);
                    } else {
                        ImageView setting_password_6 = (ImageView)findViewById(R.id.setting_password_6);
                        setting_password_6.setVisibility(View.INVISIBLE);
                        ImageView setting_password_2 = (ImageView)findViewById(R.id.setting_password_2);
                        setting_password_2.setVisibility(View.INVISIBLE);
                        ImageView setting_password_3 = (ImageView)findViewById(R.id.setting_password_3);
                        setting_password_3.setVisibility(View.INVISIBLE);
                        ImageView setting_password_4 = (ImageView)findViewById(R.id.setting_password_4);
                        setting_password_4.setVisibility(View.INVISIBLE);
                        ImageView setting_password_5 = (ImageView)findViewById(R.id.setting_password_5);
                        setting_password_5.setVisibility(View.INVISIBLE);
                        Button setting_password_button2 = (Button)findViewById(R.id.setting_password_button2);
                        setting_password_button2.setVisibility(View.INVISIBLE);
                        EditText setting_password2 = (EditText)findViewById(R.id.setting_password2);
                        setting_password2.setVisibility(View.INVISIBLE);
                        EditText setting_password3 = (EditText)findViewById(R.id.setting_password3);
                        setting_password3.setVisibility(View.INVISIBLE);
                    }
                } else {
                    // 계정이 존재하지 않는 경우
                    Toast.makeText(getApplicationContext(), "존재하지 않는 계정입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidPassword(String password) {
        // 비밀번호는 8자에서 20자 사이여야 하며 문자와 숫자의 조합
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
        return password.matches(passwordPattern);
    }
}