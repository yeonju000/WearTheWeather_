package org.techtown.weartheweather;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class calender_daily extends AppCompatActivity {
    private TextView dateEditText;
    private TextView keywordText;

    int fashionOuter = -1;
    int fashionTop = -1;
    int fashionPants = -1;
    int fashionShoes = -1;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_daily);
        SeekBar seekBar = findViewById(R.id.seekBar3);

        // 이전 액티비티에서 전달받은 데이터 받기
        Intent currentDateintent = getIntent();
        String currentDate = currentDateintent.getStringExtra("currentDate");

        // 날짜 정보를 TextView에 설정
        dateEditText = findViewById(R.id.DATE);
        if (currentDate != null) {
            dateEditText.setText(currentDate);
        }

        //이전 액티비티에서 전달받은 키워드 정보 받기
        keywordText = findViewById(R.id.KEYWORD);
        Intent intent = getIntent();
        String keyword1 = intent.getStringExtra("keyword1");
        String keyword2 = intent.getStringExtra("keyword2");
        String keyword3 = intent.getStringExtra("keyword3");

        if (keyword1 != null) {
            if (keyword2 != null && keyword3 !=null){
                keywordText.setText("#"+keyword1+" #"+keyword2+"\n#"+keyword3);
            } else if (keyword2 != null && keyword3 == null) {
                keywordText.setText("#"+keyword1+" #"+keyword2);
            } else if (keyword2 == null && keyword3 != null) {
                keywordText.setText("#"+keyword1+" #"+keyword3);
            }
        } else {
            if (keyword2 != null && keyword3 != null){
                keywordText.setText("#"+keyword2+" #"+keyword3);
            } else if (keyword2 != null && keyword3 == null) {
                keywordText.setText("#"+keyword2);
            } else if (keyword2 == null && keyword3 != null) {
                keywordText.setText("#"  + keyword3);
            }
        }

        TextView tempText = findViewById(R.id.TEMP);
        int temperature = intent.getIntExtra("temperature", 0); // 기본값은 0
        if (temperature == 0 ) tempText.setText(""); // temperature 출력
        else tempText.setText("Temperature: \n" + temperature+"°C"); // temperature 출력


        int sliderValue = intent.getIntExtra("slider", 0);
        seekBar.setProgress(sliderValue); // slider 값으로 SeekBar 설정
        seekBar.setEnabled(false);


        // 추가된 코드: 선택된 옷 정보 불러오기
        Intent intent2 = getIntent();
        int fashionOuter = intent2.getIntExtra("fashionOuter", -1);
        int fashionTop = intent2.getIntExtra("fashionTop", -1);
        int fashionPants = intent2.getIntExtra("fashionPants", -1);
        int fashionShoes = intent2.getIntExtra("fashionShoes", -1);

        // ImageView 찾기
        ImageView calenderDaily1ImageView = findViewById(R.id.calender_daily_item);
        ImageView calenderDaily2ImageView = findViewById(R.id.calender_daily_item2);
        ImageView calenderDaily3ImageView = findViewById(R.id.calender_daily_item3);
        ImageView calenderDaily4ImageView = findViewById(R.id.calender_daily_item4);


     // 전달받은 데이터에 따라 이미지 설정
        if (fashionOuter != -1 && fashionTop != -1 && fashionPants != -1 && fashionShoes != -1) {
            calenderDaily1ImageView.setImageResource(fashionOuter);
            calenderDaily2ImageView.setImageResource(fashionTop);
            calenderDaily3ImageView.setImageResource(fashionPants);
            calenderDaily4ImageView.setImageResource(fashionShoes);
        } else{

        }


