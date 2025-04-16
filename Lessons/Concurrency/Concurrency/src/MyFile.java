import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.nio.file.Files.write;

public class MyFile implements Runnable {
    private static Formatter myFile;
    private static boolean done = false;
    private String message;

    public MyFile() {
        this.message = message;
    }

    public static void main(String[] args) {
        //GUI
        JFrame frame = new JFrame("Stop Logger");
        frame.setSize(400, 400);
        JButton stopButton = new JButton("Stop Logging");
        frame.add(stopButton);
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // When "Stop" button is clicked, set the stop flag to true
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done = true;
            }
        });

        MyFile message = new MyFile();
        try {
            myFile = new Formatter("output.txt");

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        //instead of creating each thread use Executors to
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(message);
        exec.execute(message);
        //no new tasks can be started but finish what you're doing
        exec.shutdown();
        try {
            //wait UPTO 1 minute for everything to finish running
            exec.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myFile.close();
        System.out.println("Done");
        System.exit(0);
    }

    //this is what runs.. loop that runs for a certain length of time
    public void run() {
        int i = 0;
        //code to sleep
        while (!done) {
            try {
//
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            write("Thread", +i++, "Hello");
        }
    }
//private static string Lock;
    //can put syncronized after void
    private void write(String name, int count, String msg) {
        System.out.print("outside ");
        System.out.print("the ");
        System.out.print("block \n");
        //synchronized(Lock){}
        synchronized (myFile) {
            myFile.format("%s::", name);
            myFile.format("message #%d::", count);
            myFile.format("%s%n", msg);
        }
        System.out.print("outside ");
        System.out.print("the ");
        System.out.print("block \n");
    }
}
