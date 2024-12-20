package tests;

import entities.Customer;
import entities.Order;
import entities.OrderItem;
import entities.Product;
import org.junit.After;
import services.CustomerServices;
import services.OrderServices;
import services.ProductServices;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static connection.GetConnection.GetDBConnection;
import static org.junit.Assert.*;

public class OrderServiceTests {

    private OrderServices orderServices;
    private ProductServices productService;
    private CustomerServices customerService;

    private Connection conn;

    @Before
    public void SetUp() throws SQLException {

        conn = GetDBConnection();

        OrderServices.ResetInstance();
        orderServices = OrderServices.GetInstance(conn);

        ProductServices.ResetInstance();
        productService = ProductServices.getInstance(conn);

        CustomerServices.ResetInstance();
        customerService = CustomerServices.getInstance(conn);
    }

    @After
    public void TearDown() throws SQLException {

        String sql1 = "DELETE FROM customers;";
        PreparedStatement statement1 = conn.prepareStatement(sql1);
        statement1.executeUpdate();

        String sql2 = "DELETE FROM product;";
        PreparedStatement statement2 = conn.prepareStatement(sql2);
        statement2.executeUpdate();

        String sql3 = "DELETE FROM orderitem;";
        PreparedStatement statement3 = conn.prepareStatement(sql3);
        statement3.executeUpdate();

        String sql4 = "DELETE FROM orders;";
        PreparedStatement statement4 = conn.prepareStatement(sql4);
        statement4.executeUpdate();

        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void TestCloseOrder() throws SQLException {

        Customer newCustomer = new Customer("Nikita", "PowerOfSoul", "nikita13.doe@example.com",
                "password123", "1234567890", "456 Main St", "user");

        customerService.AddCustomer(newCustomer);
        Customer retrievedCustomer = customerService.GetCustomerByEmail("nikita13.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");
        orderServices.AddOrder(newOrder);

        Order orderToClose = orderServices.GetOrderByCustomerAndDate(retrievedCustomer.GetID(), sqlCurrentDate);

        assertNotNull(orderToClose);

        orderServices.CloseOrder(orderToClose.GetID());

        Order closedOrder = orderServices.GetOrderByCustomerAndDate(retrievedCustomer.GetID(), sqlCurrentDate);

        System.out.println(closedOrder.GetStatus());

        assertNotNull(closedOrder);
        assertEquals("Closed", closedOrder.GetStatus());
    }

    @Test
    public void TestDeleteOrdersBeforeDate() throws SQLException {

        Customer newCustomer = new Customer("Alice", "Smith", "alice.smith@example.com",
                "password123", "1234567890", "789 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("alice.smith@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2023, 6, 1);
        LocalDate date3 = LocalDate.of(2023, 12, 1);
        Date sqlDate1 = Date.valueOf(date1);
        Date sqlDate2 = Date.valueOf(date2);
        Date sqlDate3 = Date.valueOf(date3);

        Order order1 = new Order(retrievedCustomer.GetID(), sqlDate1, null, "Pending");
        Order order2 = new Order(retrievedCustomer.GetID(), sqlDate2, null, "Pending");
        Order order3 = new Order(retrievedCustomer.GetID(), sqlDate3, null, "Pending");

        orderServices.AddOrder(order1);
        orderServices.AddOrder(order2);
        orderServices.AddOrder(order3);

        assertNotNull(orderServices.GetOrderByID(order1.GetID()));
        assertNotNull(orderServices.GetOrderByID(order2.GetID()));
        assertNotNull(orderServices.GetOrderByID(order3.GetID()));

        LocalDate cutoffDate = LocalDate.of(2023, 7, 1);
        Date sqlCutoffDate = Date.valueOf(cutoffDate);
        orderServices.DeleteOrdersBeforeDate(sqlCutoffDate);

        assertNull(orderServices.GetOrderByID(order1.GetID()));
        assertNull(orderServices.GetOrderByID(order2.GetID()));
        assertNotNull(orderServices.GetOrderByID(order3.GetID()));
    }

    @Test
    public void TestCheckStock() throws SQLException {

        Product newProduct = new Product("Aboba", "High-end gaming laptop",
                new BigDecimal("1500.00"), 10);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Aboba");

        assertNotNull(retrievedProduct);

        Customer newCustomer = new Customer("Nikita", "Finger", "finger123123.doe@example.com",
                "password123", "1234567890", "228 Main St", "user");

        customerService.AddCustomer(newCustomer);
        Customer retrievedCustomer = customerService.GetCustomerByEmail("finger123123.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);

        Order retrievedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNotNull(retrievedOrder);

        OrderItem orderItem = new OrderItem(retrievedOrder.GetID(), retrievedProduct.GetID(),
                5, new BigDecimal("100.00"));

        boolean result = orderServices.CheckStock(orderItem);

        assertTrue(result);
    }

    @Test
    public void TestAddOrder() throws SQLException {

        Customer newCustomer = new Customer("Jack", "Doe", "jack.doe@example.com",
                "password123", "1234567890", "123 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("jack.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);

        Order retrievedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNotNull(retrievedOrder);
        assertEquals("Pending", retrievedOrder.GetStatus());
        assertEquals(sqlCurrentDate, retrievedOrder.GetCreatedDate());
    }

    @Test
    public void TestGetOrderById() throws SQLException {

        Customer newCustomer = new Customer("John", "Doe", "john.doe@example.com",
                "password123", "1234567890", "123 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("john.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);

        Order retrievedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNotNull(retrievedOrder);
        assertEquals("Pending", retrievedOrder.GetStatus());
        assertEquals(sqlCurrentDate, retrievedOrder.GetCreatedDate());
    }

    @Test
    public void TestUpdateOrder() throws SQLException {

        Customer newCustomer = new Customer("Legenda", "Doe", "lega.doe@example.com",
                "password123", "1234567890", "123 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("lega.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);

        newOrder.SetStatus("Completed");

        orderServices.UpdateOrder(newOrder);

        Order updatedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNotNull(updatedOrder);
        assertEquals("Completed", updatedOrder.GetStatus());
    }

    @Test
    public void TestDeleteOrder() throws SQLException {

        Customer newCustomer = new Customer("Abauenda", "Doe", "abauenda.doe@example.com",
                "password123", "1234567890", "123 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("abauenda.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);
        Order retrievedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNotNull(retrievedOrder);

        orderServices.DeleteOrder(newOrder.GetID());

        Order deletedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNull(deletedOrder);
    }

    @Test
    public void TestReduceStock() throws SQLException {

        Product newProduct = new Product("Laptop", "High-end gaming laptop",
                new BigDecimal("1500.00"), 10);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Laptop");

        assertNotNull(retrievedProduct);

        boolean result = orderServices.ReduceStock(retrievedProduct.GetID(), 5);

        assertTrue(result);
    }

    @Test
    public void TestPlaceOrder() throws SQLException {

        Product newProduct = new Product("Laptop", "High-end gaming laptop",
                new BigDecimal("1500.00"), 10);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Laptop");

        assertNotNull(retrievedProduct);

        Customer newCustomer = new Customer("Bebra", "Hue", "bebra.doe@example.com",
                "password123", "1234567890", "123 Main St", "user");

        customerService.AddCustomer(newCustomer);
        Customer retrievedCustomer = customerService.GetCustomerByEmail("bebra.doe@example.com");

        assertNotNull(retrievedCustomer);

        boolean result = orderServices.PlaceOrder(retrievedCustomer.GetID(), retrievedProduct.GetID(), 5);

        assertTrue(result);
    }

    @Test
    public void TestGetOrdersByCustomer() throws SQLException {

        Customer newCustomer = new Customer("Lucy", "Doe", "lucy.doe@example.com",
                "password123", "1234567890", "456 Main St", "user");

        customerService.AddCustomer(newCustomer);
        Customer retrievedCustomer = customerService.GetCustomerByEmail("lucy.doe@example.com");

        assertNotNull(retrievedCustomer);

        Product newProduct = new Product("Smartphone", "Latest model smartphone",
                new BigDecimal("800.00"), 20);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Smartphone");

        assertNotNull(retrievedProduct);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);

        List<Order> orders = orderServices.GetOrdersByCustomer(retrievedCustomer.GetID());

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }

    @Test
    public void TestDeleteOrdersByDate() throws SQLException {

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        orderServices.DeleteOrdersByDate(sqlCurrentDate);

        List<Order> orders = orderServices.GetOrdersByCustomer(1);

        assertTrue(orders.isEmpty());
    }

    @Test
    public void TestGetAllOrdersByProductID() throws SQLException {

        Product newProduct = new Product("Smartphone", "Latest model smartphone",
                new BigDecimal("800.00"), 20);

        productService.AddProduct(newProduct);

        Product retrievedProduct = productService.GetProductByName("Smartphone");

        assertNotNull(retrievedProduct);

        Customer newCustomer = new Customer("samara", "Doe", "samara.doe@example.com",
                "password123", "1234567890", "456 Main St", "user");

        customerService.AddCustomer(newCustomer);

        Customer retrievedCustomer = customerService.GetCustomerByEmail("samara.doe@example.com");

        assertNotNull(retrievedCustomer);

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);
        Order newOrder = new Order(retrievedCustomer.GetID(), sqlCurrentDate, null, "Pending");

        orderServices.AddOrder(newOrder);

        Order retrievedOrder = orderServices.GetOrderByID(newOrder.GetID());

        assertNotNull(retrievedOrder);

        OrderItem orderItem = new OrderItem(retrievedOrder.GetID(), retrievedProduct.GetID(),
                5, new BigDecimal("100.00"));

        orderServices.AddOrderItem(orderItem);

        List<Order> orders = orderServices.GetAllOrdersByProductID(retrievedProduct.GetID());

        assertNotNull(orders);
        assertFalse(orders.isEmpty());
    }
}