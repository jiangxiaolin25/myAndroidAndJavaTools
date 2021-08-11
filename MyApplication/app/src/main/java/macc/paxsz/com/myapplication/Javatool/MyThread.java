package macc.paxsz.com.myapplication.Javatool;


import android.util.Log;

public class MyThread extends Thread {

    int toal;
    int index = 0;

    private final Object lock = new Object();

    public boolean pause = false;

    /**
     * 调用该方法实现线程的暂停
     */
   public void pauseThread(){
        pause = true;
    }


    /*
    调用该方法实现恢复线程的运行
     */
    public  void resumeThread(){
        pause =false;
        synchronized (lock){
            lock.notify();
        }
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
     */
    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();

        while(index<5000){
            while (pause){
                index=toal;
                onPause();
            }
            try {
                System.out.println(index);
                Thread.sleep(50);
                toal=index;
                ++index;
                 Log.v("TAG",""+index);

            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }
}

