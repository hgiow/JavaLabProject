package dao.interfaces;

import entities.Product;
import java.util.List;

public interface ProductDAO {

    Product GetProductByName(String name);
    Product GetProductByID(int id);
    List<Product> GetAllProducts();
    void UpdateProduct(Product tProduct);
    void DeleteProduct(int id);
    void AddProduct(Product tProduct);

}
