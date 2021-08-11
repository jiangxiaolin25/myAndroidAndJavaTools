package macc.paxsz.com.myapplication.Javatool;

import android.util.Log;

import java.util.HashMap;
import java.util.Set;

/**
 * 作者：jiangxiaolin on 2021/4/19
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class collectAPi {

    /**  遍历hashmap集合
     * @param hashMap
     * @return
     */
    public String getMapAllItem(HashMap hashMap) {
        Set<String> keys = hashMap.keySet(); //此行可省略，直接将map.keySet()写在for-each循环的条件中
        for (String key : keys) {
             Log.i("TAG","key值：" + key + " value值：" + hashMap.get(key));
        }
        return "测试";
    }

    /**冒泡排序
     * @param para1    后面第几位不参与冒泡比较  传入1 表示最后面一位不参与比较
     * @param numbers  数组
     */
    public static void SortList(int para1, Integer[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = 0; j < numbers.length - 1 - i - para1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }


    }



}
