package org.techtown.weartheweather;

import static org.techtown.weartheweather.main_weather.requestQueue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class user_input extends AppCompatActivity implements View.OnClickListener {
    //데이터베이스
    private DatabaseHelper dbHelper;
    int fashionOuter = 0;
    int fashionTop, fashionPants, fashionShoes, slider, temperature;
    String keyword1, keyword2, keyword3, currentDate;

    TextView maxTempView1;
    TextView minTempView1;
    SimpleDateFormat dateFormat;

    //날씨 버튼 4개를 눌렀을 때 변수 설정
    private Button user_input_suggestion_button1, user_input_suggestion_button2, user_input_suggestion_button3, user_input_suggestion_button4;
    private Button selectedButton; //현재 선택된 버튼을 저장하는 변수


    private ScrollView scrollView;
    private ImageView imageView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        dbHelper = new DatabaseHelper(this);


        CurrentCall();

        //volley를 쓸 때 큐가 비어있으면 새로운 큐 생성하기
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        scrollView = findViewById(R.id.scroll);
        imageView = findViewById(R.id.imageView);

        scrollView.setVerticalScrollBarEnabled(false);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        imageView.startAnimation(anim);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView != null && imageView != null) {
                    if (isImageViewVisible()) {
                        imageView.clearAnimation();
                        imageView.setVisibility(View.GONE);
                    } else {
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        ImageButton user_input_temperature_backbutton2 = (ImageButton) findViewById(R.id.user_input_temperature_backbutton8);
        user_input_temperature_backbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_weather.class);
                startActivity(intent);
            }
        });

//slider
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar2);
        seekBar.setMax(100); // 시크바 최대값 설정
        seekBar.setProgress(50); // 초기 시크바 값 설정

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // SeekBar 값이 변경될 때 호출됨
                // progress 변수에 현재 SeekBar의 값이 저장되어 있음
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // SeekBar를 터치하여 움직이기 시작할 때 호출됨
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // SeekBar 터치를 끝내고 값이 변경되지 않을 때 호출됨
                // 여기서 데이터베이스에 값을 저장하는 작업 수행
                slider = seekBar.getProgress();
            }
        });

        // slider 에 저장된 값을 다른 액티비티로 송신
        int sliderValue = seekBar.getProgress();
        Intent intent_slider = new Intent(this, calender_daily.class);
        intent_slider.putExtra("sliderValue", sliderValue);



