import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jim Ronholm
 * @version Fall 2024
 */
public class Client2024 {
    /**
     * @param args the command line arguments
     */
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {
        ExecutorService exec = null;
        try {
            System.out.println("Client running - trying to connect");
            Socket cxn = new Socket("localhost", 8080); // Connect to server

            // Initialize input/output streams
            out = new ObjectOutputStream(cxn.getOutputStream());
            out.flush();
            in = new ObjectInputStream(cxn.getInputStream());
            System.out.println("Connected to Server");

            // Create thread pool
            exec = Executors.newFixedThreadPool(1);

            // Thread to handle server messages
            exec.execute(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readUTF()) != null) {
                        System.out.println("From server: " + serverMessage);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            // Main client interaction loop
            String clientInput;
            while (true) {
                clientInput = JOptionPane.showInputDialog("CLIENT ('bye' to exit):");
                if (clientInput.equals("bye")) {
                    out.writeUTF("bye");
                    out.flush();
                    break;
                }
                out.writeUTF(clientInput);
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


