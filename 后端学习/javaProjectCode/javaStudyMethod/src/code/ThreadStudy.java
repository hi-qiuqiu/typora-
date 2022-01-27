package code;

public class ThreadStudy {
    public static void main(String[] args) {
          Thread t = new Thread(new MyRunnable());
          t.start();
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("start new thread!");
    }
}
