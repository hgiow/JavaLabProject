package services;

import dao.impl.CustomerDAOImpl;
import dao.interfaces.CustomerDAO;
import entities.Customer;

import java.sql.Connection;
import java.util.List;

public class CustomerServices {

    private static CustomerServices instance;
    private CustomerDAO customerDAO;

    private CustomerServices(Connection connection){
        customerDAO = new CustomerDAOImpl(connection);
    }

    public static void ResetInstance(){
        instance = null;
    }

    public static CustomerServices getInstance(Connection connection) {
        if (instance == null){
            instance = new CustomerServices(connection);
        }
        return instance;
    }

    private boolean ValidateCustomer(Customer customer){
        if(customer == null || customer.GetEmail() == null || !customer.GetEmail().contains("@")){
            return false;
        }
        return true;
    }

    public List<Customer> GetAllCustomers(){
        return customerDAO.GetAllCustomers();
    }

    public void AddCustomer(Customer customer){
        if (ValidateCustomer(customer)){
            customerDAO.AddCustomer(customer);
        } else System.out.println("Invalid customer data");
    }

    public Customer GetCustomer(int id){
        return customerDAO.GetCustomer(id);
    }

    public Customer GetCustomerByEmail(String email){
        return customerDAO.GetCustomerByEmail(email);
    }

    public void DeleteCustomer(int id){
        customerDAO.DeleteCustomer(id);
    }

    public void UpdateCustomer(Customer customer){
        customerDAO.UpdateCustomer(customer);
    }
}
