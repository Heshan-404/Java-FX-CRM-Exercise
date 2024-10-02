package controller.customer;

import javafx.collections.ObservableList;
import model.Customer;

public interface CustomerService {
    boolean addCustomer(Customer newCustomer);

    boolean deleteCustomer(String customerID);

    boolean updateCustomer(Customer customer);

    Customer searchCustomer(String customerID);

    ObservableList<Customer> getAllCustomers();
}
