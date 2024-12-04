package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dao.interfaces.CustomerDAO;
import dao.impl.CustomerDAOImpl;
import entities.Customer;

public class TestConnection {
    public static void main(String[] args) {

        Properties properties = new Properties();
        String url = null;

        try (FileInputStream input = new FileInputStream("db.properties")) {
            properties.load(input);
            url = properties.getProperty("db.url");
        } catch (IOException e) {
            System.err.println("Error reading DB URL from properties file: " + e.getMessage());
            return;
        }

        if (url == null || url.isEmpty()) {
            System.err.println("DB URL is empty or not found in the properties file.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(url)) {
            System.out.println("Connection successful!");

            CustomerDAO customerDAO = new CustomerDAOImpl(conn);

            Customer newCustomer = new Customer("John", "Doe", "john.doe@example.com",
                    "securepassword", "123456789", "123 Main St");

            customerDAO.AddCustomer(newCustomer);

            System.out.println("Customer added successfully!");

            int customerId = 1;
            Customer fetchedCustomer = customerDAO.GetCustomer(customerId);

            if (fetchedCustomer != null) {
                System.out.println("Customer fetched successfully!");
                System.out.println("Name: " + fetchedCustomer.GetFirstName() + " " + fetchedCustomer.GetLastName());
                System.out.println("Email: " + fetchedCustomer.GetEmail());
            } else {
                System.out.println("Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

