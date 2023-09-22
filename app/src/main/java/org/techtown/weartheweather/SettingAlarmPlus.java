package org.techtown.weartheweather;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingAlarmPlus {
    private static final String PREFS_NAME = "MyPrefs";
    private static final String SWITCH_STATE_KEY = "switchState";

    private final SharedPreferences preferences;

    public SettingAlarmPlus(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveSwitchState(boolean switchState) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(SWITCH_STATE_KEY, switchState);
        editor.apply();
    }

    public boolean loadSwitchState() {
        return preferences.getBoolean(SWITCH_STATE_KEY, false); // 기본값은 false로 설정 (OFF 상태)
    }
}

