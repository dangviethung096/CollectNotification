package personal.littelduck.collectnotification.base_object;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.widget.Button;

public class BaseAlertDialog extends AlertDialog {
    protected BaseAlertDialog(Context context) {
        super(context);
    }


    public static class Builder {
        private Context context;
        private BaseAlertDialog alertDialog;

        public Builder(Context context) {
            this.context = context;
            alertDialog = new BaseAlertDialog(context);
            alertDialog.setTitle("Inform");
            alertDialog.setMessage("Nothing");
        }

        public Builder setTitle(String title) {
            alertDialog.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            alertDialog.setMessage(message);
            return this;
        }

        public Builder setNegativeButton(Listener listener) {
            setButton(alertDialog.getButton(BUTTON_NEGATIVE), listener);
            return this;
        }

        public Builder setPositiveButton(Listener listener) {
            setButton(alertDialog.getButton(BUTTON_POSITIVE), listener);
            return this;
        }

        private void setButton(Button button, Listener listener) {
            button.setOnClickListener((v) -> {
                listener.click();
            });
        }

        public BaseAlertDialog build() {
            return alertDialog;
        }
    }

    public interface Listener {
        void click();
    }
}
