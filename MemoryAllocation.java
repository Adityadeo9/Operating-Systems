import java.util.Scanner;

public class MemoryAllocation {

    // First Fit
    static void firstFit(int[] blocks, int[] processes) {

        int[] b = blocks.clone();

        System.out.println("\n--- First Fit ---");
        System.out.println("Process\tBlock\tRemaining");

        for (int i = 0; i < processes.length; i++) {

            boolean allocated = false;

            for (int j = 0; j < b.length; j++) {

                if (b[j] >= processes[i]) {

                    int remaining = b[j] - processes[i];

                    System.out.println(
                        processes[i] + "\t" +
                        b[j] + "\t" +
                        remaining
                    );

                    b[j] = remaining;
                    allocated = true;
                    break;
                }
            }

            if (!allocated) {
                System.out.println(
                    processes[i] + "\tNot Allocated"
                );
            }
        }
    }


    // Best Fit
    static void bestFit(int[] blocks, int[] processes) {

        int[] b = blocks.clone();

        System.out.println("\n--- Best Fit ---");
        System.out.println("Process\tBlock\tRemaining");

        for (int i = 0; i < processes.length; i++) {

            int best = -1;

            for (int j = 0; j < b.length; j++) {

                if (b[j] >= processes[i]) {

                    if (best == -1 ||
                        b[j] < b[best]) {

                        best = j;
                    }
                }
            }

            if (best != -1) {

                int remaining = b[best] - processes[i];

                System.out.println(
                    processes[i] + "\t" +
                    b[best] + "\t" +
                    remaining
                );

                b[best] = remaining;
            }
            else {
                System.out.println(
                    processes[i] + "\tNot Allocated"
                );
            }
        }
    }


    // Next Fit
    static void nextFit(int[] blocks, int[] processes) {

        int[] b = blocks.clone();
        int start = 0;

        System.out.println("\n--- Next Fit ---");
        System.out.println("Process\tBlock\tRemaining");

        for (int i = 0; i < processes.length; i++) {

            boolean allocated = false;

            for (int count = 0; count < b.length; count++) {

                int j = (start + count) % b.length;

                if (b[j] >= processes[i]) {

                    int remaining = b[j] - processes[i];

                    System.out.println(
                        processes[i] + "\t" +
                        b[j] + "\t" +
                        remaining
                    );

                    b[j] = remaining;

                    start = j;
                    allocated = true;
                    break;
                }
            }

            if (!allocated) {
                System.out.println(
                    processes[i] + "\tNot Allocated"
                );
            }
        }
    }


    // Worst Fit
    static void worstFit(int[] blocks, int[] processes) {

        int[] b = blocks.clone();

        System.out.println("\n--- Worst Fit ---");
        System.out.println("Process\tBlock\tRemaining");

        for (int i = 0; i < processes.length; i++) {

            int worst = -1;

            for (int j = 0; j < b.length; j++) {

                if (b[j] >= processes[i]) {

                    if (worst == -1 ||
                        b[j] > b[worst]) {

                        worst = j;
                    }
                }
            }

            if (worst != -1) {

                int remaining = b[worst] - processes[i];

                System.out.println(
                    processes[i] + "\t" +
                    b[worst] + "\t" +
                    remaining
                );

                b[worst] = remaining;
            }
            else {
                System.out.println(
                    processes[i] + "\tNot Allocated"
                );
            }
        }
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of memory blocks: ");
        int n = sc.nextInt();

        int[] blocks = new int[n];

        System.out.println("Enter memory block sizes:");

        for (int i = 0; i < n; i++) {
            blocks[i] = sc.nextInt();
        }

        System.out.print("Enter number of processes: ");
        int m = sc.nextInt();

        int[] processes = new int[m];

        System.out.println("Enter process sizes:");

        for (int i = 0; i < m; i++) {
            processes[i] = sc.nextInt();
        }

        firstFit(blocks, processes);
        bestFit(blocks, processes);
        nextFit(blocks, processes);
        worstFit(blocks, processes);

        sc.close();
    }
}