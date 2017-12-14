public class mainTest {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                public void run() {
                    System.out.println(Thread.currentThread().getThreadGroup()+"-"+Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j <5 ; j++) {
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                System.out.println(Thread.currentThread().getThreadGroup()+"-"+Thread.currentThread().getName());
                            }
                        }).start();
                    }

                }
            }).start();
        }
    }
}
