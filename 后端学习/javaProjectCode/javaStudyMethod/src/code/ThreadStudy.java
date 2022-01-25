package code;

public class ThreadStudy {
    public static void main(String[] args) {
        Thread t = new MyThread(); // 创建一个线程
        t.start(); // 启动新线程
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("start new thread");
    }
}
