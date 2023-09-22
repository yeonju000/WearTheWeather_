package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class search_result extends AppCompatActivity {
    private FeelsDataSource dataSource;
    private TextView resultTextView;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        // 인텐트로부터 온도 값과 선택된 버튼 아이디들 받기
        Intent intent = getIntent();
        int temperature = intent.getIntExtra("temperature", 0);
        ArrayList<Integer> selectedButtonIds = intent.getIntegerArrayListExtra("selectedButtonIds");

        // 결과 값을 TextView에 표시 (온도)
        TextView resultTextView = findViewById(R.id.resultTextView);
        resultTextView.setText("▼ 온도:  " + temperature + "°C");

        // 선택된 버튼 아이디들을 Month 텍스트로 변환하여 TextView에 표시
        TextView resultTextView2 = findViewById(R.id.resultTextView2);
        StringBuilder selectedMonths = new StringBuilder("▼ 달:  ");
        for (int buttonId : selectedButtonIds) {
            String buttonIndexString = getResources().getResourceEntryName(buttonId).replace("button", "");
            selectedMonths.append(buttonIndexString).append("월,");
        }
        // 마지막에 추가된 쉼표 제거
        if (selectedMonths.length() > 0) {
            selectedMonths.deleteCharAt(selectedMonths.length() - 1);
        }
        resultTextView2.setText(selectedMonths.toString());

        // DatabaseHelper 클래스의 인스턴스 생성
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        // 온도 값의 ±3 범위 내에 해당하는 데이터를 검색하여 출력
        String query = "SELECT * FROM user_input WHERE temperature BETWEEN ? AND ? ORDER BY ABS(temperature - ?) ASC, temperature ASC";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, new String[]{String.valueOf(temperature - 3), String.valueOf(temperature + 3)});


        StringBuilder userData = new StringBuilder();
        String firstDate = null;
        String firstTemperature = null;

        while (cursor.moveToNext()) {
            int userTemperature = cursor.getInt(cursor.getColumnIndex("temperature"));

            // 온도가 조건에 맞을 때만 처리
            if (userTemperature >= temperature - 3 && userTemperature <= temperature + 3) {
                int month = getMonthFromDateString(cursor.getString(cursor.getColumnIndex("date")));
                if (selectedButtonIds.contains(getResources().getIdentifier("button" + month, "id", getPackageName()))) {
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    userData.append("\n\n▶ 날짜: ").append(date).append("\n");
                    userData.append(" 온도: ").append(userTemperature).append("°C\n\n");
                    if (firstDate == null && firstTemperature == null) {
                        firstDate = date;
                        firstTemperature = String.valueOf(userTemperature);
                    }
                }
            }
        }

        cursor.close();

        TextView resultTextView5 = findViewById(R.id.resultTextView5);
        if (firstDate != null && firstTemperature != null) {
            String displayText = "날짜: " + firstDate + "\n온도: " + firstTemperature + "°C";
            resultTextView5.setText(displayText);
        } else {
            resultTextView5.setText("조건에 맞는 검색 결과가\n존재하지 않습니다");
            resultTextView5.setOnClickListener(null); // 클릭 이벤트 제거
        }

        final String finalFirstDate = firstDate; // final 변수로 선언하여 익명 클래스에서 사용 가능하도록
        resultTextView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // resultTextView5 클릭 시 날짜 정보 가져오기
                String date = extractDateFromTextView(resultTextView5.getText().toString());

                // 데이터를 인텐트에 추가하여 두 번째 액티비티 시작
                Intent intent = new Intent(search_result.this, calender_daily.class);
                intent.putExtra("selectedDate", date); // 클릭한 날짜 정보 추가
                intent.putExtra("firstDate", finalFirstDate); // 첫 번째 날짜 정보 추가
                startActivity(intent);
            }
        });

        // 결과 값을 TextView에 표시 (user_data)
        TextView resultTextView3 = findViewById(R.id.resultTextView3);
        resultTextView3.setText(userData.toString());

        ImageButton search_result_closebutton = (ImageButton) findViewById(R.id.common_closebutton);
        search_result_closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_user.class);
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
    }

    // 월에 해당하는 데이터 가져오는 함수
    @SuppressLint("Range")
    private String getMonthData(int month) {
        // DatabaseHelper 클래스의 인스턴스 생성
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        // 월 값을 가지고 데이터베이스에서 해당 월의 데이터를 찾아옴
        String query = "SELECT date FROM user_input WHERE strftime('%m', date) = ?";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, new String[]{String.format("%02d", month)});

        StringBuilder monthData = new StringBuilder();
        while (cursor.moveToNext()) {
            monthData.append(cursor.getString(cursor.getColumnIndex("date"))).append(", ");
        }

        cursor.close();
        return monthData.toString();
    }
    // 월 추출 함수
    private int getMonthFromDateString(String date) {
        String[] parts = date.split("-");
        if (parts.length >= 2) {
            return Integer.parseInt(parts[1]);
        }
        return 0; // 기본값
    }
    protected void onDestroy() {
        super.onDestroy();
        if(dataSource != null) {
            dataSource.close();
        }
    }

    // TextView에서 날짜 정보 추출하는 함수
    private String extractDateFromTextView(String text) {

        // text에서 날짜 정보 추출 (예: "▶ 날짜: 2023-08-17")
        int startIndex = text.indexOf("날짜: ") + 5;
        int endIndex = text.indexOf("\n", startIndex);
        return text.substring(startIndex, endIndex);
    }

}


