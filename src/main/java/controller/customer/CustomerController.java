package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerController implements CustomerService {
    @Override
    public boolean addCustomer(Customer newCustomer) {
        try {
            String sql = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";


            return CrudUtil.execute(sql, newCustomer.getId(), newCustomer.getTitle(), newCustomer.getName(), newCustomer.getDob(), newCustomer.getSalary(), newCustomer.getAddress(), newCustomer.getCity(), newCustomer.getProvince(), newCustomer.getPostalCode());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean deleteCustomer(String customerID) {
        String sql = "DELETE FROM Customer WHERE CustID= '" + customerID + "'";
        try {
            return CrudUtil.execute(sql);
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String sql = "Update customer SET CustName=?, CustTitle=?, DOB=?,  salary=?,  CustAddress=?, City=?, Province=?, PostalCode=? WHERE CustID=?";
        try {

            return (CrudUtil.execute(sql, customer.getName(), customer.getTitle(), customer.getDob(), customer.getSalary(), customer.getAddress(), customer.getCity(), customer.getProvince(), customer.getPostalCode(), customer.getId()));

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Customer searchCustomer(String customerID) {
        String sql = "SELECT * FROM customer WHERE CustID=?";
        try {

            try (ResultSet resultSet = CrudUtil.execute(sql, customerID)) {
                if (resultSet.next()) {
                    return new Customer(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4).toLocalDate(), resultSet.getDouble(5), resultSet.getString(6), resultSet.getString(7), resultSet.getString(8), resultSet.getString(9));
                }
            }

        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * " + " FROM customer";

            try (ResultSet resultSet = CrudUtil.execute(sql)) {
                while (resultSet.next()) {
                    Customer customer = new Customer(resultSet.getString("CustID"), resultSet.getString("CustTitle"), resultSet.getString("CustName"), resultSet.getDate("dob").toLocalDate(), resultSet.getDouble("salary"), resultSet.getString("CustAddress"), resultSet.getString("city"), resultSet.getString("province"), resultSet.getString("postalCode"));
                    customerObservableList.add(customer);
                }
            }
            return customerObservableList;


        } catch (SQLException e) {
            return customerObservableList;
        }
    }
}
