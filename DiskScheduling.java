
import java.util.Arrays;
import java.util.Scanner;

public class DiskScheduling {

    static int totalMovement(int[] order, int head) {
        int movement = 0;

        for (int track : order) {
            movement += Math.abs(head - track);
            head = track;
        }

        return movement;
    }

    static void fifo(int[] requests, int head) {
        int movement = totalMovement(requests, head);

        System.out.println("\nFIFO");
        System.out.println("Order: " + Arrays.toString(requests));
        System.out.println("Total Head Movement: " + movement);
    }

    static void sstf(int[] requests, int head) {
        boolean[] visited = new boolean[requests.length];
        int[] order = new int[requests.length];
        int current = head;

        for (int i = 0; i < requests.length; i++) {
            int nearest = -1;
            int distance = Integer.MAX_VALUE;

            for (int j = 0; j < requests.length; j++) {
                if (!visited[j]) {
                    int d = Math.abs(current - requests[j]);

                    if (d < distance) {
                        distance = d;
                        nearest = j;
                    }
                }
            }

            order[i] = requests[nearest];
            current = requests[nearest];
            visited[nearest] = true;
        }

        System.out.println("\nSSTF");
        System.out.println("Order: " + Arrays.toString(order));
        System.out.println("Total Head Movement: "
                + totalMovement(order, head));
    }

    static void scan(int[] requests, int head, int maxTrack) {
        int[] sorted = requests.clone();
        Arrays.sort(sorted);

        int movement = 0;
        int current = head;
        StringBuilder order = new StringBuilder();

        for (int track : sorted) {
            if (track >= head) {
                order.append(track).append(" ");
                movement += Math.abs(current - track);
                current = track;
            }
        }

        boolean lowerExists = false;
        for (int track : sorted) {
            if (track < head) {
                lowerExists = true;
                break;
            }
        }

        if (lowerExists) {
            movement += maxTrack - current;
            current = maxTrack;

            for (int i = sorted.length - 1; i >= 0; i--) {
                if (sorted[i] < head) {
                    order.append(sorted[i]).append(" ");
                    movement += Math.abs(current - sorted[i]);
                    current = sorted[i];
                }
            }
        }

        System.out.println("\nSCAN (Moving Up First)");
        System.out.println("Order: " + order);
        System.out.println("Total Head Movement: " + movement);
    }

    static void cscan(int[] requests, int head, int maxTrack) {
        int[] sorted = requests.clone();
        Arrays.sort(sorted);

        int movement = 0;
        int current = head;
        StringBuilder order = new StringBuilder();

        for (int track : sorted) {
            if (track >= head) {
                order.append(track).append(" ");
                movement += Math.abs(current - track);
                current = track;
            }
        }

        boolean lowerExists = false;
        for (int track : sorted) {
            if (track < head) {
                lowerExists = true;
                break;
            }
        }

        if (lowerExists) {
            movement += maxTrack - current;
            movement += maxTrack; // Return from maxTrack to 0
            current = 0;

            for (int track : sorted) {
                if (track < head) {
                    order.append(track).append(" ");
                    movement += Math.abs(current - track);
                    current = track;
                }
            }
        }

        System.out.println("\nC-SCAN (Moving Up First)");
        System.out.println("Order: " + order);
        System.out.println("Total Head Movement: " + movement);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of requests: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Number of requests must be positive.");
            sc.close();
            return;
        }

        int[] requests = new int[n];

        System.out.println("Enter disk requests:");
        for (int i = 0; i < n; i++) {
            requests[i] = sc.nextInt();
        }

        System.out.print("Enter initial head position: ");
        int head = sc.nextInt();

        System.out.print("Enter maximum track number: ");
        int maxTrack = sc.nextInt();

        if (maxTrack <= 0 || head < 0 || head > maxTrack) {
            System.out.println("Invalid head position or disk range.");
            sc.close();
            return;
        }

        for (int track : requests) {
            if (track < 0 || track > maxTrack) {
                System.out.println("Invalid disk request: " + track);
                sc.close();
                return;
            }
        }

        fifo(requests, head);
        sstf(requests, head);
        scan(requests, head, maxTrack);
        cscan(requests, head, maxTrack);

        sc.close();
    }
}
