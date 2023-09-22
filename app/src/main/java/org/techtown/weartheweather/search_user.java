package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class search_user extends AppCompatActivity {

    //search_month변수
    private int targetTemperature;
    long delay = 0;
    private ScrollView scrollView;
    private ImageView imageView;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        scrollView = findViewById(R.id.scroll);
        imageView = findViewById(R.id.search_user_bottom);

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

        ImageButton search_month_button13 = (ImageButton) findViewById(R.id.search_month_button13);
        search_month_button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextNumberSigned = findViewById(R.id.editTextNumberSigned);
                String temperatureText = editTextNumberSigned.getText().toString();

                if (temperatureText.isEmpty()) {
                    Toast.makeText(search_user.this, "온도 값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return; // 다음 단계로 진행하지 않음
                }

                int temperature = Integer.parseInt(temperatureText);

                ArrayList<Integer> selectedButtonIds = new ArrayList<>();
                boolean atLeastOneButtonSelected = false;

                for (int i = 0; i < 12; i++) {
                    int buttonId = getResources().getIdentifier("button" + (i + 1), "id", getPackageName());
                    Button button = findViewById(buttonId);
                    if (button.getCurrentTextColor() == Color.parseColor("#6094E3")) {
                        selectedButtonIds.add(buttonId);
                        atLeastOneButtonSelected = true;
                    }
                }

                if (!atLeastOneButtonSelected) {
                    Toast.makeText(search_user.this, "달을 선택해주세요", Toast.LENGTH_SHORT).show();
                    return; // 다음 단계로 진행하지 않음
                }

                // Intent 생성하여 온도 데이터와 선택된 버튼 아이디들을 search_result로 전달
                Intent intent = new Intent(getApplicationContext(), search_result.class);
                intent.putExtra("temperature", temperature);
                intent.putIntegerArrayListExtra("selectedButtonIds", selectedButtonIds);
                startActivity(intent);
            }
        });

        ImageButton imageButton9 = (ImageButton) findViewById(R.id.imageButton9);

        ImageButton search_temperature_button2 = (ImageButton) findViewById(R.id.search_temperature_button2);
        search_temperature_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton9.setVisibility(View.VISIBLE);
            }
        });

        imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageButton9.setVisibility(View.INVISIBLE);
            }
        });


        ImageButton search_user_backbutton = (ImageButton) findViewById(R.id.search_user_backbutton);
        search_user_backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),calender.class);
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
        //숫자 3개 이상 클릭하면 애니메이션 효과 + 버튼 모두 비활성화
        ImageView search_month_3 = findViewById(R.id.search_month_3);
        Button[] buttons = new Button[12];
        int[] countArray = new int[12];

        ArrayList<Button> selectedButtons = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            int buttonId = getResources().getIdentifier("button" + (i + 1), "id", getPackageName());
            buttons[i] = findViewById(buttonId);

            final int buttonIndex = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (System.currentTimeMillis() > delay) {
                        delay = System.currentTimeMillis() + 200;

                        if (selectedButtons.contains(buttons[buttonIndex])) {
                            selectedButtons.remove(buttons[buttonIndex]);
                            buttons[buttonIndex].setTextColor(Color.parseColor("#ffffff"));
                            countArray[buttonIndex] = 0;
                        } else if (selectedButtons.size() < 3) {
                            selectedButtons.add(buttons[buttonIndex]);
                            buttons[buttonIndex].setTextColor(Color.parseColor("#6094E3"));
                            countArray[buttonIndex] = 1;
                        }
                        for (Button button : buttons) {
                            if (!selectedButtons.contains(button)) {
                                button.setEnabled(selectedButtons.size() < 3);
                            }
                        }

                        int sum = 0;
                        for (int count : countArray) {
                            sum += count;
                        }

                        if (sum >= 3) {
                            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                            search_month_3.startAnimation(anim);
                        } else {
                            search_month_3.clearAnimation();
                        }
                    } else {
                        buttons[buttonIndex].setTextColor(Color.parseColor("#ffffff"));
                    }
                }
            });
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


