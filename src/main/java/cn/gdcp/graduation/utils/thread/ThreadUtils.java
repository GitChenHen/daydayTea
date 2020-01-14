package cn.gdcp.graduation.utils.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadUtils {
		/**线程锁*/
		private static ExecutorService threadPool = null;
		static {
			//获取设备最大线程池
//			threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
			//获取当前cpu的线程数
			int threadCount = Runtime.getRuntime().availableProcessors();
			threadCount = threadCount <= 1?2:threadCount;//如果cpu只有一条线程默认给予2
			threadPool = new ThreadPoolExecutor(//创建一个有队列的线程池,超出执行线程会放到队列中等待
								threadCount,//
								threadCount*3,//最大线程数
				                60,//空闲线程存活时间
				                TimeUnit.MILLISECONDS,//时间单位
				                new LinkedBlockingQueue<Runnable>(threadCount*3));//队列
		}

		public static ExecutorService getThreadPool() {
			return threadPool;
		}
}
