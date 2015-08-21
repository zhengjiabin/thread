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
 * ִ��ָ���̣߳������ض�Ӧ�Ľ��
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
     * ����ִ��Callable���ĸ���������ɣ����Ȼ�ȡ���Ӧ����Ϣ
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
        
        System.out.println("�ȴ����");
        
        String result;
        try {
            //            result = future.get();
            
            //���ȴ�1s,������ʱ��Ͳ��ٵȴ��ˣ��׳��쳣
            result = future.get(1, TimeUnit.SECONDS);
            System.out.println("����ǣ�" + result);
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
