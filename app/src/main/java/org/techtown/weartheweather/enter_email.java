package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.weartheweather.databinding.ActivityEnterEmailBinding;

public class enter_email extends AppCompatActivity {

    ActivityEnterEmailBinding binding;
    DatabaseHelper databaseHelper;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEnterEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.enterEmailButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.enterEmailInput1.getText().toString();
                String password = binding.editTextTextPassword.getText().toString();
                String confirmPassword = binding.editTextTextPassword2.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(enter_email.this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (!isValidEmail(email)) {
                        Toast.makeText(enter_email.this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                    } else if (!isValidPassword(password)) {
                        Toast.makeText(enter_email.this, "비밀번호는 8~20자 사이이며, 문자와 숫자의 조합이어야 합니다.", Toast.LENGTH_SHORT).show();
                    } else if (!password.equals(confirmPassword)) {
                        Toast.makeText(enter_email.this, "비밀번호와 확인 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);
                        if (!checkUserEmail) {
                            Boolean insert = databaseHelper.insertData(email, password);
                            if (insert) {
                                Toast.makeText(enter_email.this, "회원가입에 성공하였습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), enter_nickname.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(enter_email.this, "회원가입에 실패하였습니다", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(enter_email.this, "이미 존재하는 사용자입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        binding.enterEmailButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(enter_email.this, main_login.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(String password) {
        // 비밀번호는 8자에서 20자 사이여야 하며 문자와 숫자의 조합이어야 함
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$";
        return password.matches(passwordPattern);
    }

}