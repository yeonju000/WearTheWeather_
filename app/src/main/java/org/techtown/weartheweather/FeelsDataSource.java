package org.techtown.weartheweather;


import android.content.Context;

public class FeelsDataSource {
    private DatabaseHelper dbHelper;

        public FeelsDataSource(Context context) {
            dbHelper = new DatabaseHelper(context);
        }

        public void open() {
            dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }



}