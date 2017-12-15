import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Map;
import java.util.Set;

public class mainTest {
    public static long value=1L;

    public static void main(String[] args) {

        mainTest mainTest1 = new mainTest();
        mainTest mainTest2 = new mainTest();

        mainTest1.value++;
        System.out.println(mainTest1.value);
        System.out.println(mainTest2.value);
       /* System.out.println(Thread.currentThread().getName());
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
                        Set<Map.Entry<Thread, StackTraceElement[]>> entries = allStackTraces.entrySet();
                        for (Map.Entry<Thread, StackTraceElement[]> entry : entries) {
                            System.out.println(entry.getKey().getName());
                        }
                        System.out.println("线程数量："+Thread.activeCount());
                        System.out.println("---------------------------------------");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        thread1.setName("liudehua");
        thread1.start();
        for (int j = 0; j <3; j++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        while (true){
                            Thread.sleep(2000);
                            System.out.println(Thread.currentThread().getName());
                            Thread.currentThread().getThreadGroup().stop();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("zhangguohao-"+j);
            thread.start();
        }

        while (true){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread.activeCount():"+Thread.activeCount());
        }
*/
    }
}
