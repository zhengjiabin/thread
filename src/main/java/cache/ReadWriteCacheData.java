package cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 * ��д����������������⣬������д�����⣬д����д������--jvm���Ʒ�ʽ
 * ������߳����ݹ��������synchronized���˷���������̶߳�����
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
            value = "query data(��ѯ����)";
            cacheData.put(key, value);
        } finally {
            lock.writeLock().unlock();
        }
    }
}
