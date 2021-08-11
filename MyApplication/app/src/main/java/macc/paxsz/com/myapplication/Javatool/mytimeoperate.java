package macc.paxsz.com.myapplication.Javatool;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 作者：jiangxiaolin on 2020/5/11
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class mytimeoperate {


    /**
     * 获取当前时间yyyy-MM-dd_HH-mm-ss格式可以随便更改
     * @return
     */
    public String getcurrenttime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);// 日期格式名定义
            String fname =sdf.format(new Date());

          return fname;
        }

    public  String getWeek(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String week = sdf.format(new Date());
        return week;
    }

    /**通过 Calendar获取消耗的时间
     * @return
     */
    public Long GetSpentTime() {
        Calendar c2 = Calendar.getInstance();
        Calendar c3 = Calendar.getInstance();
        long time2 = c2.getTimeInMillis() - c3.getTimeInMillis();
          return time2;
        }






}