// 캘린더 클릭
        // DatabaseHelper 객체 초기화
        databaseHelper = new DatabaseHelper(this);

        // 이전 액티비티에서 전달받은 날짜 정보 받기
        int year = getIntent().getIntExtra("year", -1);
        int month = getIntent().getIntExtra("month", -1);
        int day = getIntent().getIntExtra("day", -1);

        if (year != -1 && month != -1 && day != -1) {
            String selectedDate = String.format("%04d-%02d-%02d", year, month, day);

            // 날짜 정보를 TextView에 설정
            TextView dateTextView = findViewById(R.id.DATE);
            dateTextView.setText(selectedDate);

            // 데이터베이스 조회 및 출력
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    "user_input",
                    new String[] {"temperature", "slider", "keyword1", "keyword2", "keyword3",
                            "fashion_outer", "fashion_top", "fashion_pants", "fashion_shoes"},
                    "date = ?",
                    new String[] {selectedDate},
                    null, null, null, null
            );

            if (cursor.moveToFirst()) {
                @SuppressLint("Range") int temperature2 = cursor.getInt(cursor.getColumnIndex("temperature"));
                @SuppressLint("Range") int sliderValue2 = cursor.getInt(cursor.getColumnIndex("slider"));
                @SuppressLint("Range") String keyword1_2 = cursor.getString(cursor.getColumnIndex("keyword1"));
                @SuppressLint("Range") String keyword2_2 = cursor.getString(cursor.getColumnIndex("keyword2"));
                @SuppressLint("Range") String keyword3_2 = cursor.getString(cursor.getColumnIndex("keyword3"));
                @SuppressLint("Range") int fashionOuter2 = cursor.getInt(cursor.getColumnIndex("fashion_outer"));
                @SuppressLint("Range") int fashionTop2 = cursor.getInt(cursor.getColumnIndex("fashion_top"));
                @SuppressLint("Range") int fashionPants2 = cursor.getInt(cursor.getColumnIndex("fashion_pants"));
                @SuppressLint("Range") int fashionShoes2 = cursor.getInt(cursor.getColumnIndex("fashion_shoes"));



                // temperature 값을 화면에 출력하는 코드
                TextView temperatureTextView = findViewById(R.id.TEMP3);
                if (temperature2 == 0) {
                    temperatureTextView.setText("");
                } else {
                    temperatureTextView.setText("Temperature: \n" + temperature2 + "°C");
                }

                // slider 값을 화면에 출력하는 코드
                seekBar.setProgress(sliderValue2);

                // 키워드 값을 화면에 출력하는 코드
                TextView keywordText = findViewById(R.id.KEYWORD3);
                if (keyword1_2 != null) {
                    if (keyword2_2 != null && keyword3_2 !=null){
                        keywordText.setText("#"+keyword1_2+" #"+keyword2_2+"\n#"+keyword3_2);
                    } else if (keyword2_2 != null && keyword3_2 == null) {
                        keywordText.setText("#"+keyword1_2+" #"+keyword2_2);
                    } else if (keyword2_2 == null && keyword3_2 != null) {
                        keywordText.setText("#"+keyword1_2+" #"+keyword3_2);
                    }
                } else {
                    if (keyword2_2 != null && keyword3_2 != null){
                        keywordText.setText("#"+keyword2_2+" #"+keyword3_2);
                    } else if (keyword2_2 != null && keyword3_2 == null) {
                        keywordText.setText("#"+keyword2_2);
                    } else if (keyword2_2 == null && keyword3_2 != null) {
                        keywordText.setText("#"  + keyword3_2);
                    }
                }

                // ImageView 찾기
                ImageView calenderDaily1_2ImageView = findViewById(R.id.calender_daily_item_3);
                ImageView calenderDaily2_2ImageView = findViewById(R.id.calender_daily_item2_3);
                ImageView calenderDaily3_2ImageView = findViewById(R.id.calender_daily_item3_3);
                ImageView calenderDaily4_2ImageView = findViewById(R.id.calender_daily_item4_3);
                // 전달받은 데이터에 따라 이미지 설정
                if (fashionOuter2 != -1 && fashionTop2 != -1 && fashionPants2 != -1 && fashionShoes2 != -1) {
                    calenderDaily1_2ImageView.setImageResource(fashionOuter2);
                    calenderDaily2_2ImageView.setImageResource(fashionTop2);
                    calenderDaily3_2ImageView.setImageResource(fashionPants2);
                    calenderDaily4_2ImageView.setImageResource(fashionShoes2);
                }


            } else {
                // 해당 날짜에 대한 데이터가 없을 경우 처리
                TextView temperatureTextView = findViewById(R.id.TEMP3);
                temperatureTextView.setText("");

                // 슬라이더 값 초기화
                seekBar.setProgress(0);

                // 키워드 값 초기화
                TextView keywordText = findViewById(R.id.KEYWORD3);
                keywordText.setText("");
            }


            cursor.close();
            db.close();
        }



