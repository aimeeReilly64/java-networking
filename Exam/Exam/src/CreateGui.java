import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateGui extends JFrame {
    private JButton fetch = new JButton("Fetch");
    private JButton quitButton = new JButton("Quit");
    private JComboBox authorList = new JComboBox();
    public JTextArea textArea = new JTextArea();
    AuthorModel model;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;

    public CreateGui() {
        super("Author Books");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        JPanel middlePanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        topPanel.add(authorList);
        topPanel.add(fetch);

        add(textArea).setEnabled(true);
        textArea.setEditable(true);
        textArea.setLineWrap(true);

        add(topPanel, BorderLayout.NORTH);
        add(middlePanel, BorderLayout.CENTER);

        add(quitButton, BorderLayout.SOUTH);

        quitButton.addActionListener(e -> {
            System.exit(0);

        });
        final String DATABASE_URL = "jdbc:mariadb://localhost:3306/books";
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, "root", "")) {
            Statement statement = connection.createStatement();
            ResultSet author = statement.executeQuery("SELECT * FROM authors");
            author.first();
            for (int i = 0; i < author.getRow(); i++) {
                authorList.addItem(author.getString("firstName"));
                System.out.println(author.getString("firstName"));
                author.next();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        fetch.addActionListener(e -> sendMove("Fetch"));
    }

    public void sendMove(String move) {
        /**
         * The constructor connects to the Authors table and queries for the data
         */
        final String DATABASE_URL = "jdbc:mariadb://localhost:3306/books";
        final String SELECT_QUERY
                = "SELECT titles.isbn, title, editionNumber, copyright\n" +
                " FROM titles, authorisbn, authors\n" +
                " WHERE titles.isbn = authorisbn.isbn\n" +
                " AND authors.authorID = authorisbn.authorID\n";
        try (Connection connection = DriverManager.getConnection(
                DATABASE_URL, "root", "")) {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(SELECT_QUERY);
            resultSet.first();
            System.out.println(resultSet.getString("ISBN"));
            System.out.println(resultSet.getString("title"));
            System.out.println(resultSet.getString("editionNumber"));
            System.out.println(resultSet.getString("copyright"));

            textArea.setText(resultSet.getString("ISBN"));

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();

        }
    }
}





