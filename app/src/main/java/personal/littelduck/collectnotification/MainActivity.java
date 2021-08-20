package personal.littelduck.collectnotification;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;

import personal.littelduck.collectnotification.adapter.StatusAdapter;
import personal.littelduck.collectnotification.base_object.BaseActivity;
import personal.littelduck.collectnotification.broadcast_receiver.NotificationBroadcastReceiver;
import personal.littelduck.collectnotification.common_object.Status;
import personal.littelduck.collectnotification.databinding.ActivityMainBinding;
import personal.littelduck.collectnotification.notification.MyNotificationListenerService;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private static StatusAdapter statusAdapter;

    public MainActivity() {
        TAG = MainActivity.class.getCanonicalName();
        statusAdapter = new StatusAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set listener
        binding.btStartService.setOnClickListener(btStartServiceListener);

        // Show
        if (isNotificationServiceEnabled() || isServiceStarted()) {
            showStopService();
        } else {
            showStartService();
        }

        setRecyclerView();

        // Receiver broadcast listener
        NotificationBroadcastReceiver receiver = new NotificationBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getPackageName());
        registerReceiver(receiver, intentFilter);
    }

    private final View.OnClickListener btStartServiceListener = (v) -> {
        Log.i(TAG, "[btStartServiceListener]");
        if (isNotificationServiceEnabled()) {
            startNotificationListenerService();
        } else {
            showPrivilegeDialog();
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

    private boolean isServiceStarted() {
        return true;
    }

    private void showPrivilegeDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_self);
        dialogBuilder.setTitle(R.string.privilege);
        dialogBuilder.setMessage(R.string.privilege_requirement);
        dialogBuilder.setPositiveButton(R.string.ok, (dialog, which) -> {
            startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
        });

        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {

        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    private void showStartService() {
        binding.btStartService.setVisibility(View.VISIBLE);
        binding.btStopService.setVisibility(View.GONE);
        binding.tvStatus.setText(R.string.off);
    }

    private void showStopService() {
        binding.btStopService.setVisibility(View.VISIBLE);
        binding.btStartService.setVisibility(View.GONE);
        binding.tvStatus.setText(R.string.on);
    }

    public void processNotificationCode(MyNotificationListenerService.BroadcastCode broadcastCode) {
        Log.i(TAG, "[processNotificationCode]");
        Status status;
        switch (broadcastCode.code) {
            case MyNotificationListenerService.Code.ON_CREATED:
                status = new Status();

                break;
            case MyNotificationListenerService.Code.ON_BIND:
                break;
            case MyNotificationListenerService.Code.LISTENER_CONNECTED:
                break;
            case MyNotificationListenerService.Code.LISTENER_DISCONNECTED:
                break;
            case MyNotificationListenerService.Code.ON_DESTROY:
                break;
            case MyNotificationListenerService.Code.NEW_NOTIFICATION:
                break;
        }
    }

    public void setRecyclerView() {
        binding.rvLog.setAdapter(statusAdapter);
    }

}