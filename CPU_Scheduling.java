import java.util.*;

public class CPU_Scheduling {

    // ================= MAIN =================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        String[] process = new String[n];
        int[] at = new int[n];
        int[] bt = new int[n];
        int[] pr = new int[n];

        // Input
        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter details for Process " + (i + 1));

            System.out.print("Process Name: ");
            process[i] = sc.next();

            System.out.print("Arrival Time: ");
            at[i] = sc.nextInt();

            System.out.print("Burst Time: ");
            bt[i] = sc.nextInt();

            System.out.print("Priority: ");
            pr[i] = sc.nextInt();
        }

        // Menu
        System.out.println("\n1. FCFS");
        System.out.println("2. SJF");
        System.out.println("3. Round Robin");
        System.out.println("4. Priority");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        int[] ct = new int[n];

        // ================= SWITCH CASE =================
        switch (choice) {

            case 1:
                fcfs(at, bt, ct);
                break;

            case 2:
                sjf(at, bt, ct);
                break;

            case 3:
                System.out.print("Enter Time Quantum: ");
                int tq = sc.nextInt();

                roundRobin(at, bt, ct, tq);
                break;

            case 4:
                priority(at, bt, pr, ct);
                break;

            default:
                System.out.println("Invalid Choice");
                sc.close();
                return;
        }

        // ================= TAT & WT =================

        double totalWT = 0;
        double totalTAT = 0;

        System.out.println("\n------------------------------------------------");
        System.out.println("Process\tAT\tBT\tPR\tCT\tTAT\tWT");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < n; i++) {

            int tat = ct[i] - at[i];
            int wt = tat - bt[i];

            totalWT += wt;
            totalTAT += tat;

            System.out.println(
                    process[i] + "\t" +
                    at[i] + "\t" +
                    bt[i] + "\t" +
                    pr[i] + "\t" +
                    ct[i] + "\t" +
                    tat + "\t" +
                    wt
            );
        }

        System.out.println("-----------------------------------------------");

        System.out.printf(
                "Average Waiting Time : %.2f\n",
                totalWT / n
        );

        System.out.printf(
                "Average Turnaround Time : %.2f\n",
                totalTAT / n
        );

        sc.close();
    }


    // ================= FCFS FUNCTION =================
    static void fcfs(int[] at, int[] bt, int[] ct) {

        int n = at.length;

        Integer[] index = new Integer[n];

        for (int i = 0; i < n; i++) {
            index[i] = i;
        }

        // Sort according to Arrival Time
        Arrays.sort(index, (i, j) -> {
            if (at[i] != at[j])
                return Integer.compare(at[i], at[j]);

            return Integer.compare(i, j);
        });

        int time = 0;

        // Execute in arrival order
        for (int i : index) {

            if (time < at[i]) {
                time = at[i];
            }

            time += bt[i];

            ct[i] = time;
        }
    }


    // ================= SJF FUNCTION =================
    static void sjf(int[] at, int[] bt, int[] ct) {

        int n = at.length;

        boolean[] done = new boolean[n];

        int completed = 0;
        int time = 0;

        while (completed < n) {

            int index = -1;
            int shortest = Integer.MAX_VALUE;

            // Find shortest burst among arrived processes
            for (int i = 0; i < n; i++) {

                if (!done[i] && at[i] <= time) {

                    if (bt[i] < shortest) {

                        shortest = bt[i];
                        index = i;

                    } else if (bt[i] == shortest) {

                        // Tie -> earlier arrival
                        if (index == -1 || at[i] < at[index]) {
                            index = i;
                        }
                    }
                }
            }

            // CPU idle
            if (index == -1) {

                int nextArrival = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {

                    if (!done[i] && at[i] < nextArrival) {
                        nextArrival = at[i];
                    }
                }

                time = nextArrival;

            } else {

                // Execute shortest process
                time += bt[index];

                ct[index] = time;

                done[index] = true;

                completed++;
            }
        }
    }


    // ================= ROUND ROBIN FUNCTION =================
    static void roundRobin(int[] at, int[] bt, int[] ct, int tq) {

        int n = at.length;

        int[] rt = new int[n];

        for (int i = 0; i < n; i++) {
            rt[i] = bt[i];
        }

        Queue<Integer> queue = new LinkedList<>();

        boolean[] added = new boolean[n];

        int completed = 0;
        int time = 0;

        // Find first arrival
        int firstArrival = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {

            if (at[i] < firstArrival) {
                firstArrival = at[i];
            }
        }

        time = firstArrival;

        // Add arrived processes
        for (int i = 0; i < n; i++) {

            if (!added[i] && at[i] <= time) {

                queue.add(i);
                added[i] = true;
            }
        }

        while (completed < n) {

            // If queue is empty
            if (queue.isEmpty()) {

                int nextArrival = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {

                    if (!added[i] && at[i] < nextArrival) {
                        nextArrival = at[i];
                    }
                }

                time = nextArrival;

                // Add newly arrived processes
                for (int i = 0; i < n; i++) {

                    if (!added[i] && at[i] <= time) {

                        queue.add(i);
                        added[i] = true;
                    }
                }
            }

            // Get first process
            int current = queue.poll();

            // Execute for Time Quantum
            int executionTime = Math.min(tq, rt[current]);

            time += executionTime;

            rt[current] -= executionTime;

            // Add newly arrived processes
            for (int i = 0; i < n; i++) {

                if (!added[i] && at[i] <= time) {

                    queue.add(i);
                    added[i] = true;
                }
            }

            // Process completed
            if (rt[current] == 0) {

                ct[current] = time;

                completed++;

            } else {

                // Put process at end of queue
                queue.add(current);
            }
        }
    }


    // ================= PRIORITY FUNCTION =================
    static void priority(int[] at, int[] bt, int[] pr, int[] ct) {

        int n = at.length;

        boolean[] complete = new boolean[n];

        int completed = 0;
        int time = 0;

        while (completed < n) {

            int index = -1;

            // Smaller number = higher priority
            int highPriority = Integer.MAX_VALUE;

            // Find highest priority arrived process
            for (int i = 0; i < n; i++) {

                if (!complete[i] && at[i] <= time) {

                    if (pr[i] < highPriority) {

                        highPriority = pr[i];
                        index = i;

                    } else if (pr[i] == highPriority) {

                        // Tie -> earlier arrival
                        if (index == -1 || at[i] < at[index]) {
                            index = i;
                        }
                    }
                }
            }

            // CPU idle
            if (index == -1) {

                int nextArrival = Integer.MAX_VALUE;

                for (int i = 0; i < n; i++) {

                    if (!complete[i] && at[i] < nextArrival) {
                        nextArrival = at[i];
                    }
                }

                time = nextArrival;

            } else {

                // Execute selected process
                time += bt[index];

                ct[index] = time;

                complete[index] = true;

                completed++;
            }
        }
    }
}
