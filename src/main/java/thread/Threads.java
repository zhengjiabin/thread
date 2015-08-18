package thread;

/**
 * 主线程执行中，等待子线程处理结束后，再继续执行
 * 
 * @author Administrator
 * 
 */
public class Threads {
    public static void main(String[] args) {
        System.out.println("main thread start working.");
        
        //只能一个子线程执行完后，才能执行下一个子线程
        for (int i = 1; i < 5; i++) {
            SubThread thread = new SubThread(i * 2000);
            thread.start();
            
            //主线程中，调用join方法，等待子线程结束后，继续往下执行
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
