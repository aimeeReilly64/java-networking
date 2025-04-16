import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jim Ronholm
 * @version Fall 2024
 */
public class Telegraph2024 {
    private static ObjectInputStream in;
    //private static ObjectOutputStream out;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(1);
        try {
            System.out.println("Server running - waiting for client");
            ServerSocket server = new ServerSocket(8080);
            Socket cxn = server.accept();
            System.out.println("Client connected");
            // get in and out streams
            ObjectOutputStream out = new ObjectOutputStream(cxn.getOutputStream());
            out.writeUTF("Hi client - from server");
            out.flush();
            in = new ObjectInputStream(cxn.getInputStream());
            // Thread to handle client messages
            exec.execute(() -> {
                try {
                    String clientMessage;
                    while ((clientMessage = in.readUTF()) != null) {
                        System.out.println("From client: " + clientMessage);
                        // Main client interaction loop
                        String serverInput;
                        serverInput = JOptionPane.showInputDialog("SERVER: ('bye' to exit):");
                        if (serverInput.equals("bye")) {
                            try {
                                out.writeUTF("bye");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                out.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        }
                        out.writeUTF(serverInput);
                        out.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
