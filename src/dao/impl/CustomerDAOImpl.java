package dao.impl;

import dao.interfaces.CustomerDAO;
import entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAOImpl implements CustomerDAO {

    private final Connection connection;
    public CustomerDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void AddCustomer(Customer customer){

        String sql = "INSERT INTO Customers(first_name, last_name, " +
                "email, password, phone, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, customer.GetFirstName());
            statement.setString(2, customer.GetLastName());
            statement.setString(3, customer.GetEmail());
            statement.setString(4, customer.GetPassword());
            statement.setString(5, customer.GetPhone());
            statement.setString(6, customer.GetAddress());

            statement.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Customer GetCustomer(int id){
        String sql = "SELECT * FROM Customers WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Customer(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> GetAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Customers";

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                customers.add(new Customer(
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("phone"),
                        resultSet.getString("address")
                ));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void UpdateCustomer(Customer customer){
        String sql = "UPDATE Customers SET first_name = ?, last_name = ?" +
                "email = ?, password = ?, phone = ?, address = ? WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, customer.GetFirstName());
            statement.setString(2, customer.GetLastName());
            statement.setString(3, customer.GetEmail());
            statement.setString(4, customer.GetPassword());
            statement.setString(5, customer.GetPhone());
            statement.setString(6, customer.GetAddress());
            statement.setInt(7, customer.GetId());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteCustomer(int id){
        String sql = "DELETE FROM Customers WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
