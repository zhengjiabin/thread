package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试Executors线程池
 * 
 * @author Administrator
 * 
 */
public class ExecutorsPool {
    public static void main(String[] args) {
        ExecutorsPool pool = new ExecutorsPool();
        //        pool.testFixedThread();
        
        //        pool.testCachedThreadPool();
        
        //        pool.testSingleThread();
        
        //        pool.testScheduledThread();
        
        pool.testScheduledThread2();
    }
    
    /**
     * 测试定时任务的线程池
     */
    public void testScheduledThread2() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        
        // 定时2秒后执行，之后每隔3秒再执行相应任务，即不释放对应的线程，剩余的7个任务无法执行。
        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " execute task " + task);
                }
            }, 2, 3, TimeUnit.SECONDS);
        }
        
        System.out.println();
    }
    
    /**
     * 测试定时任务的线程池
     */
    public void testScheduledThread() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        
        //定时2秒后执行，由于池子中只有3个线程，即每执行3个线程之后等待2秒，执行接下来的任务
        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.schedule(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getName() + " execute task " + task);
                }
            }, 2, TimeUnit.SECONDS);
        }
        
        System.out.println();
    }
    
    /**
     * 测试单个线程的线程池
     */
    public void testSingleThread() {
        // 创建单个线程，线程池中线程执行任务的过程中，任务报错了，可自动创建一个新的线程
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        
        // 创建10个任务，需要10个线程
        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println(Thread.currentThread().getName() + " execute task " + task);
                    throw new RuntimeException();
                }
            });
        }
        
        System.out.println();
    }
    
    /**
     * 测试动态线程数的线程池
     */
    public void testCachedThreadPool() {
        // 创建动态的线程池，数量根据需要的任务数，自动增加，当线程空闲，并过一段时间后，自动关闭对应的线程
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        // 创建10个任务，需要10个线程
        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println(Thread.currentThread().getName() + " execute task " + task);
                }
            });
        }
        
        System.out.println();
    }
    
    /**
     * 测试固定线程数的线程池
     */
    public void testFixedThread() {
        // 创建3个线程的池子
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        
        // 创建10个任务，需要10个线程，由于池子中只有3个线程，只能依次排队使用
        for (int i = 1; i <= 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println(Thread.currentThread().getName() + " execute task " + task);
                }
            });
        }
        
        //当线程池中的线程空闲时，关闭线程池中的线程
        //        threadPool.shutdown();
        //不管线程池中的线程是否空闲，均关闭线程池中的线程
        //        threadPool.shutdownNow();
    }
}
