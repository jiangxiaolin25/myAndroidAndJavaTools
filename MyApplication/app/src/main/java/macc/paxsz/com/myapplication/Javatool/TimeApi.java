package macc.paxsz.com.myapplication.Javatool;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeApi {
	//	延迟指定的秒后开始运行
	public static void time1() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务1--------");
			}
		}, 2000);// 设定指定的时间time,此处为2000

	}


	/***
	 * 延迟多少S后开始周期性运行
	 * 1000 延迟1S后开始运行
	 * 2000 每个这个时间运行一次
	 *     */
	public static void time2() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, 1000, 1000);

	}

	/**
	 * 第三种方法：设定指定任务task在指定延迟delay后进行固定频率peroid的执行
	 */
	public static void time3() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, 1000, 2000);

	}

	/***
	 * 每天的每个时间固定运行测试
	 */
	public static void time4() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
		calendar.set(Calendar.MINUTE, 0);       // 控制分
		calendar.set(Calendar.SECOND, 0);       // 控制秒
		Date time = calendar.getTime();         // 得出执行任务的时间,此处为今天的12：00：00
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("-------设定要指定任务--------");
			}
		}, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
	}





}
