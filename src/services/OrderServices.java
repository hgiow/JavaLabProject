package services;

import dao.impl.OrderDAOImpl;
import dao.impl.OrderItemDAOImpl;
import dao.impl.ProductDAOImpl;
import dao.interfaces.OrderDAO;
import dao.interfaces.OrderItemDAO;
import dao.interfaces.ProductDAO;
import entities.Order;
import entities.OrderItem;
import entities.Product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderServices {

    private static OrderServices instance;
    private final OrderDAO orderDAO;
    private ProductDAO productDAO;
    private OrderItemDAO orderItemDAO;

    private OrderServices(Connection connection){
        orderDAO = new OrderDAOImpl(connection);
        this.productDAO = new ProductDAOImpl(connection);
        this.orderItemDAO = new OrderItemDAOImpl(connection);
    }

    public static OrderServices getInstance(Connection connection){

        if(instance == null){
            instance = new OrderServices(connection);
        }
        return instance;
    }

    public boolean ReduceStock(int productID, int quantity){

        Product product = productDAO.GetProductByID(productID);

        if(product == null || product.GetQuantity() < quantity){
            return false;
        }

        product.SetQuantity(product.GetQuantity() - quantity);
        productDAO.UpdateProduct(product);
        return true;
    }

    public boolean PlaceOrder(int customerId, int productId, int quantity) throws SQLException {

        boolean isStockReduced = ReduceStock(productId, quantity);
        if (!isStockReduced) {
            return false;
        }

        LocalDate currentDate = LocalDate.now();
        Date sqlCurrentDate = Date.valueOf(currentDate);

        Order order = new Order(customerId, sqlCurrentDate, null, "Pending");
        orderDAO.AddOrder(order);

        return true;
    }

    public void CloseOrder(int orderId){

        Order order = orderDAO.GetOrderByID(orderId);
        if(order != null){
            LocalDate currentDate = LocalDate.now();
            Date sqlCurrentDate = Date.valueOf(currentDate);
            order.SetClosedDate(sqlCurrentDate);
            order.SetStatus("Closed");
            orderDAO.UpdateOrder(order);
        }
    }

    public void AddOrder(Order order) throws SQLException{
        orderDAO.AddOrder(order);
    }

    public Order GetOrderByID(int id){
        return orderDAO.GetOrderByID(id);
    }

    public List<Order> GetOrdersByCustomer(int customerID){
        return orderDAO.GetOrdersByCustomer(customerID);
    }

    public void DeleteOrdersByDate(Date date){
        orderDAO.DeleteOrdersBeforeDate(date);
    }

    public boolean CheckStock(OrderItem orderItem){
        Product product = productDAO.GetProductByID(orderItem.GetProductID());
        if(product != null){
            int stock = product.GetQuantity();
            return stock >= orderItem.GetQuantity();
        }
        return false;
    }

    public List<Order> GetAllOrdersByProductID(int productID){
        return orderItemDAO.GetOrdersByProductID(productID);
    }

    public void UpdateOrder(Order order){
        orderDAO.UpdateOrder(order);
    }

    public void DeleteOrder(int id){
        orderDAO.DeleteOrderByID(id);
    }

    public Order GetOrderByCustomerAndDate(int customerID, Date createdDate) throws SQLException {
        return orderDAO.GetOrderByCustomerAndDate(customerID, createdDate);
    }

    public void AddOrderItem(OrderItem orderItem){
        orderItemDAO.AddOrderItem(orderItem);
    }

    public void DeleteOrdersBeforeDate(Date date){
        orderDAO.DeleteOrdersBeforeDate(date);
    }
}
