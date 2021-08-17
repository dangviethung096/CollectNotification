package personal.littelduck.collectnotification;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import personal.littelduck.collectnotification.base_object.BaseActivity;
import personal.littelduck.collectnotification.databinding.ActivityMainBinding;
import personal.littelduck.collectnotification.notification.MyNotificationListenerService;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

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
        startNotificationListenerService();
    };

    private void requestServicePermission() {
        Log.i(TAG, "[requestServicePermission]");
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE;
        requestPermissions(permissions,100);
    }

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
}