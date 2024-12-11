package services;

import dao.impl.CustomerDAOImpl;
import dao.interfaces.CustomerDAO;
import entities.Customer;

import java.sql.Connection;
import java.util.List;

public class CustomerService {

    private static CustomerService instance;
    private CustomerDAO customerDAO;

    private CustomerService(Connection connection){
        customerDAO = new CustomerDAOImpl(connection);
    }

    public static CustomerService getInstance(Connection connection) {
        if (instance == null){
            instance = new CustomerService(connection);
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
}
