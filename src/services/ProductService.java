package services;

import dao.impl.ProductDAOImpl;
import dao.interfaces.ProductDAO;
import entities.Product;

import java.sql.Connection;
import java.util.List;

public class ProductService {

    private static ProductService instance;
    private final ProductDAO productDAO;

    private ProductService(Connection connection){
        productDAO = new ProductDAOImpl(connection);
    }

    public static ProductService getInstance(Connection connection){

        if(instance == null){
            instance = new ProductService(connection);
        }
        return instance;
    }

    public boolean ReduceStock(int productID,int quantity){

        Product product = productDAO.GetProduct(productID);

        if(product == null || product.GetQuantity() < quantity){
            return false;
        }

        product.SetQuantity(product.GetQuantity() - quantity);
        productDAO.UpdateProduct(product);
        return true;
    }

    public void AddProduct(Product product){
        productDAO.AddProduct(product);
    }

    public Product GetProduct(int id){
        return productDAO.GetProduct(id);
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
}
