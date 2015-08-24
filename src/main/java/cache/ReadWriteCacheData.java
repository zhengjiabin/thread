package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 * 读写锁：多个读锁不互斥，读锁与写锁互斥，写锁与写锁互斥--jvm控制方式
 * 缓存多线程数据共享，相对于synchronized，此方法允许多线程读数据
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ReadWriteCacheData {
    private Map<String, Object> cacheData = new HashMap<String, Object>();
    
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    
    public Object getData(String key) {
        lock.readLock().lock();
        
        Object value = cacheData.get(key);
        if (value == null) {
            lock.readLock().unlock();
            setData(key);
            lock.readLock().lock();
        }
        
        try {
            return cacheData.get(key);
        } finally {
            lock.readLock().unlock();
        }
    }
    
    private void setData(String key) {
        lock.writeLock().lock();
        
        Object value = cacheData.get(key);
        if (value != null) {
            return;
        }
        
        try {
            value = "query data(查询数据)";
            cacheData.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
