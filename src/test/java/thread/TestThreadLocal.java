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
}
