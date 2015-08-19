package thread;

import org.junit.Test;

/**
 * <pre>
 * 线程数据共享
 * 1、Runnable 共享
 * 2、将同一数据放入Runnable共享
 * 3、synchronized 共享
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class TestThreadShare {
    /** 共享方式一 start */
    /**
     * Runnable 共享数据
     */
    @Test
    public void share1() {
        MyRunnable runnable = new MyRunnable();
        for (int i = 0; i < 2; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
        }
        
        System.out.println();
    }
    
    class MyRunnable implements Runnable {
        private int i = 0;
        
        public void run() {
            i++;
            System.out.println(i);
        }
    }
    
    /** 共享方式一 end */
    
    /** 共享方式二 start */
    @Test
    public void share2() {
        MyData data = new MyData();
        
        MyRunnable1 runnable1 = new MyRunnable1(data);
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        
        MyRunnable2 runnable2 = new MyRunnable2(data);
        Thread thread2 = new Thread(runnable2);
        thread2.start();
        
        System.out.println();
    }
    
    class MyRunnable2 implements Runnable {
        private MyData data;
        
        public MyRunnable2(MyData data) {
            this.data = data;
        }
        
        public void run() {
            data.decrease();
            System.out.println(data.i);
        }
    }
    
    class MyRunnable1 implements Runnable {
        private MyData data;
        
        public MyRunnable1(MyData data) {
            this.data = data;
        }
        
        public void run() {
            data.increase();
            System.out.println(data.i);
        }
    }
    
    class MyData {
        private int i = 0;
        
        public void increase() {
            i++;
        }
        
        public void decrease() {
            i--;
        }
    }
    
    /** 共享方式二 end */
    
    /** 共享方式三 start */
    @Test
    public void share3() {
        final MyData2 data = new MyData2();
        
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                data.increase();
            };
        };
        thread1.start();
        
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                data.decrease();
            };
        };
        thread2.start();
        
        System.out.println();
    }
    
    class MyData2 {
        private int i = 0;
        
        public synchronized void increase() {
            i++;
            System.out.println(i);
        }
        
        public synchronized void decrease() {
            i--;
            System.out.println(i);
        }
    }
}
