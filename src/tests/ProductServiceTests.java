package tests;

import static connection.GetConnection.GetDBConnection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import entities.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import services.ProductService;

public class ProductServiceTests {

    private Connection conn;
    private ProductService productService;

    @Before
    public void SetUp() throws SQLException {
        conn = GetDBConnection();
        ProductService.resetInstance();
        productService = ProductService.getInstance(conn);
    }

    @After
    public void TearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void TestAddProduct() throws SQLException {

        Product newProduct = new Product("Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);

        productService.AddProduct(newProduct);

        Product retrievedProduct = productService.GetProductByName("Laptop");

        assertNotNull(retrievedProduct);
        assertEquals("Laptop", retrievedProduct.GetName());
        assertEquals("High-end gaming laptop", retrievedProduct.GetDescription());
        assertEquals(new BigDecimal("1500.00"), retrievedProduct.GetPrice());
        assertEquals(10, retrievedProduct.GetQuantity());
    }

    @Test
    public void TestGetProductById() throws SQLException {

        Product newProduct = new Product("Smartphone", "Latest model smartphone",
                new BigDecimal("800.00"), 20);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Smartphone");

        assertNotNull(retrievedProduct);

        Product productById = productService.GetProductByID(retrievedProduct.GetID());

        assertNotNull(productById);
        assertEquals("Smartphone", productById.GetName());
        assertEquals("Latest model smartphone", productById.GetDescription());
    }

    @Test
    public void TestUpdateProduct() throws SQLException {

        Product newProduct = new Product("Tablet", "10-inch tablet",
                new BigDecimal("300.00"), 15);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Tablet");

        assertNotNull(retrievedProduct);

        retrievedProduct.SetPrice(new BigDecimal("350.00"));
        productService.UpdateProduct(retrievedProduct);
        Product updatedProduct = productService.GetProductByName("Tablet");

        assertNotNull(updatedProduct);
        assertEquals(new BigDecimal("350.00"), updatedProduct.GetPrice());
    }

    @Test
    public void TestDeleteProduct() throws SQLException {

        Product newProduct = new Product("Headphones", "Noise-cancelling headphones",
                new BigDecimal("200.00"), 30);

        productService.AddProduct(newProduct);
        Product retrievedProduct = productService.GetProductByName("Headphones");

        assertNotNull(retrievedProduct);

        productService.DeleteProduct(retrievedProduct.GetID());
        Product deletedProduct = productService.GetProductByName("Headphones");

        assertNull(deletedProduct);
    }
}