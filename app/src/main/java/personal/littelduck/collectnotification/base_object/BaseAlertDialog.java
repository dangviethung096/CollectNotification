package personal.littelduck.collectnotification.base_object;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.widget.Button;

public class BaseAlertDialog extends AlertDialog {
    protected BaseAlertDialog(Context context) {
        super(context);
    }


    public interface Listener {
        void click();
    }
}
