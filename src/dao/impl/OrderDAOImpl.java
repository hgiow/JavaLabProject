package dao.impl;

import dao.interfaces.OrderDAO;
import entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

    private final Connection connection;

    public OrderDAOImpl(Connection tConnection){
        this.connection = tConnection;
    }

    @Override
    public void AddOrder(Order order){

        String sql = "INSERT INTO [Orders](CustomerID, OrderDate, CloseDate, Status) VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,order.GetCustomerID());
            statement.setDate(2,order.GetCreatedDate());
            statement.setDate(3,order.GetClosedDate());
            statement.setString(4, order.GetStatus());
            statement.executeUpdate();

            //?
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Order GetOrder(int orderID){

        String sql = "SELECT FROM [Orders] WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,orderID);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return new Order(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getDate("created_date"),
                        resultSet.getDate("closed_date"),
                        resultSet.getString("status")
                );
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> GetOrdersByCustomer(int customerID){

        String sql = "SELECT FROM [Orders] WHERE customer_id = ?";
        List<Order> orders = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                orders.add(new Order(
                   resultSet.getInt("order_id"),
                   resultSet.getInt("customer_id"),
                   resultSet.getDate("created_date"),
                   resultSet.getDate("closed_date"),
                   resultSet.getString("status")
                ));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void DeleteOrder(Date date){

        String sql = "DELETE FROM [Orders] WHERE created_date < ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setDate(1,date);
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
