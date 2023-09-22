package org.techtown.weartheweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class recommended_music_cloudy extends AppCompatActivity {
    private String[] videoUrls = {
            "https://www.youtube.com/embed/Mmg1Lfy3pLw",
            "https://www.youtube.com/embed/-xSoWCnKVW8",
            "https://www.youtube.com/embed/VNtKdI14eeM",
            "https://www.youtube.com/embed/fIm8Yig4iYU",
            "https://www.youtube.com/embed/w12PQWbfHzc",
            "https://www.youtube.com/embed/Gz85eKBk9mM",
            "https://www.youtube.com/embed/x6i3_LfeTjY",
            "https://www.youtube.com/embed/lIKmm-G7YVQ",
            "https://www.youtube.com/embed/qAg6fTVu8gI",
            "https://www.youtube.com/embed/AquWU_mgoU4",
            "https://www.youtube.com/embed/gbW1QFScQmE",
            "https://www.youtube.com/embed/8nhAu04Vb-4"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_music_cloudy);

        ImageButton cloudy_back_button = (ImageButton) findViewById(R.id.cloudy_back_button);
        cloudy_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), recommended_music.class);
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

        WebView webView = findViewById(R.id.webview_1);
        // 랜덤한 인덱스 생성
        int randomIndex = (int) (Math.random() * videoUrls.length);

        // 선택한 동영상 URL을 웹뷰에 로드
        String randomVideoUrl = videoUrls[randomIndex];
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"" + randomVideoUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>\n";

        webView.loadData(video, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }



}
