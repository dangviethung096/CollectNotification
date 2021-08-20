package personal.littelduck.collectnotification.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import personal.littelduck.collectnotification.MainActivity;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    private MainActivity activity;

    public NotificationBroadcastReceiver(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        int code = intent.getIntExtra("broadcast_code", -1);
        if (code != -1) {
            activity.processNotificationCode();
        }
    }
}
