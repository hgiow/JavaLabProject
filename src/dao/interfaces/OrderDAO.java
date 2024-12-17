package dao.interfaces;

import entities.Order;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OrderDAO {

    void AddOrder(Order order) throws SQLException;
    void UpdateOrder(Order order);
    void DeleteOrdersBeforeDate(Date date);
    void DeleteOrderByID(int id);
    Order GetOrderByID(int id);
    Order GetOrderByCustomerAndDate(int customerID, Date createdDate) throws SQLException;
    List<Order> GetOrdersByCustomer(int customerID);

}
