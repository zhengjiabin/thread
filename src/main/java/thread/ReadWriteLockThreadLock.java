package thread;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 * ��д����������������⣬������д�����⣬д����д������--jvm���Ʒ�ʽ
 * 
 * ʵ����󻺴�
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ReadWriteLockThreadLock {
    public static void main(String[] args) {
        ReadWriteLockThreadLock lock = new ReadWriteLockThreadLock();
        lock.testReadWriteLock();
    }
    
    public void testReadWriteLock() {
        final MayData data = new MayData();
        
        for (int i = 0; i < 3; i++) {
            final int task = i;
            Thread thread1 = new Thread(new Runnable() {
                public void run() {
                    data.getData();
                }
            });
            thread1.start();
            
            Thread thread2 = new Thread(new Runnable() {
                public void run() {
                    data.setData("value��" + task);
                }
            });
            thread2.start();
        }
    }
    
    class MayData {
        private String data;
        
        private ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public void getData() {
            lock.readLock().lock();
            
            System.out.println(Thread.currentThread().getName() + " getData start ");
            
            try {
                Thread.sleep(100);
                System.out.println(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
            
            System.out.println(Thread.currentThread().getName() + " getData end ");
        }
        
        public void setData(String data) {
            lock.writeLock().lock();
            
            System.out.println(Thread.currentThread().getName() + " setData start ");
            
            try {
                Thread.sleep(100);
                this.data = data;
                System.out.println(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
            
            System.out.println(Thread.currentThread().getName() + " setData end ");
        }
    }
    
    class CacheData {
        private String data;
        
        /** �ж��Ƿ��Ѿ������� */
        private volatile boolean cacheValid = false;
        
        private ReadWriteLock lock = new ReentrantReadWriteLock();
        
        public void getData() {
            lock.readLock().lock();
            if (!cacheValid) {
                lock.readLock().unlock();
                setData(Thread.currentThread().getName());
                lock.readLock().lock();
            }
            
            System.out.println(Thread.currentThread().getName() + " getData start ");
            
            try {
                Thread.sleep(100);
                System.out.println(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
            
            System.out.println(Thread.currentThread().getName() + " getData end ");
        }
        
        private void setData(String data) {
            lock.writeLock().lock();
            
            if (cacheValid) {
                return;
            }
            
            System.out.println(Thread.currentThread().getName() + " setData start ");
            
            try {
                Thread.sleep(100);
                
                this.data = data;
                cacheValid = true;
                
                System.out.println(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.writeLock().unlock();
            }
            
            System.out.println(Thread.currentThread().getName() + " setData end ");
        }
    }
}
