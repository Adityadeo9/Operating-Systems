import java.io.*;
import java.util.Scanner;

public class IPCProgram {

public static void main(String[] args) throws Exception {

        // =====================================================
        // CHILD PROCESS
        // =====================================================
        if (args.length > 0 && args[0].equals("child")) {

            // Read message sent by Parent
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String message = reader.readLine();

            // Process received message
        System.out.println("Child received: " + message);
        System.out.println("Child processed the message successfully.");

        return;
        }

        // =====================================================
        // PARENT PROCESS
        // =====================================================

        System.out.println("Parent process started.");

        // Take message from user
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter message for Child: ");
        String message = sc.nextLine();

        // =====================================================
        // CREATE CHILD PROCESS
        // =====================================================

        String classPath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp",
                classPath,
                "IPCProgram",
                "child"
        );

        // Combine error stream with normal output
        pb.redirectErrorStream(true);

        // Start Child process
        Process child = pb.start();

        // =====================================================
        // PARENT → CHILD COMMUNICATION
        // =====================================================

        BufferedWriter writer =
                new BufferedWriter(
                        new OutputStreamWriter(
                                child.getOutputStream()));

        // Send user's message to Child
        writer.write(message);
        writer.newLine();
        writer.flush();

        // Close writing stream
        writer.close();

        // =====================================================
        // CHILD → PARENT COMMUNICATION
        // =====================================================

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                child.getInputStream()));

        String response;

        while ((response = reader.readLine()) != null) {
        System.out.println("From Child: " + response);
        }

        // =====================================================
        // WAIT FOR CHILD
        // =====================================================

        int exitCode = child.waitFor();

        System.out.println("Child process completed.");
        System.out.println("Exit code: " + exitCode);

        sc.close();
}
}