package roadblock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * ·��
 * 
 * ���ã���ĩʱ�伯�彼�Σ���˾Ա�����ԴӼ��������������������˾���Ϻ���ͬʱ����ȥ���Ρ�
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
        
        //���ܺ�lock��ͬ������ȴ�ʱ�����Ǳ���ס���������߳̾����벻��
        //        final Lock lock = new ReentrantLock();
        
        int parties = 3;
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);
        for (int i = 0; i < parties; i++) {
            Runnable runnable = new Runnable() {
                
                public void run() {
                    try {
                        Thread.sleep((long)Math.random() * 1000);
                        
                        System.out.println("�̣߳�" + Thread.currentThread().getName() + "���Ｏ�ϵ�1����ǰ���У�" + (cyclicBarrier.getNumberWaiting() + 1) + " ������");
                        cyclicBarrier.await();
                        
                        System.out.println("�������ˣ���ʼ����");
                        
                        Thread.sleep((long)Math.random() * 1000);
                        System.out.println("�̣߳�" + Thread.currentThread().getName() + "���Ｏ�ϵ�2����ǰ���У�" + (cyclicBarrier.getNumberWaiting() + 1) + " ������");
                        cyclicBarrier.await();
                        
                        System.out.println("�������ˣ���ʼ����");
                        
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            };
            
            service.execute(runnable);
        }
        
        //�����̳߳أ����̳߳ؿ���ʱ��һ��ʱ��󣬻��Զ�ִ��shutdown
        service.shutdown();
    }
}
