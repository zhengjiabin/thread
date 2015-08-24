package roadblock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 路障
 * 
 * 作用：周末时间集体郊游，公司员工各自从家里出发，三三两两到公司集合后，再同时出发去郊游。
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ThreadCyclicBarrier {
    public static void main(String[] args) {
        ThreadCyclicBarrier test = new ThreadCyclicBarrier();
        test.testCyclicBarrier();
    }
    
    public void testCyclicBarrier() {
        ExecutorService service = Executors.newCachedThreadPool();
        
        //不能和lock合同，否则等待时，若是被锁住，则其他线程均进入不了
        //        final Lock lock = new ReentrantLock();
        
        int parties = 3;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);
        for (int i = 0; i < parties; i++) {
            Runnable runnable = new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep((long)Math.random() * 1000);
                        
                        System.out.println("线程：" + Thread.currentThread().getName() + "到达集合点1，当前已有：" + (cyclicBarrier.getNumberWaiting() + 1) + " 个到达");
                        cyclicBarrier.await();
                        
                        System.out.println("都到齐了，开始郊游");
                        
                        Thread.sleep((long)Math.random() * 1000);
                        System.out.println("线程：" + Thread.currentThread().getName() + "到达集合点2，当前已有：" + (cyclicBarrier.getNumberWaiting() + 1) + " 个到达");
                        cyclicBarrier.await();
                        
                        System.out.println("都到齐了，开始返航");
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            
            service.execute(runnable);
        }
        
        //缓存线程池，当线程池空闲时，一段时间后，会自动执行shutdown
        service.shutdown();
    }
}
