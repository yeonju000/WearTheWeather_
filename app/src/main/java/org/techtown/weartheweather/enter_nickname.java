package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class enter_nickname extends Activity {

    Button nickname_button;
    DatabaseHelper databaseHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_nickname);

        databaseHelper = new DatabaseHelper(this);
        if (!databaseHelper.isNicknameTableExists()) {
            databaseHelper.createNicknameTable();
        }

        getWindow().setWindowAnimations(0);

        nickname_button = findViewById(R.id.nickname_button);
        nickname_button.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                nickname_button.setBackgroundResource(R.drawable.add_icon2_button5);

                // EditText에서 입력한 닉네임을 가져옴
                EditText nicknameEditText = findViewById(R.id.editTextText);
                String nickname = nicknameEditText.getText().toString();

                // 닉네임을 이전 액티비티에서 이메일과 함께 받아옴
                String email = getIntent().getStringExtra("email");
                boolean nicknameInserted = databaseHelper.insertNickname(email, nickname);

                if (nicknameInserted) {

                    //닉네임을 complete 액티비티에 넘겨줌
                    Intent intent = new Intent(getApplicationContext(), complete.class);
                    intent.putExtra("nickname", nickname);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    // 삽입 오류 처리
                }
            }
            return false;
        });

        EditText editText = findViewById(R.id.editTextText);
        // 초기 버튼 비활성화
        nickname_button.setEnabled(false);
        // EditText에 텍스트가 입력될 때마다 호출되는 TextWatcher 등록
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 입력된 텍스트의 길이가 0보다 크면 버튼을 활성화
                if (charSequence.length() > 0) {
                    nickname_button.setBackgroundResource(R.drawable.add_icon2_button5);
                    nickname_button.setEnabled(true);
                } else {
                    nickname_button.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        ImageButton enter_nickname_back_button = findViewById(R.id.enter_nickname_backbutton1);
        enter_nickname_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), enter_password.class);
                startActivity(intent);
            }
        });
    }
}
