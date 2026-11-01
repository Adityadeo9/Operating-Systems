
import java.util.Arrays;
import java.util.Scanner;

public class PageReplacement {

    static void fifo(int[] pages, int capacity) {
        int[] frames = new int[capacity];
        Arrays.fill(frames, -1);

        int pointer = 0, faults = 0, hits = 0;

        for (int page : pages) {
            boolean found = false;

            for (int frame : frames) {
                if (frame == page) {
                    found = true;
                    break;
                }
            }

            if (found) {
                hits++;
            } else {
                frames[pointer] = page;
                pointer = (pointer + 1) % capacity;
                faults++;
            }

            System.out.println("FIFO: " + page + " "
                    + Arrays.toString(frames)
                    + (found ? " Hit" : " Fault"));
        }

        System.out.println("FIFO Page Faults: " + faults);
        System.out.println("FIFO Page Hits: " + hits);
    }

    static void lru(int[] pages, int capacity) {
        int[] frames = new int[capacity];
        int[] lastUsed = new int[capacity];
        Arrays.fill(frames, -1);

        int faults = 0, hits = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            int position = -1;

            for (int j = 0; j < capacity; j++) {
                if (frames[j] == page) {
                    position = j;
                    break;
                }
            }

            boolean found = position != -1;

            if (found) {
                hits++;
                lastUsed[position] = i;
            } else {
                int replace = -1;

                for (int j = 0; j < capacity; j++) {
                    if (frames[j] == -1) {
                        replace = j;
                        break;
                    }
                }

                if (replace == -1) {
                    replace = 0;
                    for (int j = 1; j < capacity; j++) {
                        if (lastUsed[j] < lastUsed[replace]) {
                            replace = j;
                        }
                    }
                }

                frames[replace] = page;
                lastUsed[replace] = i;
                faults++;
            }

            System.out.println("LRU: " + page + " "
                    + Arrays.toString(frames)
                    + (found ? " Hit" : " Fault"));
        }

        System.out.println("LRU Page Faults: " + faults);
        System.out.println("LRU Page Hits: " + hits);
    }

    static void optimal(int[] pages, int capacity) {
        int[] frames = new int[capacity];
        Arrays.fill(frames, -1);

        int faults = 0, hits = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            boolean found = false;

            for (int frame : frames) {
                if (frame == page) {
                    found = true;
                    break;
                }
            }

            if (found) {
                hits++;
            } else {
                int replace = -1;

                for (int j = 0; j < capacity; j++) {
                    if (frames[j] == -1) {
                        replace = j;
                        break;
                    }
                }

                if (replace == -1) {
                    int farthest = -1;

                    for (int j = 0; j < capacity; j++) {
                        int next = Integer.MAX_VALUE;

                        for (int k = i + 1; k < pages.length; k++) {
                            if (pages[k] == frames[j]) {
                                next = k;
                                break;
                            }
                        }

                        if (next > farthest) {
                            farthest = next;
                            replace = j;
                        }
                    }
                }

                frames[replace] = page;
                faults++;
            }

            System.out.println("Optimal: " + page + " "
                    + Arrays.toString(frames)
                    + (found ? " Hit" : " Fault"));
        }

        System.out.println("Optimal Page Faults: " + faults);
        System.out.println("Optimal Page Hits: " + hits);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Number of pages must be positive.");
            sc.close();
            return;
        }

        int[] pages = new int[n];

        System.out.println("Enter page reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        System.out.print("Enter number of frames: ");
        int capacity = sc.nextInt();

        if (capacity <= 0) {
            System.out.println("Number of frames must be positive.");
            sc.close();
            return;
        }

        System.out.println("\n--- FIFO ---");
        fifo(pages, capacity);

        System.out.println("\n--- LRU ---");
        lru(pages, capacity);

        System.out.println("\n--- OPTIMAL ---");
        optimal(pages, capacity);

        sc.close();
    }
}
