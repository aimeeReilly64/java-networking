import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
        /**
         * Executable method to start the program
         * @param args the command line arguments
         */
        public static void main(String[] args) {
            //note: if tables already exist, uncomment the drop statements
/**            final String SQL_CREATE_TABLES[] = { //"DROP TABLE authorISBN;",
//            "DROP TABLE titles;",
//            "DROP TABLE authors;",

                    "CREATE TABLE authors (" +
                            "   authorID INT NOT NULL AUTO_INCREMENT," +
                            "   firstName varchar (20) NOT NULL," +
                            "   lastName varchar (30) NOT NULL," +
                            "   PRIMARY KEY (authorID)" +
                            ");",

                    "CREATE TABLE titles (" +
                            "   isbn varchar (20) NOT NULL," +
                            "   title varchar (100) NOT NULL," +
                            "   editionNumber INT NOT NULL," +
                            "   copyright varchar (4) NOT NULL," +
                            "   PRIMARY KEY (isbn)" +
                            ");",

                    "CREATE TABLE authorISBN (" +
                            "   authorID INT NOT NULL," +
                            "   isbn varchar (20) NOT NULL," +
                            "   FOREIGN KEY (authorID) REFERENCES authors (authorID), " +
                            "   FOREIGN KEY (isbn) REFERENCES titles (isbn)" +
                            ");",

                    "INSERT INTO authors (firstName, lastName)" +
                            "VALUES " +
                            "   ('Paul','Deitel'), " +
                            "   ('Harvey','Deitel')," +
                            "   ('Abbey','Deitel'), " +
                            "   ('Dan','Quirk')," +
                            "   ('Michael','Morgano');",

                    "INSERT INTO titles (isbn,title,editionNumber,copyright)" +
                            "VALUES" +
                            "   ('0132151006','Internet & World Wide Web How to Program',5,'2012')," +
                            "   ('0133807800','Java How to Program',10,'2015')," +
                            "   ('0132575655','Java How to Program, Late Objects Version',10,'2015')," +
                            "   ('013299044X','C How to Program',7,'2013'), " +
                            "   ('0132990601','Simply Visual Basic 2010',4,'2013')," +
                            "   ('0133406954','Visual Basic 2012 How to Program',6,'2014')," +
                            "   ('0133379337','Visual C# 2012 How to Program',5,'2014')," +
                            "   ('0136151574','Visual C++ How to Program',2,'2008')," +
                            "   ('0133378713','C++ How to Program',9,'2014')," +
                            "   ('0133764036','Android How to Program',2,'2015')," +
                            "   ('0133570924','Android for Programmers: An App-Driven Approach, Volume 1',2,'2014')," +
                            "   ('0132121360','Android for Programmers: An App-Driven Approach',1,'2012');",

                    "INSERT INTO authorISBN (authorID,isbn)" +
                            "VALUES" +
                            "   (1,'0132151006')," +
                            "   (2,'0132151006')," +
                            "   (3,'0132151006')," +
                            "   (1,'0133807800')," +
                            "   (2,'0133807800')," +
                            "   (1,'0132575655')," +
                            "   (2,'0132575655')," +
                            "   (1,'013299044X')," +
                            "   (2,'013299044X')," +
                            "   (1,'0132990601')," +
                            "   (2,'0132990601')," +
                            "   (3,'0132990601')," +
                            "   (1,'0133406954')," +
                            "   (2,'0133406954')," +
                            "   (3,'0133406954')," +
                            "   (1,'0133379337')," +
                            "   (2,'0133379337')," +
                            "   (1,'0136151574')," +
                            "   (2,'0136151574')," +
                            "   (4,'0136151574')," +
                            "   (1,'0133378713')," +
                            "   (2,'0133378713')," +
                            "   (1,'0133764036')," +
                            "   (2,'0133764036')," +
                            "   (3,'0133764036')," +
                            "   (1,'0133570924')," +
                            "   (2,'0133570924')," +
                            "   (3,'0133570924')," +
                            "   (1,'0132121360')," +
                            "   (2,'0132121360')," +
                            "   (3,'0132121360')," +
                            "   (5,'0132121360');"};
*/
            try (
                    Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/books?user=root&password=");
                    Statement statement = connection.createStatement();
            ){
           /**     for (String s : SQL_CREATE_TABLES){
                    System.out.println(s);
                    statement.executeQuery(s) ;
                }
            */
                System.out.println(connection);
                CreateGui app = new CreateGui();
                    Container contentPane = app.getContentPane();
                    app.setSize(600, 600);
                    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    //simple JTable
                    String simpleData[][] = {{"one-one", "one-two", "one-three"},
                            {"two-one", "two-two", "two-three"},
                            {"three-one", "three-two", "three-three"},
                            {"four-one", "four-two", "four-three"}
                    };
                    JTable tblData = new JTable(simpleData, new String[]{"Col 1", "Col 2", "Col 3"});
                    JScrollPane scroller1 = new JScrollPane(tblData);
                    contentPane.add(scroller1);

                    //Table based on SQL query
                    //the TableModel takes care of retrieving data
                    AuthorModel model = new AuthorModel();
                    JTable tblAuthors = new JTable(model);

                    JScrollPane scroller2 = new JScrollPane(tblAuthors);
                    contentPane.add(scroller2);

                    app.invalidate();
                    app.validate();
                    app.setVisible(true);

            } catch (SQLException ex) {
             //   Logger.getLogger(CreateBooksDB.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
