package dao.interfaces;

import entities.Order;

import java.sql.Date;
import java.util.List;

public interface OrderDAO {

    void AddOrder(Order order);
    Order GetOrder(int id);
    List<Order> GetOrdersByCustomer(int customerID);
    void DeleteOrder(Date date);

}
