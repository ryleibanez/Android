package com.example.jaj;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {

//    private static String serverName = "eu-az-sql-servdf368cbedbcf4c1791c9bed2a7ce88e2.database.windows.net"; // Example: "yourserver.database.windows.net"
//    private static String databaseName = "dig6lw4nlfn7dnm"; // Example: "yourdb"
//    private static String username = "u113czxob0lgt7u"; // Example: "yourusername"
//    private static String password = "FQZu6IJrbLQTjYqX1P5ghFCTU"; // Example: "yourpassword"


    private static String serverName = "192.168.68.114"; // Example: "yourserver.database.windows.net"
    private static String databaseName = "JAJ4"; // Example: "yourdb"
    private static String username = "mylogin"; // Example: "yourusername"
    private static String password = "!Godofwar2"; // Example: "yourpassword"

    public static Connection connect() {
        Connection connection = null;

        // Enable network operations on the main thread (not recommended for production)
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String connectionString = String.format("jdbc:jtds:sqlserver://%s:%d;databaseName=%s;user=%s;password=%s;",
                serverName, 1433, databaseName, username, password);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}