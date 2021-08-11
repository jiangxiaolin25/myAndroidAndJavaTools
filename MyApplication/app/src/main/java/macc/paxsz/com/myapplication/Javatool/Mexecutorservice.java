package macc.paxsz.com.myapplication.Javatool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 主要是用于创建一个线程池
 * 线程池比线程的好处就是避免了创建和销毁线程对象的消耗
 *相当于已经创建好了一个池子的线程了，只需要你把runnable对象传入进来，我就会分配对应的一个线程给你运行
 * 作者：jiangxiaolin on 2020/3/17
 * 邮箱：jiangxiaolin@xgd.com
 * ToDo：
 */
public class Mexecutorservice {

    /**创建一个可缓存的线程池，若有需求它会回收空闲的线程进行使用，如果没有空闲的线程，则会创建新的线程
     * @param runnable  需要线程池运行的runnabl对象其实就是线程
     */
    public  void myCachedThreadPool(Runnable runnable) {
        ExecutorService  newFixedThreadPool = Executors.newCachedThreadPool();
        newFixedThreadPool.execute(runnable);
    }



}