//search

        String firstDate = getIntent().getStringExtra("firstDate");

        if (firstDate != null) {
            // 데이터베이스 조회 및 출력
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    "user_input",
                    new String[] {"temperature", "slider", "keyword1", "keyword2", "keyword3",
                            "fashion_outer", "fashion_top", "fashion_pants", "fashion_shoes"},
                    "date = ?",
                    new String[] {firstDate},
                    null, null, null
            );

            if (cursor.moveToFirst()) {
                // 날짜를 TextView에 설정
                TextView dateTextView = findViewById(R.id.DATE);
                dateTextView.setText("" + firstDate);

                @SuppressLint("Range") int temperature2 = cursor.getInt(cursor.getColumnIndex("temperature"));
                @SuppressLint("Range") int sliderValue2 = cursor.getInt(cursor.getColumnIndex("slider"));
                @SuppressLint("Range") String keyword1_2 = cursor.getString(cursor.getColumnIndex("keyword1"));
                @SuppressLint("Range") String keyword2_2 = cursor.getString(cursor.getColumnIndex("keyword2"));
                @SuppressLint("Range") String keyword3_2 = cursor.getString(cursor.getColumnIndex("keyword3"));
                @SuppressLint("Range") int fashionOuter2 = cursor.getInt(cursor.getColumnIndex("fashion_outer"));
                @SuppressLint("Range") int fashionTop2 = cursor.getInt(cursor.getColumnIndex("fashion_top"));
                @SuppressLint("Range") int fashionPants2 = cursor.getInt(cursor.getColumnIndex("fashion_pants"));
                @SuppressLint("Range") int fashionShoes2 = cursor.getInt(cursor.getColumnIndex("fashion_shoes"));



                // temperature 값을 화면에 출력하는 코드
                TextView temperatureTextView = findViewById(R.id.TEMP3);
                if (temperature2 == 0) {
                    temperatureTextView.setText("");
                } else {
                    temperatureTextView.setText("Temperature: \n" + temperature2 + "°C");
                }

                // slider 값을 화면에 출력하는 코드
                seekBar.setProgress(sliderValue2);

                // 키워드 값을 화면에 출력하는 코드
                TextView keywordText = findViewById(R.id.KEYWORD3);
                if (keyword1_2 != null) {
                    if (keyword2_2 != null && keyword3_2 != null) {
                        keywordText.setText("#" + keyword1_2 + " #" + keyword2_2 + "\n#" + keyword3_2);
                    } else if (keyword2_2 != null && keyword3_2 == null) {
                        keywordText.setText("#" + keyword1_2 + " #" + keyword2_2);
                    } else if (keyword2_2 == null && keyword3_2 != null) {
                        keywordText.setText("#" + keyword1_2 + " #" + keyword3_2);
                    }
                } else {
                    if (keyword2_2 != null && keyword3_2 != null) {
                        keywordText.setText("#" + keyword2_2 + " #" + keyword3_2);
                    } else if (keyword2_2 != null && keyword3_2 == null) {
                        keywordText.setText("#" + keyword2_2);
                    } else if (keyword2_2 == null && keyword3_2 != null) {
                        keywordText.setText("#" + keyword3_2);
                    }
                }
            // ImageView 찾기
                ImageView calenderDaily1_2ImageView = findViewById(R.id.calender_daily_item_3);
                ImageView calenderDaily2_2ImageView = findViewById(R.id.calender_daily_item2_3);
                ImageView calenderDaily3_2ImageView = findViewById(R.id.calender_daily_item3_3);
                ImageView calenderDaily4_2ImageView = findViewById(R.id.calender_daily_item4_3);
                // 전달받은 데이터에 따라 이미지 설정
                if (fashionOuter2 != -1 && fashionTop2 != -1 && fashionPants2 != -1 && fashionShoes2 != -1) {
                    calenderDaily1_2ImageView.setImageResource(fashionOuter2);
                    calenderDaily2_2ImageView.setImageResource(fashionTop2);
                    calenderDaily3_2ImageView.setImageResource(fashionPants2);
                    calenderDaily4_2ImageView.setImageResource(fashionShoes2);
                }

            } else {
                // 해당 날짜에 대한 데이터가 없을 경우 처리
                TextView dateTextView = findViewById(R.id.DATE);
                dateTextView.setText(firstDate);

                TextView temperatureTextView = findViewById(R.id.TEMP3);
                temperatureTextView.setText("");

                // 슬라이더 값 초기화
                seekBar.setProgress(0);

                // 키워드 값 초기화
                TextView keywordText = findViewById(R.id.KEYWORD3);
                keywordText.setText("");
            }

            cursor.close();
            db.close();
        } else {
        }


        ImageButton calender_daily_button1 = (ImageButton) findViewById(R.id.calender_daily_button1);
        calender_daily_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnCaptureClick(view);
                View rootView = getWindow().getDecorView();
                Bitmap screenShot = takeScreenShot(rootView);
                if (screenShot != null) {
                    // 이미지 공유 기능 호출
                    saveAndShareImage(screenShot);
                }
            }
        });

        ImageView calender_daily_button3 = (ImageView) findViewById(R.id.calender_daily_button3);
        calender_daily_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView calender_daily_button3 = (ImageView)findViewById(R.id.calender_daily_button3);
                calender_daily_button3.setVisibility(View.INVISIBLE);
            }
        });

        ImageButton calender_daily_common_closebutton = (ImageButton) findViewById(R.id.calender_daily_common_closebutton);
        calender_daily_common_closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    private void loadSelectedClothesInfo() {
        // 아우터 이미지 불러오기
        ImageView input_outer = findViewById(R.id.input_outer);
        if (fashionOuter != -1) {
            input_outer.setImageResource(fashionOuter);
        } else {
            input_outer.setImageDrawable(null);
        }

        // 상의 이미지 불러오기
        ImageView input_top = findViewById(R.id.input_top);
        if (fashionTop != -1) {
            input_top.setImageResource(fashionTop);
        } else {
            input_top.setImageDrawable(null);
        }

        // 하의 이미지 불러오기
        ImageView input_pants = findViewById(R.id.intput_pants);
        if (fashionPants != -1) {
            input_pants.setImageResource(fashionPants);
        } else {
            input_pants.setImageDrawable(null);
        }

        // 신발 이미지 불러오기
        ImageView input_shoes = findViewById(R.id.input_shoes);
        if (fashionShoes != -1) {
            input_shoes.setImageResource(fashionShoes);
        } else {
            input_shoes.setImageDrawable(null);
        }
    }



    // 스크린샷 캡처
    private Bitmap takeScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    // 이미지 저장 및 공유
    private void saveAndShareImage(Bitmap imageBitmap) {
        // 이미지 파일 저장
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "screenshot.png");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        // 이미지 파일을 외부 저장소의 Pictures 디렉토리에 저장
        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        try {
            OutputStream outputStream = contentResolver.openOutputStream(imageUri);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

            // 이미지를 공유하기 위한 인텐트 생성
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("image/*");
            sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

            // 공유할 수 있는 앱을 선택할 수 있는 액티비티 실행
            startActivity(Intent.createChooser(sharingIntent, "이미지 공유하기"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //캡쳐버튼클릭
    public void mOnCaptureClick(View v){
        //전체화면
        View rootView = getWindow().getDecorView();

        File screenShot = ScreenShot(rootView);
        if(screenShot!=null){
            String imagePath = screenShot.getAbsolutePath(); // 이미지 파일의 경로
            //갤러리에 추가
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imagePath)));

            // 이미지 공유 기능 호출
            shareImage(screenShot);
        }
    }

    //화면 캡쳐하기
    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용

        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환

        String filename = "screenshot.png";
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);  //Pictures폴더 screenshot.png 파일
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
            os.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        view.setDrawingCacheEnabled(false);
        return file;
    }
    // 이미지 공유
    private void shareImage(File imageFile) {
        // 이미지 파일의 경로를 지정
        String imagePath = "/sdcard/Pictures/screenshot.png";

        // 이미지 파일의 경로로부터 Uri 생성
        Uri screenshotUri = Uri.parse(imagePath);

        // 공유 인텐트 생성
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*"); // 이미지 파일 타입을 지정

        // 이미지 Uri를 인텐트에 추가
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);

        // 공유할 수 있는 앱을 선택할 수 있는 액티비티 실행
        startActivity(Intent.createChooser(sharingIntent, "이미지 공유하기"));
    }
    public void onBackPressed() {
        // 뒤로가기(액티비티 종료)
        super.onBackPressed();
    }

}