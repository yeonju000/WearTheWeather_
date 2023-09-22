package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class main_weather extends AppCompatActivity {


    TextView cityView;
    TextView weatherView;
    TextView tempView;
    TextView rainfallView;

    TextView maxTempView;
    TextView minTempView;
    TextView feelsLikeView;
    TextView humidityView;

    ImageView weatherIconView; // 날씨 아이콘을 표시할 ImageView


    static RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        weatherIconView = findViewById(R.id.weatherIconView);
        //dateView = findViewById(R.id.dateView);
        cityView = findViewById(R.id.cityView);
        weatherView = findViewById(R.id.weatherView);
        tempView = findViewById(R.id.tempView);
        maxTempView = findViewById(R.id.maxTempView);
        minTempView = findViewById(R.id.minTempView);
        feelsLikeView = findViewById(R.id.feelsLikeView);
        humidityView = findViewById(R.id.humidityView);
        rainfallView = findViewById(R.id.rainfallView);


        ImageView main_ = findViewById(R.id.main_);
        ImageView imageView4 = findViewById(R.id.imageView4);
        Animation rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        main_.setAnimation(rotate);

        // 다크 테마 모드인지 확인
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            // 다크 테마 모드인 경우 이미지를 변경
            main_.setImageResource(R.drawable.main_1_dark);
            imageView4.setImageResource(R.drawable.darklogo);
        } else {
            // 기본 테마 모드인 경우 이미지를 변경하지 않음
            main_.setImageResource(R.drawable.main_1);
            imageView4.setImageResource(R.drawable.light_logo);
        }

        ImageButton button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // main_weather_box의 상태 변경
                ImageView mainWeatherBox = findViewById(R.id.main_weather_box);
                    mainWeatherBox.setVisibility(View.VISIBLE);

                //시간데이터와 날씨데이터 활용
                CurrentCall();
            }
        });

        //volley를 쓸 때 큐가 비어있으면 새로운 큐 생성
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


        ImageView main_weather_button1 = findViewById(R.id.main_weather_button1);
        main_weather_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), user_input.class);
                startActivity(intent);
            }
        });

        ImageButton imageButton5 = findViewById(R.id.imageButton5);
        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_weather.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton6 = findViewById(R.id.imageButton6);
        imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), recommended_music.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton7 = findViewById(R.id.imageButton7);
        imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calender.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton8 = findViewById(R.id.imageButton8);
        imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search_user.class);
                startActivity(intent);
            }
        });
        ImageButton imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), setting.class);
                startActivity(intent);
            }
        });
    }

    private void CurrentCall(){

        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=ffb0f1a2074f79704a5df7e9c27431c6&units=metric&lang=kr";



        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    //api로 받은 파일 jsonobject로 새로운 객체 선언
                    JSONObject jsonObject = new JSONObject(response);


                    // 강수량 정보 가져오기
                    if (jsonObject.has("rain")) {
                        JSONObject rainObj = jsonObject.getJSONObject("rain");
                        if (rainObj.has("1h")) {
                            double rainfall1h = rainObj.getDouble("1h");
                            String rainfallText = String.format("%.2f mm", rainfall1h); // 강수량 포맷 지정
                            rainfallView.setText("일일 누적 강수량: " + rainfallText);
                        } else {
                            rainfallView.setText("일일 누적 강수량 정보 없음");
                        }
                    } else {
                        rainfallView.setText("일일 누적 강수량 정보 없음");
                    }
                    //도시 키값 받기
                    String city = jsonObject.getString("name");

                    cityView.setText(city);


                    //날씨 키값 받기
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);

                    int weatherId = weatherObj.getInt("id");
                    setWeatherIcon(weatherId);

                    String weather = weatherObj.getString("description");

                    weatherView.setText(weather);



                    // 기온 키값 받기
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));
                    // 화면에 섭씨 온도 표시
                    double tempCelsius = tempK.getDouble("temp");
                    tempView.setText(String.format("%.1f °C", tempCelsius));
                    // 최고 기온 키값 받기
                    double maxTemp = tempK.getDouble("temp_max");
                    maxTempView.setText("▶최고 기온: " + String.format("%.1f°C", maxTemp));
                    // 최저 기온 키값 받기
                    double minTemp = tempK.getDouble("temp_min");
                    minTempView.setText("▶최저 기온: " + String.format("%.1f°C", minTemp));
                    // 체감 온도 키값 받기
                    double feelsLike = tempK.getDouble("feels_like");
                    feelsLikeView.setText("▶체감 온도: " + String.format("%.1f°C", feelsLike));
                    // 습도 키값 받기
                    int humidity = tempK.getInt("humidity");
                    humidityView.setText("습도: " + humidity + "%");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    private void setWeatherIcon(int weatherId) {
        int iconResource;

        if (weatherId >= 300 && weatherId <= 321) {
            iconResource = R.drawable.rain; // 이슬비
        } else if (weatherId >= 500 && weatherId <= 531) {
            iconResource = R.drawable.rain; // 비
        } else if (weatherId >= 600 && weatherId <= 622) {
            iconResource = R.drawable.snow; // 눈
        } else if (weatherId >= 701 && weatherId <= 781) {
            iconResource = R.drawable.mist; // mist
        } else if (weatherId == 800) {
            iconResource = R.drawable.sun; // 맑음
        } else if (weatherId >= 801 && weatherId <= 804) {
            iconResource = R.drawable.sun_cloud; // 흐림
        } else {
            iconResource = R.drawable.night_cloud; // 기타 상태에 대한 아이콘 //수정
        }

        weatherIconView.setImageResource(iconResource);
    }
}