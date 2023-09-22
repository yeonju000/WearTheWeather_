package org.techtown.weartheweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView main_1 = findViewById(R.id.main_1);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        main_1.setAnimation(rotate);

        ImageView myImageView = findViewById(R.id.my_image_view);
        ImageView main_1ImageView = findViewById(R.id.main_1);
        ImageView main_2ImageView = findViewById(R.id.main_2);

        // 다크 테마 모드인지 확인
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            // 다크 테마 모드인 경우 이미지를 변경
            myImageView.setImageResource(R.drawable.main_moon_hanger);
            main_1ImageView.setImageResource(R.drawable.main_1_dark);
            main_2ImageView.setImageResource(R.drawable.main_2_dark);
        } else {
            // 기본 테마 모드인 경우 이미지를 변경하지 않음
            myImageView.setImageResource(R.drawable.main_cloud_hanger);
            main_1ImageView.setImageResource(R.drawable.main_1);
            main_2ImageView.setImageResource(R.drawable.main_2);
        }

        ImageButton main_button = findViewById(R.id.main_button);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), main_login.class);
                startActivity(intent);
            }
        });


    }
}
