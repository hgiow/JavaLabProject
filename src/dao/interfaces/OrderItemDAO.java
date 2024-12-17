package dao.interfaces;

import entities.Order;
import entities.OrderItem;
import java.util.List;

public interface OrderItemDAO {

    void AddOrderItem(OrderItem orderItem);
    List<OrderItem> GetOrderItems(int id);
    List<Order> GetOrdersByProductID(int productID);
    void UpdateOrderItem(OrderItem orderItem);
    void DeleteOrderItem(int id);
}
