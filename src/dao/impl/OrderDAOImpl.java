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
    public void AddOrder(Order order) throws SQLException {

        String sql = "INSERT INTO Orders (customer_id, created_date, closed_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.GetCustomerID());
            statement.setDate(2, order.GetCreatedDate());
            statement.setDate(3, order.GetClosedDate());
            statement.setString(4, order.GetStatus());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.SetID(generatedKeys.getInt(1));
                }
            }
        }
    }

    @Override
    public Order GetOrderByID(int orderID){

        String sql = "SELECT * FROM Orders WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, orderID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){

                int customerId = resultSet.getInt("customer_id");
                Date createdDate = resultSet.getDate("created_date");
                Date closedDate = resultSet.getDate("closed_date");
                String status = resultSet.getString("status");

                Order order = new Order();
                order.SetID(orderID);
                order.SetCustomerID(customerId);
                order.SetCreatedDate(createdDate);
                order.SetClosedDate(closedDate);
                order.SetStatus(status);
                return order;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> GetOrdersByCustomer(int customerID){

        String sql = "SELECT * FROM Orders WHERE customer_id = ?";
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                orders.add(new Order(
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
    public void DeleteOrdersBeforeDate(Date date){

        String sql = "DELETE FROM Orders WHERE created_date < ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, date);
            statement.executeUpdate();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteOrderByID(int id){

        String sql = "DELETE FROM Orders WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void UpdateOrder(Order order) {

        String sql = "UPDATE Orders SET customer_id = ?, created_date = ?, closed_date = ?, " +
                "status = ? WHERE order_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, order.GetCustomerID());
            statement.setDate(2, order.GetCreatedDate());
            statement.setDate(3, order.GetClosedDate());
            statement.setString(4, order.GetStatus());
            statement.setInt(5, order.GetID());
            statement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Order GetOrderByCustomerAndDate(int customerId, Date createdDate) throws SQLException {

        String sql = "SELECT * FROM Orders WHERE customer_id = ? AND created_date = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.setDate(2, createdDate);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                int orderId = resultSet.getInt("order_id");
                Date closedDate = resultSet.getDate("closed_date");
                String status = resultSet.getString("status");

                Order order = new Order();
                order.SetID(orderId);
                order.SetCustomerID(customerId);
                order.SetCreatedDate(createdDate);
                order.SetClosedDate(closedDate);
                order.SetStatus(status);
                return order;
            }
        }
        return null;
    }
}