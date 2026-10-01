class AscDec implements Runnable {

    String val;

    public synchronized void run() {

        val = Thread.currentThread().getName();

        if (val.equals("ASC")) {
            asc();
        } else {
            des();
        }
    }

    synchronized void asc() {

        System.out.println("Ascending:");

        for (int i = 0; i <= 10; i++) {
            System.out.println(i);
        }
    }

    synchronized void des() {

        System.out.println("Descending:");

        for (int i = 10; i >= 0; i--) {
            System.out.println(i);
        }
    }
}

public class AscendingDescendingThreads {

    public static void main(String[] args) {

        AscDec obj = new AscDec();

        Thread t1 = new Thread(obj);
        Thread t2 = new Thread(obj);

        t1.setName("ASC");
        t2.setName("DES");

        t1.start();
        t2.start();
    }
}