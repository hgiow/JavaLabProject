package tests;

import static connection.GetConnection.GetDBConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import entities.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.CustomerService;

public class CustomerServiceTests {

    private Connection conn;
    private CustomerService customerService;

    @Before
    public void SetUp() throws SQLException {
        conn = GetDBConnection();
        CustomerService.resetInstance();
        customerService = CustomerService.getInstance(conn);
    }

    @After
    public void TearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void TestAddCustomer() throws SQLException {

        Customer newCustomer = new Customer("Nikita", "Finger", "finger123.doe@example.com",
                "password", "123456789", "123 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("finger123.doe@example.com");

        assertNotNull(retrievedCustomer);
        assertEquals("Nikita", retrievedCustomer.GetFirstName());
        assertEquals("Finger", retrievedCustomer.GetLastName());
        assertEquals("finger123.doe@example.com", retrievedCustomer.GetEmail());
    }

    @Test
    public void TestGetCustomerById() throws SQLException {

        Customer newCustomer = new Customer("John", "Doe", "john.doe@example.com",
                "password", "987654321", "456 Elm St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("john.doe@example.com");
        assertNotNull(retrievedCustomer);

        Customer customerById = customerService.GetCustomer(retrievedCustomer.GetId());
        assertNotNull(customerById);
        assertEquals("John", customerById.GetFirstName());
        assertEquals("Doe", customerById.GetLastName());
    }

    @Test
    public void TestUpdateCustomer() throws SQLException {

        Customer newCustomer = new Customer("Jane", "Doe", "jane.doe@example.com",
                "password", "123123123", "789 Pine St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("jane.doe@example.com");
        assertNotNull(retrievedCustomer);

        retrievedCustomer.SetFirstName("Janet");
        customerService.UpdateCustomer(retrievedCustomer);

        Customer updatedCustomer = customerService.GetCustomerByEmail("jane.doe@example.com");
        assertNotNull(updatedCustomer);
        assertEquals("Janet", updatedCustomer.GetFirstName());
    }

    @Test
    public void TestDeleteCustomer() throws SQLException {

        Customer newCustomer = new Customer("Alice", "Smith", "alice.smith@example.com",
                "password", "321321321", "101 Maple St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("alice.smith@example.com");
        assertNotNull(retrievedCustomer);

        customerService.DeleteCustomer(retrievedCustomer.GetId());

        Customer deletedCustomer = customerService.GetCustomerByEmail("alice.smith@example.com");
        assertNull(deletedCustomer);
    }
}