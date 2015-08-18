package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;

public class Thread3 {
    static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
    
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("main thread start working.");
        
        //只能一个子线程执行完后，才能执行下一个子线程
        for (int i = 1; i < 5; i++) {
            SubThread thread = new SubThread(queue, 2000);
            thread.start();
            
            queue.take();
        }
        
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
        private int sleep;
        
        private BlockingQueue<Integer> queue;
        
        /**
         * @param queue
         */
        public SubThread(BlockingQueue<Integer> queue, int sleep) {
            this.queue = queue;
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
                try {
                    queue.put(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
        }
    }
}
