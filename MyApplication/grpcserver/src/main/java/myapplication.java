import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by jiangxiaolin on 2021/8/10.
 */

public class myapplication extends MultiDexApplication {
    public myapplication() {
        super();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
