package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class GetConnection {

    public static Connection GetDBConnection() {

        Properties properties = new Properties();
        String url = null;
        String username = null;
        String password = null;

        try (FileInputStream input = new FileInputStream("db.properties")) {
            properties.load(input);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
        } catch (IOException e) {
            System.err.println("Error reading DB configuration from properties file: " + e.getMessage());
            return null;
        }

        if (url == null || url.isEmpty()) {
            System.err.println("DB URL is empty or not found in the properties file.");
            return null;
        }

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful!");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
