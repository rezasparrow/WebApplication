package dataaccess;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dotin School1 on 4/20/2016.
 */
public class DataBaseManager implements Closeable {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/bank";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "reza";

    public Connection connection;
    public Statement statement;

    public DataBaseManager() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(DB_URL,USER,PASS);

        statement = connection.createStatement();
    }

    public void close() throws IOException {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
