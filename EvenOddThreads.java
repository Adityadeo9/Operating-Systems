class Even implements Runnable {

    public void run() {
        for (int i = 0; i <= 20; i = i + 2) {
            System.out.println(
                Thread.currentThread().getName() + " : " + i
            );
        }
    }
}

class Odd implements Runnable {

    public void run() {
        for (int i = 1; i <= 20; i = i + 2) {
            System.out.println(
                Thread.currentThread().getName() + " : " + i
            );
        }
    }
}

public class EvenOddThreads {

    public static void main(String[] args) {

        Even e1 = new Even();
        Odd o1 = new Odd();

        Thread t1 = new Thread(e1);
        Thread t2 = new Thread(o1);

        t1.setName("Even-1");
        t2.setName("Odd-1");

        t1.setPriority(9);
        t2.setPriority(3);

        t1.start();
        t2.start();
    }
}