//keyword
        ImageView closetipbutton1 = (ImageView) findViewById(R.id.user_input_suggestionkey);
        closetipbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView search_tip = (ImageView) findViewById(R.id.user_input_suggestionkey);
                search_tip.setVisibility(View.INVISIBLE);

            }
        });

        ImageButton user_input_keyword_button_1 = findViewById(R.id.user_input_keyword_button_1);
        user_input_keyword_button_1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(getApplicationContext(), keyword_sun.class);
                    startActivity(intent);
                }
                return true;
            }
            return false;
        });

        ImageButton user_input_keyword_button_1_2 = findViewById(R.id.user_input_keyword_button_1_2);
        user_input_keyword_button_1_2.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(getApplicationContext(), keyword_cloudy.class);
                    startActivity(intent);
                }
                return true;
            }
            return false;
        });

        ImageButton user_input_keyword_button_1_3 = findViewById(R.id.user_input_keyword_button_1_3);
        user_input_keyword_button_1_3.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(getApplicationContext(), keyword_rain.class);
                    startActivity(intent);
                }
                return true;
            }
            return false;
        });

        ImageButton user_input_keyword_button_1_4 = findViewById(R.id.user_input_keyword_button_1_4);
        user_input_keyword_button_1_4.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Intent intent = new Intent(getApplicationContext(), keyword_snow.class);
                    startActivity(intent);
                }
                return true;
            }
            return false;
        });

        //  <<<<<날씨 버튼 4개를 눌렀을 때 코드 모음>>>>>
        user_input_suggestion_button1 = findViewById(R.id.user_input_suggestion_button1);
        user_input_suggestion_button2 = findViewById(R.id.user_input_suggestion_button2);
        user_input_suggestion_button3 = findViewById(R.id.user_input_suggestion_button3);
        user_input_suggestion_button4 = findViewById(R.id.user_input_suggestion_button4);

        // 모든 버튼에 같은 OnClickListener를 등록
        user_input_suggestion_button1.setOnClickListener(this);
        user_input_suggestion_button2.setOnClickListener(this);
        user_input_suggestion_button3.setOnClickListener(this);
        user_input_suggestion_button4.setOnClickListener(this);


        user_input_suggestion_button1.setOnClickListener(view -> {
            user_input_keyword_button_1.setEnabled(true); // sun만 활성화
            user_input_keyword_button_1_2.setEnabled(false);
            user_input_keyword_button_1_3.setEnabled(false);
            user_input_keyword_button_1_4.setEnabled(false);

            user_input_keyword_button_1.setVisibility(View.VISIBLE);
            ImageButton user_input_keyword_button_2 = findViewById(R.id.user_input_keyword_button_1);
            user_input_keyword_button_2.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_inputimageView2 = findViewById(R.id.user_input_keyword_inputimageView2);
            user_input_keyword_inputimageView2.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input1_bg = findViewById(R.id.user_input_keyword_input1_bg);
            user_input_keyword_input1_bg.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input2_bg = findViewById(R.id.user_input_keyword_input2_bg);
            user_input_keyword_input2_bg.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input1 = findViewById(R.id.user_input_keyword_input1);
            user_input_keyword_input1.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input2 = findViewById(R.id.user_input_keyword_input2);
            user_input_keyword_input2.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input3 = findViewById(R.id.user_input_keyword_input3);
            user_input_keyword_input3.setVisibility(View.VISIBLE);
        });
        user_input_keyword_button_1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(this, keyword_sun.class);
                startActivity(intent);
            }
            return true;
        });


        user_input_suggestion_button2.setOnClickListener(view -> {
            user_input_keyword_button_1_2.setEnabled(true); //cloud만 활성화
            user_input_keyword_button_1.setEnabled(false);
            user_input_keyword_button_1_3.setEnabled(false);
            user_input_keyword_button_1_4.setEnabled(false);

            user_input_keyword_button_1_2.setVisibility(View.VISIBLE);
            ImageButton user_input_keyword_button_3 = findViewById(R.id.user_input_keyword_button_1_2);
            user_input_keyword_button_3.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_inputimageView2 = findViewById(R.id.user_input_keyword_inputimageView2);
            user_input_keyword_inputimageView2.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input1_bg = findViewById(R.id.user_input_keyword_input1_bg);
            user_input_keyword_input1_bg.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input2_bg = findViewById(R.id.user_input_keyword_input2_bg);
            user_input_keyword_input2_bg.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input1 = findViewById(R.id.user_input_keyword_input1);
            user_input_keyword_input1.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input2 = findViewById(R.id.user_input_keyword_input2);
            user_input_keyword_input2.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input3 = findViewById(R.id.user_input_keyword_input3);
            user_input_keyword_input3.setVisibility(View.VISIBLE);
        });
        user_input_keyword_button_1_2.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(this, keyword_cloudy.class);
                startActivity(intent);
            }
            return true;
        });

        user_input_suggestion_button3.setOnClickListener(view -> {
            user_input_keyword_button_1_3.setEnabled(true); //rain만 활성화
            user_input_keyword_button_1_2.setEnabled(false);
            user_input_keyword_button_1.setEnabled(false);
            user_input_keyword_button_1_4.setEnabled(false);

            user_input_keyword_button_1_3.setVisibility(View.VISIBLE);
            ImageButton user_input_keyword_button_4 = findViewById(R.id.user_input_keyword_button_1_3);
            user_input_keyword_button_4.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_inputimageView2 = findViewById(R.id.user_input_keyword_inputimageView2);
            user_input_keyword_inputimageView2.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input1_bg = findViewById(R.id.user_input_keyword_input1_bg);
            user_input_keyword_input1_bg.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input2_bg = findViewById(R.id.user_input_keyword_input2_bg);
            user_input_keyword_input2_bg.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input1 = findViewById(R.id.user_input_keyword_input1);
            user_input_keyword_input1.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input2 = findViewById(R.id.user_input_keyword_input2);
            user_input_keyword_input2.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input3 = findViewById(R.id.user_input_keyword_input3);
            user_input_keyword_input3.setVisibility(View.VISIBLE);
        });
        user_input_keyword_button_1_3.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(this, keyword_rain.class);
                startActivity(intent);
            }
            return true;
        });

        user_input_suggestion_button4.setOnClickListener(view -> {
            user_input_keyword_button_1_4.setEnabled(true);//snow만 활성화
            user_input_keyword_button_1_2.setEnabled(false);
            user_input_keyword_button_1_3.setEnabled(false);
            user_input_keyword_button_1.setEnabled(false);

            user_input_keyword_button_1_4.setVisibility(View.VISIBLE);
            ImageButton user_input_keyword_button_5 = findViewById(R.id.user_input_keyword_button_1_4);
            user_input_keyword_button_5.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_inputimageView2 = findViewById(R.id.user_input_keyword_inputimageView2);
            user_input_keyword_inputimageView2.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input1_bg = findViewById(R.id.user_input_keyword_input1_bg);
            user_input_keyword_input1_bg.setVisibility(View.VISIBLE);
            ImageView user_input_keyword_input2_bg = findViewById(R.id.user_input_keyword_input2_bg);
            user_input_keyword_input2_bg.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input1 = findViewById(R.id.user_input_keyword_input1);
            user_input_keyword_input1.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input2 = findViewById(R.id.user_input_keyword_input2);
            user_input_keyword_input2.setVisibility(View.VISIBLE);
            EditText user_input_keyword_input3 = findViewById(R.id.user_input_keyword_input3);
            user_input_keyword_input3.setVisibility(View.VISIBLE);
        });
        user_input_keyword_button_1_4.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Intent intent = new Intent(this, keyword_snow.class);
                startActivity(intent);
            }
            return true;
        });



        //각 버튼에 대한 onTouchListenenr 등록(누르면 파란색으로 변함)
        user_input_suggestion_button1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_suggestion_button1.setBackgroundResource(R.drawable.user_input_suggestion_button1);
                user_input_suggestion_button2.setBackgroundResource(R.drawable.user_input_suggestion_key_button2);
                user_input_suggestion_button3.setBackgroundResource(R.drawable.user_input_suggestion_key_button3);
                user_input_suggestion_button4.setBackgroundResource(R.drawable.user_input_suggestion_key_button4);
            }
            return false;
        });

        user_input_suggestion_button2.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_suggestion_button1.setBackgroundResource(R.drawable.user_input_suggestion_key_button1);
                user_input_suggestion_button2.setBackgroundResource(R.drawable.user_input_suggestion_button2);
                user_input_suggestion_button3.setBackgroundResource(R.drawable.user_input_suggestion_key_button3);
                user_input_suggestion_button4.setBackgroundResource(R.drawable.user_input_suggestion_key_button4);
            }
            return false;
        });

        user_input_suggestion_button3.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_suggestion_button1.setBackgroundResource(R.drawable.user_input_suggestion_key_button1);
                user_input_suggestion_button2.setBackgroundResource(R.drawable.user_input_suggestion_key_button2);
                user_input_suggestion_button3.setBackgroundResource(R.drawable.user_input_suggestion_button3);
                user_input_suggestion_button4.setBackgroundResource(R.drawable.user_input_suggestion_key_button4);
            }
            return false;
        });

        user_input_suggestion_button4.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_suggestion_button1.setBackgroundResource(R.drawable.user_input_suggestion_key_button1);
                user_input_suggestion_button2.setBackgroundResource(R.drawable.user_input_suggestion_key_button2);
                user_input_suggestion_button3.setBackgroundResource(R.drawable.user_input_suggestion_key_button3);
                user_input_suggestion_button4.setBackgroundResource(R.drawable.user_input_suggestion_button4);
            }
            return false;
        });


