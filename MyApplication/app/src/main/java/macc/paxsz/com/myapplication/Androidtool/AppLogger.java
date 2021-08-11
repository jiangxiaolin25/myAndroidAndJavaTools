package macc.paxsz.com.myapplication.Androidtool;


/***
 * 主要是编译的打印的信息会比较齐全，可以看到很多信息，方便可以定位问题
 */

public class AppLogger {
     static boolean  isprintlog=true;

    private AppLogger() {

    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v( String tag, String msg) {
        if(isprintlog){
            android.util.Log.v(tag, buildMessage(msg));
        }

    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void v(String tag,String msg, Throwable thr) {
        if(isprintlog){
            android.util.Log.v(tag, buildMessage(msg), thr);
        }

    }

    /**
     * Send a DEBUG log message.
     *
     * @param msg
     */
    public static void d(String tag,String msg) {
        if(isprintlog){
            android.util.Log.d(tag, buildMessage(msg));
        }

    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static void d(String tag,String msg, Throwable thr) {

        if(isprintlog){
            android.util.Log.d(tag, buildMessage(msg), thr);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {

        if(isprintlog){
            android.util.Log.i(tag, buildMessage(msg));
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static  void i(String tag,String msg, Throwable thr) {
        if(isprintlog){
            android.util.Log.i(tag, buildMessage(msg), thr);
        }

    }

    /**
     * Send an ERROR log message.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String tag,String msg) {
        if(isprintlog){
            android.util.Log.e(tag, buildMessage(msg));
        }


    }

    /**
     * Send a WARN log message
     *
     * @param msg The message you would like logged.
     */
    public static  void w(String tag,String msg) {
        if(isprintlog){
            android.util.Log.w(tag, buildMessage(msg));
        }


    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static  void w(String tag,String msg, Throwable thr) {
        if(isprintlog){
            android.util.Log.w(tag, buildMessage(msg), thr);
        }

    }

    /**
     * Send an empty WARN log message and log the exception.
     *
     * @param thr An exception to log
     */
    public static void w(String tag,Throwable thr) {
        if(isprintlog){
            android.util.Log.w(tag, buildMessage(""), thr);
        }

    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     * @param thr An exception to log
     */
    public static  void e(String tag,String msg, Throwable thr) {

        if(isprintlog){
            android.util.Log.e(tag, buildMessage(msg), thr);
        }
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];

        return new StringBuilder()
                .append(caller.getClassName())
                .append(".")
                .append(caller.getMethodName())
                .append("() line:")
                .append(caller.getLineNumber())
                .append(" ")
                .append(msg).toString();
    }
}
