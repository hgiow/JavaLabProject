package dao.impl;

import dao.interfaces.ProductDAO;
import entities.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final Connection connection;

    public ProductDAOImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void AddProduct(Product tProduct){

        String sql = "INSERT INTO Product(name, description, price, quantity)" +
                " VALUES (?, ?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1, tProduct.GetName());
            statement.setString(2, tProduct.GetDescription());
            statement.setBigDecimal(3, tProduct.GetPrice());
            statement.setInt(4, tProduct.GetQuantity());

            statement.executeUpdate();

        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Product GetProduct(int id){

        String sql = "SELECT * FROM Product WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1,id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new Product(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getInt("quantity")
                );
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Product> GetAllProducts() {

        String sql = "SELECT * From Products";
        List<Product> products = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                products.add(new Product(
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getInt("quantity")
                ));
            }

        } catch(SQLException e){
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public void UpdateProduct(Product tProduct){

        String sql = "UPDATE Product SET name = ?, description = ?, price = ?, quantity = ?" +
                "WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setString(1,tProduct.GetName());
            statement.setString(2,tProduct.GetDescription());
            statement.setBigDecimal(3,tProduct.GetPrice());
            statement.setInt(4,tProduct.GetQuantity());
            statement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteProduct(int id){

        String sql = "DELETE FROM Product WHERE id = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setInt(1, id);
            statement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}

//

////
