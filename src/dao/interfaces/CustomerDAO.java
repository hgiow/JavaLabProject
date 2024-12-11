package dao.interfaces;

import entities.Customer;
import java.util.List;

public interface CustomerDAO {

    void AddCustomer(Customer customer);
    Customer GetCustomer(int id);
    List<Customer> GetAllCustomers();
    void UpdateCustomer(Customer customer);
    void DeleteCustomer(int id);
    Customer GetCustomerByEmail(String email);

}
