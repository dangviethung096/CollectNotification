package personal.littelduck.collectnotification;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import personal.littelduck.collectnotification.base_object.BaseActivity;
import personal.littelduck.collectnotification.base_object.BaseAlertDialog;
import personal.littelduck.collectnotification.databinding.ActivityMainBinding;
import personal.littelduck.collectnotification.notification.MyNotificationListenerService;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";

    public MainActivity() {
        TAG = MainActivity.class.getCanonicalName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btStartService.setOnClickListener(btStartServiceListener);
    }

    private View.OnClickListener btStartServiceListener = (v) -> {
        Log.i(TAG, "[btStartServiceListener]");
        if (isNotificationServiceEnabled()) {
            startNotificationListenerService();
        } else {
            BaseAlertDialog.Builder dialogBuilder = new BaseAlertDialog.Builder(getApplicationContext());
        }

    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startNotificationListenerService();
        } else {
            Log.i(TAG, "[onRequestPermissionsResult] DENIED - system don't provide permission");
        }
    }

    private void startNotificationListenerService() {
        Log.i(TAG, "[startNotificationListenerService]");
        Intent intent = new Intent(this, MyNotificationListenerService.class);
        startService(intent);
    }

    /**
     * Is Notification Service Enabled.
     * Verifies if the notification listener service is enabled.
     * Got it from: https://github.com/kpbird/NotificationListenerService-Example/blob/master/NLSExample/src/main/java/com/kpbird/nlsexample/NLService.java
     * @return True if enabled, false otherwise.
     */
    private boolean isNotificationServiceEnabled(){
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}