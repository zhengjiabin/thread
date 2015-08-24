package signal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 信号灯
 * 控制同时访问资源的线程个数。
 * 
 * 作用：举例：有5个坑，假如有10个人要入坑，同时只能5个入坑，另外5个在外面等候，
 * 等里面有人出来时，外面等候的5人，可按先等的先入坑，或者随机入坑。
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ThreadSemaphore {
    public static void main(String[] args) {
        ThreadSemaphore test = new ThreadSemaphore();
        test.testSemaphore();
    }
    
    public void testSemaphore() {
        final Lock lock = new ReentrantLock();
        ExecutorService service = Executors.newCachedThreadPool();
        final int permits = 3;
        final Semaphore semaphore = new Semaphore(permits);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                
                public void run() {
                    try {
                        //查询灯是否亮，即是否有坑位，无坑位，则阻塞，直到有坑位，自动往下执行
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    lock.lock();
                    try {
                        System.out.println("线程：" + Thread.currentThread().getName() + "进入，当前已有：" + (permits - semaphore.availablePermits()) + "并发");
                    } finally {
                        lock.unlock();
                    }
                    
                    try {
                        Thread.sleep((long)Math.random() * 10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    lock.lock();
                    try {
                        System.out.println("线程：" + Thread.currentThread().getName() + "离开，当前剩余：" + (permits - 1 - semaphore.availablePermits()) + "并发");
                        //释放坑位，并通知其他等待的
                        semaphore.release();
                    } finally {
                        lock.unlock();
                    }
                    
                }
            };
            
            service.execute(runnable);
        }
        
    }
}
