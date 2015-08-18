package thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <pre>
 * ExecutorService
 * ���߳�ִ���У��ȴ����̶߳�ִ�н������ڼ�������ִ��
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class Threads2 {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("main thread start working.");
        
        //ֻ��һ�����߳�ִ����󣬲���ִ����һ�����߳�
        for (int i = 1; i < 5; i++) {
            SubThread thread = new SubThread(2000);
            Future future = executorService.submit(thread);
            
            future.get();
        }
        
        executorService.shutdown();
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
        private int sleep = 0;
        
        public SubThread(int sleep) {
            this.sleep = sleep;
        }
        
        @Override
        public void run() {
            System.out.println(sleep + ": sub thread start working.");
            
            try {
                sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println(sleep + ": sub thread end working.");
        }
    }
}
