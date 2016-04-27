package dataaccess;

import javax.xml.crypto.Data;
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
    static final String DB_URL = "jdbc:mysql://localhost:3306/bank?characterEncoding=UTF-8";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "reza";
    private static Connection connection = null;
    private static Object lock = new Object();


    private DataBaseManager() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(DB_URL, USER, PASS);

    }

    public static Connection getConnection() throws SQLException {
        synchronized (lock) {

            if (connection == null) {
                new DataBaseManager();
            }
            connection.setAutoCommit(true);
            return connection;

        }
    }

    public void close() throws IOException {

        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
