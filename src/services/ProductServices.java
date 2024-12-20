package services;

import dao.impl.ProductDAOImpl;
import dao.interfaces.ProductDAO;
import entities.Product;

import java.sql.Connection;
import java.util.List;

public class ProductServices {

    private static ProductServices instance;
    private final ProductDAO productDAO;

    private ProductServices(Connection connection){
        productDAO = new ProductDAOImpl(connection);
    }

    public static ProductServices getInstance(Connection connection){

        if(instance == null){
            instance = new ProductServices(connection);
        }
        return instance;
    }

    public static void ResetInstance(){
        instance = null;
    }

    public void AddProduct(Product product){
        productDAO.AddProduct(product);
    }

    public Product GetProductByID(int id){
        return productDAO.GetProductByID(id);
    }

    public List<Product> GetAllProduct(){
        return productDAO.GetAllProducts();
    }

    public void UpdateProduct(Product product){
        productDAO.UpdateProduct(product);
    }

    public void DeleleProduct(int id){
        productDAO.DeleteProduct(id);
    }

    public Product GetProductByName(String name){
        return productDAO.GetProductByName(name);
    }

    public void DeleteProduct(int id){
        productDAO.DeleteProduct(id);
    }
}
