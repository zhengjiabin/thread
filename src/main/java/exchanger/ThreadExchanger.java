package exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 数据间交换
 * 
 * 作用：每个人在完成一定的事务后想与对方交换数据，第一个先拿出数据的人将一直等待第二个人拿着数据到来时，才能彼此交换数据
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ThreadExchanger {
    public static void main(String[] args) {
        ThreadExchanger test = new ThreadExchanger();
        test.testExchanger();
    }
    
    public void testExchanger() {
        ExecutorService service = Executors.newCachedThreadPool();
        
        final Exchanger<Integer> exchanger = new Exchanger<Integer>();
        for (int i = 0; i < 2; i++) {
            final int data = i;
            Runnable runnable = new Runnable() {
                
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "，准备交换数据：" + data);
                    
                    Integer changeData = null;
                    try {
                        Thread.sleep((long)Math.random() * 10000);
                        System.out.println(Thread.currentThread().getName() + "，开始等待");
                        changeData = exchanger.exchange(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println(Thread.currentThread().getName() + "，交换得到的数据：" + changeData);
                }
            };
            
            service.execute(runnable);
        }
        
        service.shutdown();
    }
}
