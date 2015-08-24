package signal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * �źŵ�
 * ����ͬʱ������Դ���̸߳�����
 * 
 * ���ã���������5���ӣ�������10����Ҫ��ӣ�ͬʱֻ��5����ӣ�����5��������Ⱥ�
 * ���������˳���ʱ������Ⱥ��5�ˣ��ɰ��ȵȵ�����ӣ����������ӡ�
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ThreadSemaphore {
    public static void main(String[] args) {
        ThreadSemaphore test = new ThreadSemaphore();
        test.testSemaphore();
    }
    
    public void testSemaphore() {
        final Lock lock = new ReentrantLock();
        ExecutorService service = Executors.newCachedThreadPool();
        final int permits = 3;
        final Semaphore semaphore = new Semaphore(permits);
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                
                public void run() {
                    try {
                        //��ѯ���Ƿ��������Ƿ��п�λ���޿�λ����������ֱ���п�λ���Զ�����ִ��
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    lock.lock();
                    try {
                        System.out.println("�̣߳�" + Thread.currentThread().getName() + "���룬��ǰ���У�" + (permits - semaphore.availablePermits()) + "����");
                    } finally {
                        lock.unlock();
                    }
                    
                    try {
                        Thread.sleep((long)Math.random() * 10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                    lock.lock();
                    try {
                        System.out.println("�̣߳�" + Thread.currentThread().getName() + "�뿪����ǰʣ�ࣺ" + (permits - 1 - semaphore.availablePermits()) + "����");
                        //�ͷſ�λ����֪ͨ�����ȴ���
                        semaphore.release();
                    } finally {
                        lock.unlock();
                    }
                    
                }
            };
            
            service.execute(runnable);
        }
        
    }
}
