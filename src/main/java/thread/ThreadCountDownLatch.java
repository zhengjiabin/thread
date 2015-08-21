package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * 针对多个子线程，主线程等待子线程结束后，再执行
 * 
 * @author Administrator
 * 
 */
public class ThreadCountDownLatch {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("main thread start working.");
        
        int threads = 5;
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        
        //在一个主线程下，同时执行多个子线程
        for (int i = 0; i < threads; i++) {
            SubThread thread = new SubThread(2000 * (i + 1), countDownLatch);
            thread.start();
        }
        
        //待子线程结束后，继续执行主线程
        countDownLatch.await();
        
        mainThreadWorkingOtherThings();
        
        System.out.println("main thread end working.");
    }
    
    private static void mainThreadWorkingOtherThings() {
        System.out.println("main thread start other working.");
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("main thread end other working.");
    }
    
    public static class SubThread extends Thread {
        private CountDownLatch countDownLatch;
        
        private int sleep;
        
        public SubThread(int sleep, CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
            this.sleep = sleep;
        }
        
        @Override
        public void run() {
            try {
                System.out.println(sleep + ": sub thread start working.");
                
                sleep(sleep);
                
                System.out.println(sleep + ": sub thread end working.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }
}
