import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import static java.awt.SystemColor.text;

/**
 * A small application to look up info concerning a book. The shell is built to
 * demonstrate that by passing values around as arguments we don't need (as many)
 * class level variables.
 *
 * @author Jim Ronholm
 */
public class Main {

    /**
     * Main method that executes the project
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

        Main main = new Main();

        JFrame app = new JFrame("Look up books");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setSize(400, 400);
        buildGUI(app);
        app.setVisible(true);
    }

    /**
     * Build some gui items and add them to the given JFrame
     *
     * @param frame where the gui items are to be placed
     */
    private static void buildGUI(JFrame frame) throws SQLException {
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Enter book title search term:"));
        JTextField searchField = new JTextField(20);
        JTextArea outputArea = new JTextArea(20, 20);

        addSearchAction(searchField, outputArea);

        inputPanel.add(searchField);
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(outputArea, BorderLayout.CENTER);
    }

    /**
     * Adds a custom ActionListener to the JTextField that puts results in the
     * JTextArea
     *
     * @param searchField the control that causes the action, and contains text
     *                    to be used in the action
     * @param outputArea  where the results of the action will be placed
     */
    private static void addSearchAction(JTextField searchField, JTextArea outputArea) throws SQLException {

        /* for the in-class exercise you need to modify the following so that it
    takes the text from the JTextField, queries the database, and puts the
        query results into the JTextArea
*/
        searchField.addActionListener(new ActionListener() {
                                          public void actionPerformed(ActionEvent e) {
                                              String searchTerm = searchField.getText();
                                              try {
                                                  Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/books?user=root&password=");
                                                  Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                                              ResultSet titles = stmt.executeQuery("SELECT titles.title, titles.isbn, authorisbn.authorID, authors.firstname, authors.lastname FROM `titles` INNER JOIN authorisbn ON authorisbn.isbn = titles.isbn INNER JOIN authors ON authorisbn.authorid = authors.authorid WHERE `title` LIKE '%" + searchTerm + "%' ORDER BY titles.title, authors.lastname, authors.firstname"   );
                                                  //next would bring you to the next row
                                                  //absolute will also bring you to the row you want
                                                  //then you need a get method to retrieve the data(getString or getObject)
                                                  //while (rs.next()) {
                                                  //  System.out.printf("Author ID:%d\tFirst Name:%s\t Last Name:%s\t%n", rs.getInt(1), rs.getString(2), rs.getString("lastName"));
                                                  //System.out.printf("%d: %s%n", rs.getInt(1), rs.getString(2));
                                                  boolean found = false; // Track if any match is found
                                                  outputArea.setText(""); // Clear the output area before starting the search

                                                  while (titles.next()) {
                                                      String title = titles.getString(1);
                                                      String author = titles.getString(4) + " " + titles.getString(5);
                                                      outputArea.append("Title: " + title + " - " + author + "\n");
                                                      found = true; // A match is found
                                                  }
                                                  if (!found) {
                                                      outputArea.append("No match\n"); // Only add this once, after the loop
                                                  }
                                              } catch (SQLException ex) {
                                                  throw new RuntimeException(ex);
                                              }
                                          }

                                      }
        );
    }
}


