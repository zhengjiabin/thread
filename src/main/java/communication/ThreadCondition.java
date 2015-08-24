package communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 线程通信Condition，作用：类似wait和nofity
 * Condition可实现多路等待通信
 * 一旦wait，则会释放锁
 * </pre>
 * 
 * 
 * @author Administrator
 * 
 */
public class ThreadCondition {
    public static void main(String[] args) {
        ThreadCondition test = new ThreadCondition();
        test.testCondition();
    }
    
    public void testCondition() {
        final MyData data = new MyData();
        for (int i = 0; i < 5; i++) {
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    data.mainMethod();
                }
            });
            thread1.start();
            
            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    data.subMethod();
                }
            });
            thread2.start();
        }
    }
    
    class MyData {
        private boolean flag = true;
        
        private Lock lock = new ReentrantLock();
        
        private Condition condition = lock.newCondition();
        
        public synchronized void synMethod() {
            try {
                System.out.println(Thread.currentThread().getName() + " synMethod wait!");
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public void subMethod() {
            lock.lock();
            try {
                if (!flag) {
                    System.out.println(Thread.currentThread().getName() + " subMethod wait!");
                    condition.await();
                    flag = false;
                } else {
                    System.out.println(Thread.currentThread().getName() + " subMethod notify!");
                    condition.signal();
                    flag = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        
        public void mainMethod() {
            lock.lock();
            try {
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " mainMethod wait!");
                    condition.await();
                    flag = true;
                } else {
                    System.out.println(Thread.currentThread().getName() + " mainMethod notify!");
                    condition.signal();
                    flag = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
