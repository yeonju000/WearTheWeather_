package org.techtown.weartheweather;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channelID = "channelID";
    public static final String channelNm = "channelNm";

    private NotificationManager notiManager;

    public NotificationHelper(Context base) {
        super(base);

        //안드로이드 버전이 오레오거나 이상이면 채널생성
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {

        NotificationChannel channel1 = new NotificationChannel(channelID, channelNm, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(com.google.android.material.R.color.design_default_color_on_primary); //약간 수정
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        if(notiManager == null) {
            notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notiManager;
    }

    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle("오늘의 날씨를 확인하고")
                .setContentText("날씨에 맞는 자신만의 옷을 기록해 보세요! ")
                .setSmallIcon(R.drawable.splash);
    }
}

