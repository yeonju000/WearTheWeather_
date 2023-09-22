package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class enter_password extends Activity {


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        getWindow().setWindowAnimations(0);

        ImageButton enter_password_back_button = findViewById(R.id.find_password_common_backbutton4);
        enter_password_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), enter_email.class);
                startActivity(intent);
            }
        });


        Button enter_password_button2 = findViewById(R.id.enter_password_button2);
        enter_password_button2.setOnTouchListener((view, motionEvent) -> {

            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                enter_password_button2.setBackgroundResource(R.drawable.add_icon2_button4);
                Intent intent = new Intent(getApplicationContext(), enter_nickname.class);
                startActivity(intent);
            }
            return false;
        });
    }


}
