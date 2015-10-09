package com.example.mall.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.mall.R;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.mall.notification.NotificationView.java
 * @author: liqiongwei
 * @date: 2015-10-09 15:57
 */
public class NotificationView {
    private static final String TAG = NotificationView.class.getSimpleName();

    private static NotificationView instance = null;
    public NotificationManager mNotificationManager;
    public Notification mNotification;
    private Context mContext;
    public static final int VERSION_NOTIFICATION_ID = 1;

    private NotificationView(Context context) {

        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        this.mContext = context;
    }

    public static NotificationView getInstance(Context ct) {
        if (instance == null) {
            instance = new NotificationView(ct);
        }
        return instance;
    }

    public void show() {
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = mContext.getResources().getString(
                R.string.download_apk_tip);
        long timestamp = System.currentTimeMillis();
        mNotification = new Notification(icon, tickerText, timestamp);

        RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
                R.layout.layout_notification_view);
        contentView.setImageViewResource(R.id.update_app_icon, icon);
        mNotification.contentView = contentView;

        Intent intent = new Intent("");
        PendingIntent contentIntent = PendingIntent.getBroadcast(mContext, 0,
                intent, 0);
        mNotification.contentIntent = contentIntent;
        mNotificationManager.notify(VERSION_NOTIFICATION_ID, mNotification);
    }
}
