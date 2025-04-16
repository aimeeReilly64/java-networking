import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Pair<String, Integer> pair1 = new Pair<>("first", 1);

        System.out.println(pair1.getFirst());
        Pair<Integer, Integer> pair2 = new Pair<>(999, 2);
        JFrame frame = new JFrame();


        System.out.println(pair2.getSecond());

        System.out.println(pair1.getSecond() + pair2.getFirst());

    }
}

