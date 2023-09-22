package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class find_password extends AppCompatActivity {

    Button showPasswordButton;
    EditText passwordEditText;
    ImageButton find_password_backbutton;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        showPasswordButton = findViewById(R.id.showPasswordButton);
        passwordEditText = findViewById(R.id.password_edit_text);
        find_password_backbutton = findViewById(R.id.find_password_common_backbutton);

        find_password_backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_login.class);
                startActivity(intent);
            }
        });

        passwordEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredEmail = passwordEditText.getText().toString(); // 사용자가 입력한 이메일
                // 이메일에 해당하는 비밀번호 가져옴
                String password = getPasswordForEmail(enteredEmail);
            }
        });

        showPasswordButton.setOnTouchListener((view, motionEvent) -> {
            String enteredEmail = passwordEditText.getText().toString(); // 사용자가 입력한 이메일
            // 이메일에 해당하는 비밀번호 가져옴
            String password = getPasswordForEmail(enteredEmail);
            if (password != null) {
                Toast.makeText(find_password.this, "입력한 이메일에 대한 비밀번호: " + password, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(find_password.this, "입력한 이메일에 대한 비밀번호를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
            }

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                showPasswordButton.setBackgroundResource(R.drawable.add_icon1_button2);
            }

            return false;
        });
    }

    // 이메일에 해당하는 비밀번호 조회
    @SuppressLint("Range")
    private String getPasswordForEmail(String email) {
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        String query = "SELECT password FROM users WHERE email=?";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, new String[]{email});

        String password = null;
        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
        }
        cursor.close();
        return password;
    }
}