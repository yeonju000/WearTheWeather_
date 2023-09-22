package org.techtown.weartheweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class keyword_cloudy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_cloudy);


        ImageButton keyword_closebutton = (ImageButton) findViewById(R.id.keyword_closebutton);
        keyword_closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        ArrayList<String> cloudyKeywords = new ArrayList<>();
        cloudyKeywords.add("Lethargic");    cloudyKeywords.add("Quiet");    cloudyKeywords.add("Opaque");
        cloudyKeywords.add("Humid");    cloudyKeywords.add("LackingVitality");    cloudyKeywords.add("Atmospheric");
        cloudyKeywords.add("Cozy");    cloudyKeywords.add("Serene");    cloudyKeywords.add("Comfort");
        cloudyKeywords.add("Romantic");    cloudyKeywords.add("Calm");    cloudyKeywords.add("Melancholic");
        cloudyKeywords.add("Languid");    cloudyKeywords.add("Depress");    cloudyKeywords.add("Remember");
        cloudyKeywords.add("Homebody");    cloudyKeywords.add("Indecisive");    cloudyKeywords.add("Tranquil");
        cloudyKeywords.add("Dark");    cloudyKeywords.add("Cool");

        // 이미 정해놓은 키워드 리스트에서 랜덤으로 3개의 키워드를 추천
        ArrayList<String> recommendedKeywords = getRandomKeywords(cloudyKeywords, 3);

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

    // 랜덤으로 n개의 키워드를 추천하는 메서드
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