class SharedData {
    int data = 0;

    // Read operation
    void read(String name) {
        System.out.println(name + " started reading.");
        System.out.println(name + " reads data = " + data);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println(name + " finished reading.");
    }

    // Write operation
    void write(String name, int value) {
        System.out.println(name + " started writing.");

        data = value;

        System.out.println(name + " writes data = " + data);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println(name + " finished writing.");
    }
}


// Reader implements Runnable
class Reader implements Runnable {
    SharedData data;
    String name;

    Reader(SharedData data, String name) {
        this.data = data;
        this.name = name;
    }

    public void run() {
        data.read(name);
    }
}


// Writer implements Runnable
class Writer implements Runnable {
    SharedData data;
    String name;
    int value;

    Writer(SharedData data, String name, int value) {
        this.data = data;
        this.name = name;
        this.value = value;
    }

    public void run() {
        data.write(name, value);
    }
}


// Main class
public class ReaderWriterWithoutSync {

    public static void main(String[] args) {

        SharedData data = new SharedData();

        // 3 Readers
        Thread r1 = new Thread(new Reader(data, "Reader 1"));
        Thread r2 = new Thread(new Reader(data, "Reader 2"));
        Thread r3 = new Thread(new Reader(data, "Reader 3"));

        // 3 Writers
        Thread w1 = new Thread(new Writer(data, "Writer 1", 10));
        Thread w2 = new Thread(new Writer(data, "Writer 2", 20));
        Thread w3 = new Thread(new Writer(data, "Writer 3", 30));

        // Start Readers
        r1.start();
        r2.start();
        r3.start();

        // Start Writers
        w1.start();
        w2.start();
        w3.start();
    }
}