package org.techtown.weartheweather;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    public static final String databaseName = "SignLog.db";
    public DatabaseHelper(@Nullable Context context) {
        super(context, "SignLog.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table users(email TEXT primary key, password TEXT)");
        MyDatabase.execSQL("create Table user_input(" +
                "date TEXT," +
                "temperature INTEGER," +
                "slider INTEGER," +
                "keyword1 TEXT," +
                "keyword2 TEXT," +
                "keyword3 TEXT," +
                "fashion_outer INTEGER," +
                "fashion_top INTEGER," +
                "fashion_pants INTEGER," +
                "fashion_shoes INTEGER" +
                ")");
        MyDatabase.execSQL("create Table alarmTime(time INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        //
        MyDB.execSQL("drop Table if exists user_input");
        //
        // alarmTime 테이블 생성
        MyDB.execSQL("drop Table if exists alarmTime");
    }
    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("users", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }
    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    //닉네임을 저장할 테이블 생성
    public void createNicknameTable() {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        MyDatabase.execSQL("create Table nicknames(email TEXT primary key, nickname TEXT)");
    }

    // 닉네임을 nicknames 테이블에 삽입
    public Boolean insertNickname(String email, String nickname) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("nickname", nickname);
        long result = MyDatabase.insert("nicknames", null, contentValues);
        return result != -1;
    }

    // 이메일로 닉네임 조회
    public String getNicknameByEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String[] projection = {"nickname"};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = MyDatabase.query("nicknames", projection, selection, selectionArgs, null, null, null);

        String nickname = null;
        if (cursor.moveToFirst()) {
            nickname = cursor.getString(cursor.getColumnIndexOrThrow("nickname"));
        }

        cursor.close();
        return nickname;
    }

    // nicknames 테이블 존재 여부 확인
    public boolean isNicknameTableExists() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='nicknames'", null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // 사용자 입력값
    public Boolean insertUserInputData(String date, int temperature, int slider, String keyword1, String keyword2,
                                               String keyword3, int fashion_outer, int fashion_top,
                                               int fashion_pants, int fashion_shoes) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("temperature", temperature);
        contentValues.put("slider", slider);
        contentValues.put("keyword1", keyword1);
        contentValues.put("keyword2", keyword2);
        contentValues.put("keyword3", keyword3);
        contentValues.put("fashion_outer", fashion_outer);
        contentValues.put("fashion_top", fashion_top);
        contentValues.put("fashion_pants", fashion_pants);
        contentValues.put("fashion_shoes", fashion_shoes);


        int rowsAffected = MyDatabase.update("user_input", contentValues,
                "date = ?", new String[]{date});

        if (rowsAffected > 0) {
            // 업데이트가 성공한 경우
            return true;
        } else {
            // 해당 조건의 데이터가 없는 경우, 새로운 데이터로 추가
            long result = MyDatabase.insert("user_input", null, contentValues);
            return result != -1;
        }
    }
    public boolean isInsertOperation(String date, int temperature) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();

        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM user_input WHERE date = ? AND temperature = ?",
                new String[]{date, String.valueOf(temperature)});

        boolean isInsert = cursor.getCount() == 0;
        cursor.close();
        return isInsert;
    }
    // 데이터 입력 유효성 검사 메서드
    public boolean someDataIsMissing(int slider, int fashionOuter, int fashionTop, int fashionPants, int fashionShoes,
                                     String keyword1, String keyword2, String keyword3) {
        return slider == 0 || fashionOuter == 0 || fashionTop == 0 || fashionPants == 0 || fashionShoes == 0 ||
                TextUtils.isEmpty(keyword1) || TextUtils.isEmpty(keyword2) || TextUtils.isEmpty(keyword3);
    }

    // 알람 시간을 업로드하거나 업데이트
    public boolean insertOrUpdateAlarmTime(long timeInMillis) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("time", timeInMillis);

        // 데이터베이스에 해당 알람 시간이 있는지 확인
        Cursor cursor = db.rawQuery("SELECT * FROM alarmTime", null);
        boolean hasData = cursor.moveToFirst(); // 데이터가 있으면 true, 없으면 false
        cursor.close();

        long rowId;

        if (hasData) {
            // 이미 데이터가 있는 경우 업데이트
            rowId = db.update("alarmTime", values, null, null);
        } else {
            // 데이터가 없는 경우 새로 삽입
            rowId = db.insert("alarmTime", null, values);
        }
        db.close();
        return rowId != -1;
    }
    // 알람 시간이 저장되어 있는지 여부를 확인하는 메서드
    public boolean checkAlarmTimeExists() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alarmTime", null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public long getAlarmTime() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT time FROM alarmTime";
        Cursor cursor = db.rawQuery(query, null);

        long alarmTimeInMillis = -1; // Default value

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("time");
            if (columnIndex >= 0) {
                alarmTimeInMillis = cursor.getLong(columnIndex);
            } else {
                Log.e(TAG, "Column index for 'time' not found");
            }
        }
        cursor.close();
        db.close();

        return alarmTimeInMillis;
    }

    public String getEmailByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"email"};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query("users", columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String storedEmail = cursor.getString(cursor.getColumnIndex("email"));
            cursor.close(); // Cursor 닫기
            db.close(); // 데이터베이스 닫기
            return storedEmail;
        }
        db.close(); // 데이터베이스 닫기
        return null;
    }

    public boolean updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        int rowsAffected = db.update("users", values, "email = ?", new String[]{email});

        db.close();

        return rowsAffected > 0;
    }

    public boolean checkEmailExist(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = "users"; // 테이블 이름을 설정해야 합니다.
        String emailColumnName = "email"; // 이메일 컬럼의 이름을 설정해야 합니다.
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + emailColumnName + "=?", new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    /**
     public Cursor getUserInputData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user_input";
        return db.rawQuery(query, null);
     }
     }

     */



}