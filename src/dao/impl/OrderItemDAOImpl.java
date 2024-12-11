package dao.impl;

import dao.interfaces.OrderItemDAO;
import entities.Order;
import entities.OrderItem;

import javax.swing.plaf.BorderUIResource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {

    private Connection connection;

    public OrderItemDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void AddOrderItem(OrderItem orderItem){

        String sql = "INSERT INTO OrderItems (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,orderItem.GetOrderID());
            statement.setInt(2,orderItem.GetProductID());
            statement.setInt(3,orderItem.GetQuantity());
            statement.setBigDecimal(4,orderItem.GetPrice());
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OrderItem> GetOrderItems(int id){

        String sql = "SELECT * FROM OrderItems WHERE order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                OrderItem orderItem = new OrderItem(
                        resultSet.getInt("id"),
                        resultSet.getInt("order_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getBigDecimal("price")
                );
                orderItems.add(orderItem);
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public void UpdateOrderItem(OrderItem orderItem){

        String sql = "UPDATE OrderItems SET quantity = ?, price = ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,orderItem.GetQuantity());
            statement.setBigDecimal(2, orderItem.GetPrice());
            statement.setInt(3,orderItem.GetID());
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteOrderItem(int orderItemID){

        String sql = "DELETE FROM OrderItems WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, orderItemID);
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
