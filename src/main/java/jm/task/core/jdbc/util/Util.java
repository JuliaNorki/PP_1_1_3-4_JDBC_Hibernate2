package jm.task.core.jdbc.util;

import java.nio.channels.ScatteringByteChannel;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/nor";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootroot"; // для пароля

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
             connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("get connect");
        } catch (SQLException  | ClassNotFoundException e) {
            System.out.println("error");

        }
        return connection;
    }
}