//keyword
        EditText user_input_keyword_input1 = findViewById(R.id.user_input_keyword_input1);
        keyword1 = user_input_keyword_input1.getText().toString();
        EditText user_input_keyword_input2 = findViewById(R.id.user_input_keyword_input2);
        keyword2 = user_input_keyword_input2.getText().toString();
        EditText user_input_keyword_input3 = findViewById(R.id.user_input_keyword_input3);
        keyword3 = user_input_keyword_input3.getText().toString();


//fashion
        Button user_input_fashion_button_1 = findViewById(R.id.user_input_fashion_button_1);
        Button user_input_fashion_button_2 = findViewById(R.id.user_input_fashion_button_2);
        Button user_input_fashion_button_3 = findViewById(R.id.user_input_fashion_button_3);
        Button user_input_fashion_button_4 = findViewById(R.id.user_input_fashion_button_4);
        HorizontalScrollView outer = findViewById(R.id.outer);
        HorizontalScrollView top = findViewById(R.id.top);
        HorizontalScrollView pants = findViewById(R.id.pants);
        HorizontalScrollView shoes = findViewById(R.id.shoes);
        Button none = findViewById(R.id.none);
        Button coat = findViewById(R.id.coat);
        Button padding = findViewById(R.id.padding);
        Button jumper = findViewById(R.id.jumper);
        Button zipup = findViewById(R.id.zipup);
        Button cardigon = findViewById(R.id.cardigon);
        ImageView input_outer = findViewById(R.id.input_outer);
        ImageView input_top = findViewById(R.id.input_top);
        ImageView input_pants = findViewById(R.id.intput_pants);
        ImageView input_shoes = findViewById(R.id.input_shoes);

        user_input_fashion_button_1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_fashion_button_1.setBackgroundResource(R.drawable.user_input_fashion_button_1_blue);
                user_input_fashion_button_2.setBackgroundResource(R.drawable.user_input_fashion_button_2);
                user_input_fashion_button_3.setBackgroundResource(R.drawable.user_input_fashion_button_3);
                user_input_fashion_button_4.setBackgroundResource(R.drawable.user_input_fashion_button_4);
                outer.setVisibility(View.VISIBLE);
                top.setVisibility(View.INVISIBLE);
                pants.setVisibility(View.INVISIBLE);
                shoes.setVisibility(View.INVISIBLE);
            }
            return false;
        });
        none.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_outer.setBackgroundResource(R.mipmap.none_2_foreground);
                none.setText("SELECTED");
                fashionOuter = R.mipmap.none_2_foreground;
                coat.setText("");
                padding.setText("");
                jumper.setText("");
                zipup.setText("");
                cardigon.setText("");
            }
            return false;
        });
        coat.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_outer.setBackgroundResource(R.mipmap.coat_foreground);
                none.setText("");
                coat.setText("SELECTED");
                fashionOuter = R.mipmap.coat_foreground;
                padding.setText("");
                jumper.setText("");
                zipup.setText("");
                cardigon.setText("");
            }
            return false;
        });
        padding.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_outer.setBackgroundResource(R.mipmap.padding_foreground);
                none.setText("");
                coat.setText("");
                padding.setText("SELECTED");
                fashionOuter = R.mipmap.padding_foreground;
                jumper.setText("");
                zipup.setText("");
                cardigon.setText("");
            }
            return false;
        });
        jumper.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_outer.setBackgroundResource(R.mipmap.jumper_foreground);
                none.setText("");
                coat.setText("");
                padding.setText("");
                jumper.setText("SELECTED");
                fashionOuter = R.mipmap.jumper_foreground;
                zipup.setText("");
                cardigon.setText("");
            }
            return false;
        });
        zipup.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_outer.setBackgroundResource(R.mipmap.zipup_foreground);
                none.setText("");
                coat.setText("");
                padding.setText("");
                jumper.setText("");
                zipup.setText("SELECTED");
                fashionOuter = R.mipmap.zipup_foreground;
                cardigon.setText("");
            }
            return false;
        });
        cardigon.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_outer.setBackgroundResource(R.mipmap.cardigon_foreground);
                none.setText("");
                cardigon.setText("SELECTED");
                fashionOuter = R.mipmap.cardigon_foreground;
                coat.setText("");
                padding.setText("");
                jumper.setText("");
                zipup.setText("");
            }
            return false;
        });
        user_input_fashion_button_2.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_fashion_button_1.setBackgroundResource(R.drawable.user_input_fashion_button_1);
                user_input_fashion_button_2.setBackgroundResource(R.drawable.user_input_fashion_button_2_blue);
                user_input_fashion_button_3.setBackgroundResource(R.drawable.user_input_fashion_button_3);
                user_input_fashion_button_4.setBackgroundResource(R.drawable.user_input_fashion_button_4);
                top.setVisibility(View.VISIBLE);
                outer.setVisibility(View.INVISIBLE);
                pants.setVisibility(View.INVISIBLE);
                shoes.setVisibility(View.INVISIBLE);
            }
            return false;
        });
        Button hood = findViewById(R.id.hood);
        Button mantoman = findViewById(R.id.mantoman);
        Button knit = findViewById(R.id.knit);
        Button tshirt = findViewById(R.id.tshirt);
        hood.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_top.setBackgroundResource(R.mipmap.hood_foreground);
                hood.setText("SELECTED");
                fashionTop = R.mipmap.hood_foreground;
                mantoman.setText("");
                knit.setText("");
                tshirt.setText("");
            }
            return false;
        });
        mantoman.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_top.setBackgroundResource(R.mipmap.mantoman_foreground);
                hood.setText("");
                mantoman.setText("SELECTED");
                fashionTop = R.mipmap.mantoman_foreground;
                knit.setText("");
                tshirt.setText("");
            }
            return false;
        });
        knit.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_top.setBackgroundResource(R.mipmap.knit_foreground);
                hood.setText("");
                mantoman.setText("");
                knit.setText("SELECTED");
                fashionTop = R.mipmap.knit_foreground;
                tshirt.setText("");
            }
            return false;
        });
        tshirt.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_top.setBackgroundResource(R.mipmap.tshirts_foreground);
                hood.setText("");
                mantoman.setText("");
                knit.setText("");
                tshirt.setText("SELECTED");
                fashionTop = R.mipmap.tshirts_foreground;
            }
            return false;
        });

        Button shortskirt = findViewById(R.id.shortskirt);
        Button longskirt = findViewById(R.id.longskirt);
        Button longpants1 = findViewById(R.id.longpants1);
        Button longpants2 = findViewById(R.id.longpants2);
        Button shortpants = findViewById(R.id.shortpants);
        Button shortpants2 = findViewById(R.id.shortpants2);
        Button slacks = findViewById(R.id.slacks);
        user_input_fashion_button_3.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_fashion_button_1.setBackgroundResource(R.drawable.user_input_fashion_button_1);
                user_input_fashion_button_2.setBackgroundResource(R.drawable.user_input_fashion_button_2);
                user_input_fashion_button_3.setBackgroundResource(R.drawable.user_input_fashion_button_3_blue);
                user_input_fashion_button_4.setBackgroundResource(R.drawable.user_input_fashion_button_4);
                shoes.setVisibility(View.INVISIBLE);
                outer.setVisibility(View.INVISIBLE);
                top.setVisibility(View.INVISIBLE);
                pants.setVisibility(View.VISIBLE);
            }
            return false;
        });
        shortskirt.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.shortskirt_foreground);
                shortskirt.setText("SELECTED");
                fashionPants = R.mipmap.shortskirt_foreground;
                longskirt.setText("");
                longpants1.setText("");
                longpants2.setText("");
                shortpants.setText("");
                shortpants2.setText("");
                slacks.setText("");
            }
            return false;
        });
        longskirt.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.longskirt_foreground);
                shortskirt.setText("");
                longskirt.setText("SELECTED");
                fashionPants = R.mipmap.longskirt_foreground;
                longpants1.setText("");
                longpants2.setText("");
                shortpants.setText("");
                shortpants2.setText("");
                slacks.setText("");
            }
            return false;
        });
        longpants1.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.longpants_training_foreground);
                shortskirt.setText("");
                longskirt.setText("");
                longpants1.setText("SELECTED");
                fashionPants = R.mipmap.longpants_training_foreground;
                longpants2.setText("");
                shortpants.setText("");
                shortpants2.setText("");
                slacks.setText("");
            }
            return false;
        });
        longpants2.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.longpants_blue_foreground);
                shortskirt.setText("");
                longskirt.setText("");
                longpants1.setText("");
                longpants2.setText("SELECTED");
                fashionPants = R.mipmap.longpants_blue_foreground;
                shortpants.setText("");
                shortpants2.setText("");
                slacks.setText("");
            }
            return false;
        });
        shortpants.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.shortpants_training_foreground);
                shortskirt.setText("");
                longskirt.setText("");
                longpants1.setText("");
                longpants2.setText("");
                shortpants.setText("SELECTED");
                fashionPants = R.mipmap.shortpants_training_foreground;
                shortpants2.setText("");
                slacks.setText("");
            }
            return false;
        });
        shortpants2.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.shortpants_blue_foreground);
                shortskirt.setText("");
                longskirt.setText("");
                longpants1.setText("");
                longpants2.setText("");
                shortpants.setText("");
                shortpants2.setText("SELECTED");
                fashionPants = R.mipmap.shortpants_blue_foreground;
                slacks.setText("");
            }
            return false;
        });
        slacks.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_pants.setBackgroundResource(R.mipmap.longpants_slacks_foreground);
                shortskirt.setText("");
                longskirt.setText("");
                longpants1.setText("");
                longpants2.setText("");
                shortpants.setText("");
                shortpants2.setText("");
                slacks.setText("SELECTED");
                fashionPants = R.mipmap.longpants_slacks_foreground;
            }
            return false;
        });


        Button boots = findViewById(R.id.boots);
        Button sneakers = findViewById(R.id.sneakers);
        Button sandals = findViewById(R.id.sandals);

        user_input_fashion_button_4.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                user_input_fashion_button_1.setBackgroundResource(R.drawable.user_input_fashion_button_1);
                user_input_fashion_button_2.setBackgroundResource(R.drawable.user_input_fashion_button_2);
                user_input_fashion_button_3.setBackgroundResource(R.drawable.user_input_fashion_button_3);
                user_input_fashion_button_4.setBackgroundResource(R.drawable.user_input_fashion_button_4_blue);
                shoes.setVisibility(View.VISIBLE);
                outer.setVisibility(View.INVISIBLE);
                top.setVisibility(View.INVISIBLE);
                pants.setVisibility(View.INVISIBLE);
            }
            return false;
        });
        boots.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_shoes.setBackgroundResource(R.mipmap.boots_foreground);
                boots.setText("SELECTED");
                fashionShoes = R.mipmap.boots_foreground;
                sneakers.setText("");
                sandals.setText("");
            }
            return false;
        });
        sneakers.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_shoes.setBackgroundResource(R.mipmap.sneakers_foreground);
                boots.setText("");
                sneakers.setText("SELECTED");
                fashionShoes = R.mipmap.sneakers_foreground;
                sandals.setText("");
            }
            return false;
        });
        sandals.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                input_shoes.setBackgroundResource(R.mipmap.sandles_foreground);
                boots.setText("");
                sneakers.setText("");
                sandals.setText("SELECTED");
                fashionShoes = R.mipmap.sandles_foreground;
            }
            return false;
        });


