package queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * <pre>
 * 可阻塞式队列
 * </pre>
 * 
 * @author Administrator
 * 
 */
public class ThreadArrayBlockingQueue {
    public static void main(String[] args) {
        //        ThreadArrayBlockingQueue test = new ThreadArrayBlockingQueue();
        //        test.testArrayBlockingQueue();
        
        //        test.testMyData();
        
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(1);
        try {
            queue.put(1);
            
            //阻塞式
            //            queue.put(1);
            
            //抛异常式
            //            queue.add(1);
            
            //返回是否添加成功
            boolean offer = queue.offer(1);
            System.out.println(offer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("程序执行完了");
    }
    
    /**
     * 同步通知功能
     */
    public void testMyData() {
        final MyData data = new MyData();
        
        for (int i = 0; i < 10; i++) {
            Runnable runnable1 = new Runnable() {
                public void run() {
                    data.mainMethod();
                }
            };
            
            Thread thread1 = new Thread(runnable1);
            thread1.start();
            
            Runnable runnable2 = new Runnable() {
                public void run() {
                    data.subMethod();
                }
            };
            
            Thread thread2 = new Thread(runnable2);
            thread2.start();
        }
    }
    
    /**
     * 两个方法之间的阻塞式通信
     * 
     * @author Administrator
     * 
     */
    class MyData {
        BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
        
        BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
        
        {
            //匿名方法在构造方法之前调用
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public void subMethod() {
            try {
                queue1.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("subMethod doing ");
            
            try {
                queue2.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        public void mainMethod() {
            try {
                queue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            System.out.println("mainMethod doing ");
            
            try {
                queue1.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 阻塞式队列
     */
    public void testArrayBlockingQueue() {
        int threadSize = 3;
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(threadSize);
        
        for (int i = 0; i < 2; i++) {
            Runnable runnable = new Runnable() {
                
                public void run() {
                    try {
                        while (true) {
                            Thread.sleep((long)Math.random() * 1000);
                            System.out.println(Thread.currentThread().getName() + "准备放数据");
                            
                            queue.put(1);
                            
                            System.out.println(Thread.currentThread().getName() + "已放数据，队列目前有：" + queue.size() + "个数据");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        
        Runnable runnable2 = new Runnable() {
            
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName() + "准备   取   数据");
                        
                        queue.take();
                        
                        System.out.println(Thread.currentThread().getName() + "已取数据，队列目前有：" + queue.size() + "个数据");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
        };
        Thread thread2 = new Thread(runnable2);
        thread2.start();
    }
}
