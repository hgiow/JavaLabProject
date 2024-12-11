package dao.interfaces;

import entities.OrderItem;
import java.util.List;

public interface OrderItemDAO {

    void AddOrderItem(OrderItem orderItem);
    List<OrderItem> GetOrderItems(int id);
    void UpdateOrderItem(OrderItem orderItem);
    void DeleteOrderItem(int id);
}
