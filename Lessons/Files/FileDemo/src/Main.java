import java.util.Formatter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import java.io.IOException;
import java.util.Formatter;

public class Main {

    public static void main(String[] args) {

        try (Formatter out = new Formatter("MitchsOutput.txt")) {
            out.format("%s%n", "Mitch was here");
            out.format("%d %f%n", 42, 105.5);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}