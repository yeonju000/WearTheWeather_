package org.techtown.weartheweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class keyword_snow extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_snow);


        ImageButton keyword_closebutton = (ImageButton) findViewById(R.id.keyword_closebutton);
        keyword_closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        ArrayList<String> snowKeywords = new ArrayList<>();
        snowKeywords.add("Clean");  snowKeywords.add("Excitement");  snowKeywords.add("Fantastic");
        snowKeywords.add("Romantic");  snowKeywords.add("Moist");  snowKeywords.add("Clear");
        snowKeywords.add("GoodFeeling");  snowKeywords.add("WhiteSnowflake");
        snowKeywords.add("Tranquil");  snowKeywords.add("Stillness");  snowKeywords.add("Chill");
        snowKeywords.add("Anticipation");  snowKeywords.add("Happy");  snowKeywords.add("Warmth");
        snowKeywords.add("Coziness");  snowKeywords.add("Joy");  snowKeywords.add("Freshness");
        snowKeywords.add("Dampness");  snowKeywords.add("Crunchy");  snowKeywords.add("Beauty");
        snowKeywords.add("Expectation");  snowKeywords.add("Comfort");



        // 이미 정해놓은 키워드 리스트에서 랜덤으로 3개의 키워드를 추천
        ArrayList<String> recommendedKeywords = getRandomKeywords(snowKeywords, 3);

        // 키워드 리스트를 '#'으로 구분하여 문자열로 변환
        StringBuilder keywordBuilder = new StringBuilder();
        for (int i = 0; i < recommendedKeywords.size(); i++) {
            keywordBuilder.append("#").append(recommendedKeywords.get(i));
            if (i < recommendedKeywords.size() - 1) {
                keywordBuilder.append("   ");
            }
        }
        String keywordText = keywordBuilder.toString();

        // XML 레이아웃에서 TextView를 찾아서 키워드를 설정
        TextView keywordsTextView = findViewById(R.id.keywordsTextView);
        keywordsTextView.setText(keywordText);
    }

    // 랜덤으로 n개의 키워드를 추천
    public static ArrayList<String> getRandomKeywords(ArrayList<String> keywordList, int n) {
        ArrayList<String> recommendedKeywords = new ArrayList<>();
        Random random = new Random();

        int listSize = keywordList.size();
        if (listSize <= n) {
            recommendedKeywords.addAll(keywordList);
        } else {
            while (recommendedKeywords.size() < n) {
                int randomIndex = random.nextInt(listSize);
                String randomKeyword = keywordList.get(randomIndex);
                if (!recommendedKeywords.contains(randomKeyword)) {
                    recommendedKeywords.add(randomKeyword);
                }
            }
        }
        return recommendedKeywords;
    }
    public void onBackPressed() {
        super.onBackPressed();
    }
}
