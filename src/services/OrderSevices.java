package services;

import dao.impl.OrderDAOImpl;
import dao.impl.ProductDAOImpl;
import dao.interfaces.OrderDAO;
import dao.interfaces.ProductDAO;
import entities.Order;
import entities.OrderItem;
import entities.Product;

import javax.print.attribute.standard.OutputDeviceAssigned;
import java.sql.Connection;
import java.sql.Date;
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
        Product product = productDAO.GetProduct(orderItem.GetProductID());
        if(product != null){
            int stock = product.GetQuantity();
            return stock >= orderItem.GetQuantity();
        }
        return false;
    }

}
