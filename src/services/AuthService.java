package services;

import dao.impl.CustomerDAOImpl;
import dao.interfaces.CustomerDAO;
import entities.Customer;

import java.sql.Connection;

public class AuthService {

    private static AuthService instance;
    private CustomerDAO customerDAO;

    private AuthService(Connection connection) {
        customerDAO = new CustomerDAOImpl(connection);
    }

    public static AuthService GetInstance(Connection connection) {
        if (instance == null) {
            instance = new AuthService(connection);
        }
        return instance;
    }

    public boolean Login(String email, String password) {
        Customer customer = customerDAO.GetCustomerByEmail(email);
        return customer != null && customer.GetPassword().equals(password);
    }

    public boolean IsAuthorized(String email, String requiredRole) {
        Customer customer = customerDAO.GetCustomerByEmail(email);
        return customer != null && customer.GetRole().equals(requiredRole);
    }

    public boolean IsAdmin(String email) {
        return IsAuthorized(email, "admin");
    }

    public boolean IsUser(String email) {
        return IsAuthorized(email, "user");
    }

    public boolean HasAccess(String email, String action) {
        Customer customer = customerDAO.GetCustomerByEmail(email);
        if (customer == null) {
            return false;
        }

        if ("admin".equals(customer.GetRole())) {
            return true;
        }
        return "user".equals(customer.GetRole()) && "view".equals(action);
    }
}