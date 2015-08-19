package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ����Executors�̳߳�
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
     * ���Զ�ʱ������̳߳�
     */
    public void testScheduledThread2() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        
        // ��ʱ2���ִ�У�֮��ÿ��3����ִ����Ӧ���񣬼����ͷŶ�Ӧ���̣߳�ʣ���7�������޷�ִ�С�
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
     * ���Զ�ʱ������̳߳�
     */
    public void testScheduledThread() {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(3);
        
        //��ʱ2���ִ�У����ڳ�����ֻ��3���̣߳���ÿִ��3���߳�֮��ȴ�2�룬ִ�н�����������
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
     * ���Ե����̵߳��̳߳�
     */
    public void testSingleThread() {
        // ���������̣߳��̳߳����߳�ִ������Ĺ����У����񱨴��ˣ����Զ�����һ���µ��߳�
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        
        // ����10��������Ҫ10���߳�
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
     * ���Զ�̬�߳������̳߳�
     */
    public void testCachedThreadPool() {
        // ������̬���̳߳أ�����������Ҫ�����������Զ����ӣ����߳̿��У�����һ��ʱ����Զ��رն�Ӧ���߳�
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        // ����10��������Ҫ10���߳�
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
     * ���Թ̶��߳������̳߳�
     */
    public void testFixedThread() {
        // ����3���̵߳ĳ���
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        
        // ����10��������Ҫ10���̣߳����ڳ�����ֻ��3���̣߳�ֻ�������Ŷ�ʹ��
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
        
        //���̳߳��е��߳̿���ʱ���ر��̳߳��е��߳�
        //        threadPool.shutdown();
        //�����̳߳��е��߳��Ƿ���У����ر��̳߳��е��߳�
        //        threadPool.shutdownNow();
    }
}
