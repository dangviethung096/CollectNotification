package personal.littelduck.collectnotification.common_object;

import java.util.Date;

public class Status {
    Date time;
    String message;
    Type type;

    public Status() {
        time = new Date();
        message = "";
        type = Type.NONE;
    }

    public static enum Type {
        CREATE,
        DESTROY,
        BIND,
        NEW_NOTIFICATION,
        LISTENER_CONNECTED,
        LISTENER_DISCONNECTED,
        NONE;
    }
}
