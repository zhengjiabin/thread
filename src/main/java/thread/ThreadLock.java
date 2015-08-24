package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * �߳���Lock��ֻ����ɻ��⣬�������ͨ��
 * 
 * ��д����������������⣬������д�����⣬д����д������--jvm���Ʒ�ʽ
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ThreadLock {
    public static void main(String[] args) {
        ThreadLock lock = new ThreadLock();
        lock.testLock();
    }
    
    public void testLock() {
        final MyData data = new MyData();
        
        Runnable runnable1 = new Runnable() {
            public void run() {
                data.work("abcdefghijklmn");
            }
        };
        
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        
        
        Runnable runnable2 = new Runnable() {
            public void run() {
                data.work("1234567890");
            }
        };
        
        Thread thread2 = new Thread(runnable2);
        thread2.start();
    }
    
    class MyData {
        private Lock lock = new ReentrantLock();
        
        public void work(String data) {
            lock.lock();
            
            try {
                for (int i = 0; i < data.length(); i++) {
                    Thread.sleep(100);
                    System.out.print(data.charAt(i));
                }
                
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
