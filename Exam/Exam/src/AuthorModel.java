import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class AuthorModel extends AbstractTableModel {

        private ResultSet resultSet;
        private ResultSetMetaData metaData;

        /**
         * The constructor connects to the Authors table and queries for the data
         */
        public AuthorModel() {
            final String DATABASE_URL = "jdbc:mariadb://localhost:3306/books";
            final String SELECT_QUERY
                    = "SELECT titles.isbn, title, editionNumber, copyright\n" +
                    " FROM titles, authorisbn, authors\n" +
                    " WHERE titles.isbn = authorisbn.isbn\n" +
                    " AND authors.authorID = authorisbn.authorID\n";
            try ( Connection connection = DriverManager.getConnection(
                    DATABASE_URL, "root", "")) {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(SELECT_QUERY);

                System.out.println(resultSet);

            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}