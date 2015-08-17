package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��ʱ��
 * 
 * @author Administrator
 * 
 */
public class TestTimer {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        //10s��ʼ��ӡ��֮��ÿ3���ӡһ��
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                System.out.println("testTimer");
            }
        }, 10000,3000);*/
        
        
        class MyTimerTask extends TimerTask {
            @Override
            public void run() {
                System.out.println("myTimerTask");
                Timer timer = new Timer();
                timer.schedule(new MyTimerTask(), 2000);
            }
        }
        
        Timer timer = new Timer();
        timer.schedule(new MyTimerTask(), 2000);
        
        while (true) {
            System.out.println(new Date().getSeconds());
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
