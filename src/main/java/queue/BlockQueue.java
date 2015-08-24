package queue;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 阻塞式队列
 * Condition可实现多路等待通信
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class BlockQueue {
    private Lock lock = new ReentrantLock();
    
    private Condition putCondition = lock.newCondition();
    
    private Condition takeCondition = lock.newCondition();
    
    private Object[] data = new Object[100];
    
    private int putIndex = 0;
    
    private int takeIndex = 0;
    
    private int count = 0;
    
    /**
     * 
     * @param obj
     * @throws InterruptedException
     */
    public void put(Object obj) throws InterruptedException {
        lock.lock();
        try {
            while (count == data.length) {
                putCondition.await();
            }
            
            data[putIndex] = obj;
            ++count;
            
            if (++putIndex == data.length) {
                putIndex = 0;
            }
            
            takeCondition.signal();
        } finally {
            lock.unlock();
        }
    }
    
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                takeCondition.await();
            }
            
            Object obj = data[takeIndex];
            --count;
            
            if (++takeIndex == data.length) {
                takeIndex = 0;
            }
            
            putCondition.signal();
            
            return obj;
        } finally {
            lock.unlock();
        }
    }
}
