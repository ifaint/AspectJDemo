package demo.aspect;

/**
 * Created by zhangxiaofei on 2017/3/9.
 */

import android.util.Log;

/**
 * Wrapper around [email protected] android.util.Log}
 */
public class DebugLog {

    private DebugLog() {}

    /**
     * Send a debug log message
     *
     * @param tag Source of a log message.
     * @param message The message you would like logged.
     */
    public static void log(String tag, String message) {
        Log.d(tag, message);
    }
}
