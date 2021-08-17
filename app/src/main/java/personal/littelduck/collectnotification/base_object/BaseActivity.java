package personal.littelduck.collectnotification.base_object;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected BaseActivity _self;
    protected static String TAG;

    public BaseActivity() {
        _self = this;
        TAG = BaseActivity.class.getCanonicalName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }
}
