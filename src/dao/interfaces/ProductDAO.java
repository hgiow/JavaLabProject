package dao.interfaces;

import entities.Product;
import java.util.List;

public interface ProductDAO {

    void AddProduct(Product tProduct);
    Product GetProduct(int id);
    List<Product> GetAllProducts();
    void UpdateProduct(Product tProduct);
    void DeleteProduct(int id);

}
