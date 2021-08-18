package personal.littelduck.collectnotification.notification;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


public class MyNotificationListenerService extends NotificationListenerService {
    private final String TAG = MyNotificationListenerService.class.getCanonicalName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "[onCreate]");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "[onBind]");
        return super.onBind(intent);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "[onListenerConnected]");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.i(TAG, "[onListenerDisconnected]");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.i(TAG, "[onNotificationPosted]");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "[onDestroy]");
    }
}
