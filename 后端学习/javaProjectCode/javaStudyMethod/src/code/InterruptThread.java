package code;

public class InterruptThread {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        System.out.println("t start");
        Thread.sleep(1000);
        System.out.println("t 2");
        System.out.println("t 3");
        t.interrupt(); // 中断t线程
        t.join(); // 等待t线程结束
        System.out.println("end");
    }
}

class MyThread extends Thread {
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        System.out.println("hello start");
        try {
            System.out.println("hello 2");
            hello.join(); // 等待hello线程结束
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
        hello.interrupt();
    }
}

class HelloThread extends Thread {
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(" hello end!");
               break;
            }
        }
    }
}

