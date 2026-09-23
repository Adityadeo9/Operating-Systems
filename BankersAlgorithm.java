
import java.util.*;

public class BankersAlgorithm {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Input number of processes and resources
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of resources: ");
        int m = sc.nextInt();

        int[][] allocation = new int[n][m];
        int[][] max = new int[n][m];
        int[][] need = new int[n][m];
        int[] available = new int[m];

        // Input Allocation Matrix
        System.out.println("\nEnter Allocation Matrix:");

        for (int i = 0; i < n; i++) {
            System.out.println("Process P" + i + ":");

            for (int j = 0; j < m; j++) {
                allocation[i][j] = sc.nextInt();
            }
        }

        // Input Max Matrix
        System.out.println("\nEnter Max Matrix:");

        for (int i = 0; i < n; i++) {
            System.out.println("Process P" + i + ":");

            for (int j = 0; j < m; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        // Input Available Resources
        System.out.println("\nEnter Available Resources:");

        for (int j = 0; j < m; j++) {
            available[j] = sc.nextInt();
        }

        // Calculate Need Matrix
        for (int i = 0; i < n; i++) {

            for (int j = 0; j < m; j++) {

                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        // Display Need Matrix
        System.out.println("\nNeed Matrix:");

        for (int i = 0; i < n; i++) {

            System.out.print("P" + i + " : ");

            for (int j = 0; j < m; j++) {

                System.out.print(need[i][j] + " ");
            }

            System.out.println();
        }

        // Work = Available
        int[] work = new int[m];

        for (int j = 0; j < m; j++) {
            work[j] = available[j];
        }

        boolean[] finish = new boolean[n];

        int[] safeSequence = new int[n];

        int count = 0;

        // Banker Algorithm
        while (count < n) {

            boolean found = false;

            for (int i = 0; i < n; i++) {

                if (!finish[i]) {

                    boolean canExecute = true;

                    // Check Need <= Work
                    for (int j = 0; j < m; j++) {

                        if (need[i][j] > work[j]) {

                            canExecute = false;
                            break;
                        }
                    }

                    // If process can execute
                    if (canExecute) {

                        // Release allocated resources
                        for (int j = 0; j < m; j++) {

                            work[j] += allocation[i][j];
                        }

                        safeSequence[count] = i;

                        finish[i] = true;

                        count++;

                        found = true;
                    }
                }
            }

            // No process can execute
            if (!found) {
                break;
            }
        }

        // Check Safe State
        if (count == n) {

            System.out.println("\nSystem is in SAFE STATE.");

            System.out.print("Safe Sequence: ");

            for (int i = 0; i < n; i++) {

                System.out.print("P" + safeSequence[i]);

                if (i != n - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println();

        } else {

            System.out.println("\nSystem is in UNSAFE STATE.");
            System.out.println("No safe sequence exists.");
        }

        sc.close();
    }
}