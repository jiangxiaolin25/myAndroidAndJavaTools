package macc.paxsz.com.myapplication.Androidtool;

import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * 作者：jiangxiaolin on 2019/10/15
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：用于安卓记录sharepreference的使用
 */
public class sharepreference {

    /**
     * @param i 输入参数记录
     * 备注：
     *需要放在mainactivity里面getSharedPreferences才不会报错
     */
    private void putintSP(int i) {
//        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//        editor.putInt("key", i);
//        editor.commit();


    } private void putstringSP(int i) {
//        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
//        editor.putInt("key", i);
//        editor.commit();


    }

    /**
     * @return 获取参数
     * 备注：
     * 需要放在mainactivity里面getSharedPreferences才不会报错
     */
    private int getintSP() {
        int u;

//        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
//         u = preferences.getInt("key", 0);
        return 1;

    }

//    private String getstringSP() {
//        String u;
//
////        SharedPreferences preferences = getSharedPreferences("data", MODE_PRIVATE);
////         u = preferences.getString("key", "空");
//        return u;
//
//    }

}
