package thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 执行指定线程，并返回对应的结果
 * 
 * @author Administrator
 * 
 */
public class CallableAndFuture {
    public static void main(String[] args) {
        CallableAndFuture test = new CallableAndFuture();
        //        test.Callable();
        
        test.Completion();
    }
    
    /**
     * 批量执行Callable，哪个任务先完成，就先获取其对应的信息
     */
    public <V> void Completion() {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletionService<Integer> service = new ExecutorCompletionService<Integer>(threadPool);
        
        for (int i = 0; i < 10; i++) {
            MyCallable2<Integer> myCallable = new MyCallable2<Integer>(i);
            service.submit(myCallable);
        }
        
        try {
            for (int i = 0; i < 10; i++) {
                Future<Integer> take = service.take();
                Integer integer = take.get();
                System.out.println(integer);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        
        threadPool.shutdown();
    }
    
    class MyCallable2<V> implements Callable<V> {
        
        private Integer integer;
        
        public MyCallable2(Integer integer) {
            this.integer = integer;
        }
        
        @SuppressWarnings("unchecked")
        public V call() throws Exception {
            Random random = new Random();
            Thread.sleep(random.nextInt(5000));
            return (V)integer;
        }
        
    }
    
    public void Callable() {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        
        MyCallable<String> myCallable = new MyCallable<String>();
        Future<String> future = threadPool.submit(myCallable);
        
        System.out.println("等待结果");
        
        String result;
        try {
            //            result = future.get();
            
            //最多等待1s,超过此时间就不再等待了，抛出异常
            result = future.get(1, TimeUnit.SECONDS);
            System.out.println("结果是：" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        
        threadPool.shutdown();
    }
    
    class MyCallable<V> implements Callable<V> {
        
        @SuppressWarnings("unchecked")
        public V call() throws Exception {
            Thread.sleep(5000);
            return (V)"true";
        }
        
    }
}
