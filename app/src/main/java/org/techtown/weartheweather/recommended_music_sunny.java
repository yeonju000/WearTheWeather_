package org.techtown.weartheweather;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class recommended_music_sunny extends AppCompatActivity {

    private String[] videoUrls = {
            "https://www.youtube.com/embed/hPwMSH1RPMQ",
            "https://www.youtube.com/embed/zAFYo-qteug",
            "https://www.youtube.com/embed/WKqasNKfcFY",
            "https://www.youtube.com/embed/dLrxto-1cu8",
            "https://www.youtube.com/embed/w9TON4IwR2w",
            "https://www.youtube.com/embed/pDTJPg5gOxU",
            "https://www.youtube.com/embed/fj8ReY0HxWc",
            "https://www.youtube.com/embed/k1V4ho7w_Ww",
            "https://www.youtube.com/embed/E0COLl4M1i4",
            "https://www.youtube.com/embed/WoLjqsYDQIM",
            "https://www.youtube.com/embed/wyR0IfWC9Lg",
            "https://www.youtube.com/embed/lx0wyILl4-c"


    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_music_sunny);

        ImageButton sunny_back_button = (ImageButton) findViewById(R.id.sunny_back_button);
        sunny_back_button.setOnClickListener(new View.OnClickListener() {
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

        WebView webView = findViewById(R.id.webview_4);
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
