package services;

import dao.impl.OrderDAOImpl;
import dao.impl.ProductDAOImpl;
import dao.interfaces.OrderDAO;
import dao.interfaces.ProductDAO;
import entities.Order;
import entities.OrderItem;
import entities.Product;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class OrderSevices {

    private static OrderSevices instance;
    private final OrderDAO orderDAO;
    private ProductDAO productDAO;

    private OrderSevices(Connection connection){
        orderDAO = new OrderDAOImpl(connection);
        this.productDAO = new ProductDAOImpl(connection);
    }

    public static OrderSevices getInstance(Connection connection){

        if(instance == null){
            instance = new OrderSevices(connection);
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

    public boolean PlaceOrder(int customerId, int productId, int quantity) {

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
        Order order = orderDAO.GetOrder(orderId);
        if(order != null){
            LocalDate currentDate = LocalDate.now();
            Date sqlCurrentDate = Date.valueOf(currentDate);
            order.SetClosedDate(sqlCurrentDate);
            order.SetStatus("Closed");
            orderDAO.UpdateOrder(order);
        }
    }

    public void AddOrder(Order order){
        orderDAO.AddOrder(order);
    }

    public Order GetOrder(int id){
        return orderDAO.GetOrder(id);
    }

    public List<Order> GetOrdersByCustomer(int customerID){
        return orderDAO.GetOrdersByCustomer(customerID);
    }

    public void DeleteOrdersByDate(Date date){
        orderDAO.DeleteOrder(date);
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
        return orderDAO.GetOrdersByProductID(productID);
    }
}
