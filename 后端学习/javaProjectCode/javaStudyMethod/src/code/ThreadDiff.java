package code;

public class ThreadDiff {
    public static void main (String[] arg) throws InterruptedException{
        System.out.println("main start...");
        Thread t = new Thread() {
            public void run() {
                System.out.println("thread start...");
                System.out.println("thread end...");
            }
        };
        t.start();
        t.join();
        System.out.println("main end...");
    }
}
