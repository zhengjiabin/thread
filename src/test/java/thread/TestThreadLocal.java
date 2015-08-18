package thread;

import java.util.Random;

import org.junit.Test;

public class TestThreadLocal {
    
    ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    
    @Test
    public void threadLocal() {
        for (int i = 0; i < 5; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread() + "put data£º" + data);
                    threadLocal.set(new Integer(data));
                    
                    A a = new A();
                    a.get();
                }
            };
            
            Thread thread = new Thread(runnable);
            thread.start();
        }
        System.out.println("xx");
    }
    
    class A {
        public void get() {
            Integer integer = threadLocal.get();
            System.out.println(Thread.currentThread() + "get data£º" + integer);
        }
    }
    
    @Test
    public void testThreadScopeData() {
        for (int i = 0; i < 5; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    
                    ThreadScopeData threadInfo = ThreadScopeData.getInstance();
                    threadInfo.setName(String.valueOf(data));
                    System.out.println(Thread.currentThread() + "put data£º" + data);
                    
                    threadLocal.set(new Integer(data));
                    
                    B b = new B();
                    b.get();
                }
            };
            
            Thread thread = new Thread(runnable);
            thread.start();
        }
        
        System.out.println("xx");
    }
    
    class B {
        public void get() {
            ThreadScopeData info = ThreadScopeData.getInstance();
            System.out.println(Thread.currentThread() + "get data£º" + info.getName());
        }
    }
}

class ThreadScopeData {
    private ThreadScopeData() {
        
    }
    
    private static ThreadLocal<ThreadScopeData> threadInfo = new ThreadLocal<ThreadScopeData>();
    
    public static/*synchronized*/ThreadScopeData getInstance() {
        ThreadScopeData info = threadInfo.get();
        if (info == null) {
            info = new ThreadScopeData();
            threadInfo.set(info);
        }
        return info;
    }
    
    private String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
