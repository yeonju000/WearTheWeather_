package org.techtown.weartheweather;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    DatabaseHelper dbHelper;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dbHelper = new DatabaseHelper(getActivity());

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener timeSetListener = (view, selectedHour, selectedMinute) -> {
            // 원래의 OnTimeSetListener 동작을 수행
            ((TimePickerDialog.OnTimeSetListener) getActivity()).onTimeSet(view, selectedHour, selectedMinute);

            // 선택된 시간을 Calendar 객체에 설정
            c.set(Calendar.HOUR_OF_DAY, selectedHour);
            c.set(Calendar.MINUTE, selectedMinute);
            c.set(Calendar.SECOND, 0);

            long timeInMillis = c.getTimeInMillis();

            // 알람 시간을 업로드하거나 업데이트
            dbHelper.insertOrUpdateAlarmTime(timeInMillis);
        };

        return new TimePickerDialog(getActivity(), timeSetListener, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}
