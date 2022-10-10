package org.enigma.data.db;

import java.sql.*;

public class AppDatabase {
    private static final String CONNECTION_STRING = "jdbc:h2:~/secret-db";

    private static AppDatabase instance = null;

    private static Connection connection;

    public static synchronized AppDatabase getInstance() throws SQLException {
        if (instance == null) instance = new AppDatabase();
        return instance;
    }

    public AppDatabase() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            String quarry = "";
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println("H2 DATABASE CONNECTION SUCCESS");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