//menubar
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



        Button user_input_fashion_button_5 = findViewById(R.id.user_input_fashion_button_5);
        user_input_fashion_button_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 날짜 구하기 (예시: YYYY-MM-DD 형식)
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String currentDate = dateFormat.format(new Date());

                // temperature 가져오기
                EditText temperatureEditText = findViewById(R.id.temperature);
                try {
                    String temperatureStr = temperatureEditText.getText().toString();
                    temperature = Integer.parseInt(temperatureStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // 사용자가 입력한 키워드 가져오기
                EditText keywordInput1 = findViewById(R.id.user_input_keyword_input1);
                EditText keywordInput2 = findViewById(R.id.user_input_keyword_input2);
                EditText keywordInput3 = findViewById(R.id.user_input_keyword_input3);

                keyword1 = keywordInput1.getText().toString();
                keyword2 = keywordInput2.getText().toString();
                keyword3 = keywordInput3.getText().toString();


                // 데이터베이스에 데이터 추가 또는 업데이트
                boolean isInsertedOrUpdated = dbHelper.insertUserInputData(currentDate, temperature, slider,
                        keyword1, keyword2, keyword3, fashionOuter, fashionTop, fashionPants, fashionShoes);

                if (isInsertedOrUpdated) {
                    if (dbHelper.someDataIsMissing(slider, fashionOuter, fashionTop, fashionPants, fashionShoes,
                            keyword1, keyword2, keyword3)) {
                        Toast.makeText(user_input.this, "모든 항목을 채워주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        String message;
                        if (dbHelper.isInsertOperation(currentDate, temperature)) {
                            message = "저장되었습니다";
                        } else {
                            message = currentDate + "에 저장된 기존 데이터가 변경됩니다";
                        }
                        Toast.makeText(user_input.this, message, Toast.LENGTH_SHORT).show();

                        // 데이터 전달을 위한 Intent 생성 -> 정상 송수신 되지만 null로 되는 경우가 있어서 변경 필요함
                        Intent intent = new Intent(user_input.this, calender_daily.class);
                        intent.putExtra("keyword1", keyword1);
                        intent.putExtra("keyword2", keyword2);
                        intent.putExtra("keyword3", keyword3);
                        intent.putExtra("fashionOuter", fashionOuter);
                        intent.putExtra("fashionTop", fashionTop);
                        intent.putExtra("fashionPants", fashionPants);
                        intent.putExtra("fashionShoes", fashionShoes);
                        intent.putExtra("currentDate", currentDate);
                        intent.putExtra("temperature", temperature);
                        intent.putExtra("slider", slider);

                        startActivity(intent);
                    }
                } else {
                    // 저장 또는 업데이트 실패한 경우
                    Toast.makeText(user_input.this, "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void sendDataToCalenderDaily() {
        Intent intent = new Intent(getApplicationContext(), calender_daily.class);
        intent.putExtra("fashionOuter", fashionOuter);
        intent.putExtra("fashionTop", fashionTop);
        intent.putExtra("fashionPants", fashionPants);
        intent.putExtra("fashionShoes", fashionShoes);
        // 이외에도 필요한 데이터를 추가로 전달할 수 있습니다.

        // calender_daily 액티비티 실행
        startActivity(intent);
    }


    private void CurrentCall() {

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=5bdc57d38b0a192f0fa45c6e71b7bc34&units=metric&lang=kr";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // main 오브젝트에서 최고 온도와 최저 온도 값 가져오기
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    double maxTemp = mainObject.getDouble("temp_max");
                    double minTemp = mainObject.getDouble("temp_min");

                    // 화면에 온도 값을 표시하기 위해 TextView 요소 찾기
                    TextView maxTempTextView = findViewById(R.id.maxTempTextView);
                    TextView minTempTextView = findViewById(R.id.minTempTextView);

                    // TextView 엘리먼트를 온도 값으로 업데이트
                    maxTempTextView.setText("최고 온도: " + maxTemp + "°C");
                    minTempTextView.setText("최저 온도: " + minTemp + "°C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }


    //keyword
    public void onClick(View view) {
        //클릭된 버튼을 저장, 나머지 버튼들 비활성화
        if (selectedButton != null) {
            selectedButton.setEnabled(true);
        }

        selectedButton = (Button) view;
        selectedButton.setEnabled(false);

        if (view.getId() == R.id.user_input_suggestion_button1) {
            int desiredBackgroundResource = R.drawable.user_input_suggestion_button1;
            handleButtonBackgroundChange(user_input_suggestion_button1, desiredBackgroundResource);
            user_input_suggestion_button2.setEnabled(false);
            user_input_suggestion_button3.setEnabled(false);
            user_input_suggestion_button4.setEnabled(false);
            ImageButton user_input_keyword_button_1 = findViewById(R.id.user_input_keyword_button_1);
            user_input_keyword_button_1.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.user_input_suggestion_button2) {
            int desiredBackgroundResource = R.drawable.user_input_suggestion_button2;
            handleButtonBackgroundChange(user_input_suggestion_button2, desiredBackgroundResource);
            user_input_suggestion_button1.setEnabled(false);
            user_input_suggestion_button3.setEnabled(false);
            user_input_suggestion_button4.setEnabled(false);
            ImageButton user_input_keyword_button_1_2 = findViewById(R.id.user_input_keyword_button_1_2);
            user_input_keyword_button_1_2.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.user_input_suggestion_button3) {
            int desiredBackgroundResource = R.drawable.user_input_suggestion_button3;
            handleButtonBackgroundChange(user_input_suggestion_button3, desiredBackgroundResource);
            user_input_suggestion_button1.setEnabled(false);
            user_input_suggestion_button2.setEnabled(false);
            user_input_suggestion_button4.setEnabled(false);
            ImageButton user_input_keyword_button_1_3 = findViewById(R.id.user_input_keyword_button_1_3);
            user_input_keyword_button_1_3.setVisibility(View.VISIBLE);
        } else if (view.getId() == R.id.user_input_suggestion_button4) {
            int desiredBackgroundResource = R.drawable.user_input_suggestion_button4;
            handleButtonBackgroundChange(user_input_suggestion_button4, desiredBackgroundResource);
            user_input_suggestion_button1.setEnabled(false);
            user_input_suggestion_button2.setEnabled(false);
            user_input_suggestion_button3.setEnabled(false);
            ImageButton user_input_keyword_button_1_4 = findViewById(R.id.user_input_keyword_button_1_4);
            user_input_keyword_button_1_4.setVisibility(View.VISIBLE);
        }
    }

    private void handleButtonBackgroundChange(Button button, int desiredBackgroundResource) {
        if (button.getBackground().getConstantState() == getResources().getDrawable(desiredBackgroundResource).getConstantState()) {
            // 버튼의 배경이 원하는 Drawable과 같은 경우

        } else {
            // 버튼의 배경이 원하는 Drawable과 다른 경우
            ImageButton user_input_keyword_button_1 = findViewById(R.id.user_input_keyword_button_1);
            user_input_keyword_button_1.setVisibility(View.INVISIBLE);
            ImageButton user_input_keyword_button_1_2 = findViewById(R.id.user_input_keyword_button_1_2);
            user_input_keyword_button_1.setVisibility(View.INVISIBLE);
            ImageButton user_input_keyword_button_1_3 = findViewById(R.id.user_input_keyword_button_1_3);
            user_input_keyword_button_1.setVisibility(View.INVISIBLE);
            ImageButton user_input_keyword_button_1_4 = findViewById(R.id.user_input_keyword_button_1_4);
            user_input_keyword_button_1.setVisibility(View.INVISIBLE);
            ImageView user_input_keyword_inputimageView2 = findViewById(R.id.user_input_keyword_inputimageView2);
            user_input_keyword_inputimageView2.setVisibility(View.INVISIBLE);
            ImageView user_input_keyword_input1_bg = findViewById(R.id.user_input_keyword_input1_bg);
            user_input_keyword_input1_bg.setVisibility(View.INVISIBLE);
            ImageView user_input_keyword_input2_bg = findViewById(R.id.user_input_keyword_input2_bg);
            user_input_keyword_input2_bg.setVisibility(View.INVISIBLE);
            EditText user_input_keyword_input1 = findViewById(R.id.user_input_keyword_input1);
            user_input_keyword_input1.setVisibility(View.INVISIBLE);
            EditText user_input_keyword_input2 = findViewById(R.id.user_input_keyword_input2);
            user_input_keyword_input2.setVisibility(View.INVISIBLE);
            EditText user_input_keyword_input3 = findViewById(R.id.user_input_keyword_input3);
            user_input_keyword_input3.setVisibility(View.INVISIBLE);
        }
    }


private boolean isImageViewVisible() {
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);

        int imageViewBottom = location[1] + imageView.getHeight();
        int scrollViewBottom = scrollView.getScrollY() + scrollView.getHeight();

        // 이미지뷰의 아래쪽 경계가 스크롤뷰의 아래쪽 경계보다 위에 있는 경우 숨김
        return imageViewBottom < scrollViewBottom;
    }

}
