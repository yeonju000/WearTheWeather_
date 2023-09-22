package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.techtown.weartheweather.databinding.ActivityMainLoginBinding;

public class main_login extends AppCompatActivity {

    ActivityMainLoginBinding binding;
    DatabaseHelper databaseHelper;
    //카카오 로그인
    private ISessionCallback mSessionCallback;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.mainLoginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.mainemailinput.getText().toString();
                String password = binding.mainpasswordinput.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(main_login.this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(main_login.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
                    if (checkCredentials == true) {
                        Toast.makeText(main_login.this, "로그인에 성공하였습니다", Toast.LENGTH_SHORT).show();

                        // 닉네임 가져오기
                        String nickname = databaseHelper.getNicknameByEmail(email);

                        // SharedPreferences에 데이터 저장
                        SharedPreferences sharedPref = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("nickname", nickname);
                        editor.apply();

                        // 메뉴 액티비티로 전환
                        Intent menuIntent = new Intent(getApplicationContext(), setting.class);
                        startActivity(menuIntent);

                        // main_weather 액티비티로 전환
                        Intent mainWeatherIntent = new Intent(getApplicationContext(), main_weather.class);
                        startActivity(mainWeatherIntent);
                        } else {
                            Toast.makeText(main_login.this, "잘못된 자격증명입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        });


        binding.mainLoginButton3.setOnClickListener(view -> {
            Intent intent = new Intent(main_login.this, terms_of_use.class);
            startActivity(intent);
        });

        ImageButton find_password_button = (ImageButton) findViewById(R.id.main_login_button2);
        find_password_button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), find_password.class);
            startActivity(intent);
        });

        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() { // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) { //로그인 실패
                        Toast.makeText(main_login.this, "로그인에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) { //세션이 닫힘
                        Toast.makeText(main_login.this, "로그인에 실패하였습니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) { //로그인 성공
                        databaseHelper.insertData(result.getKakaoAccount().getEmail(), "");
                        Intent intent_main_weather = new Intent(main_login.this, main_weather.class);
                        startActivity(intent_main_weather);
                        Toast.makeText(main_login.this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {

            }
        };

        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mSessionCallback);
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
