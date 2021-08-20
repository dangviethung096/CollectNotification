package personal.littelduck.collectnotification.notification;

import android.content.Intent;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;


public class MyNotificationListenerService extends NotificationListenerService {
    private final String TAG = MyNotificationListenerService.class.getCanonicalName();
    private boolean isConnected;

    public MyNotificationListenerService() {
        isConnected = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "[onCreate]");
        sendCode(new BroadcastCode(Code.ON_CREATED));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "[onBind]");
        sendCode(new BroadcastCode(Code.ON_BIND));
        return super.onBind(intent);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.i(TAG, "[onListenerConnected]");
        sendCode(new BroadcastCode(Code.LISTENER_CONNECTED));
        isConnected = true;
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.i(TAG, "[onListenerDisconnected]");
        sendCode(new BroadcastCode(Code.LISTENER_DISCONNECTED));
        isConnected = false;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Log.i(TAG, "[onNotificationPosted]");
        if (isConnected) {
            Log.i(TAG, "[onNotificationPosted] Send to main thread");
            sendCode(new BroadcastCode(Code.NEW_NOTIFICATION));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "[onDestroy]");
        sendCode(new BroadcastCode(Code.ON_DESTROY));
    }


    public void sendCode(BroadcastCode bc) {
        String pkgName = getPackageName();
        Intent intent = new Intent(pkgName);
        intent.putExtra("broadcast_code", bc.code);
        sendBroadcast(intent);
    }

    public static final class BroadcastCode {
        public int code;
        public BroadcastCode(int code) {
            this.code = code;
        }
    }

    public static final class Code {
        public static final int ON_CREATED = 0;
        public static final int ON_BIND = 1;
        public static final int LISTENER_CONNECTED = 2;
        public static final int LISTENER_DISCONNECTED = 3;
        public static final int NEW_NOTIFICATION = 4;
        public static final int ON_DESTROY = 5;
    }
}
