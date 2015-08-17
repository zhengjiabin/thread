package thread;

/**
 * 创建一个thread线程对象，代码功能运行在runnable对象里面
 * 
 * @author Administrator
 * 
 */
public class TestThread {
    public static void main(String[] args) {
        /*//创建线程，覆盖其中的run方法
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                System.out.println(this.getName());
            };
        };
        thread1.start();
        
        //创建线程，通过线程的run方法，去调用代码功能runnable对象
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread2.start();*/
        
        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                System.out.println("thread1");
            }
        }) {
            public void run() {
                System.out.println("thread0");
            };
        };
        thread3.start();
    }
}
