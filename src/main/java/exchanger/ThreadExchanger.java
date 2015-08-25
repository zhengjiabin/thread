package exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * ���ݼ佻��
 * 
 * ���ã�ÿ���������һ�������������Է��������ݣ���һ�����ó����ݵ��˽�һֱ�ȴ��ڶ������������ݵ���ʱ�����ܱ˴˽�������
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
                    System.out.println(Thread.currentThread().getName() + "��׼���������ݣ�" + data);
                    
                    Integer changeData = null;
                    try {
                        Thread.sleep((long)Math.random() * 10000);
                        System.out.println(Thread.currentThread().getName() + "����ʼ�ȴ�");
                        changeData = exchanger.exchange(data);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    System.out.println(Thread.currentThread().getName() + "�������õ������ݣ�" + changeData);
                }
            };
            
            service.execute(runnable);
        }
        
        service.shutdown();
    }
}
