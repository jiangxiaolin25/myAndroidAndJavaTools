package macc.paxsz.com.myapplication.Androidtool;

import android.util.Log;

/**
 * 自定义log，如果是调试模式就设置level = VERBOSE，如果是release模式就是level = NOTHING，表示所有log都不会打印
 * 作者：jiangxiaolin on 2019/10/12
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class logutil {

    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    public static int level = VERBOSE;

    public static void v( String msg) {
        if (level <= VERBOSE) {
             Log.v("TAG",msg);

        }
    }

    public static void d( String msg) {
        if (level <= DEBUG) {
            Log.d("TAG",msg);
        }
    }

    public static void i( String msg) {
        if (level <= INFO) {
            Log.i("TAG", msg);
        }
    }

    public static void w( String msg) {
        if (level <= WARN) {
            Log.w("TAG", msg);
        }
    }

    public static void e( String msg) {
        if (level <= ERROR) {
            Log.e("TAG", msg);
        }
    }

}
