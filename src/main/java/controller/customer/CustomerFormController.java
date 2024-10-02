package controller.customer;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colDob;

    @FXML
    private JFXComboBox<String> cmbTitle;

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colCity;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colPostalCode;

    @FXML
    private TableColumn colProvince;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableColumn colTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private TableView<Customer> tblCustomers;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtProvince;

    @FXML
    private JFXTextField txtSalary;

    CustomerService customerController = new CustomerController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> customerTittleList = FXCollections.observableArrayList();
        customerTittleList.add("Mr");
        customerTittleList.add("Mrs");
        customerTittleList.add("Miss");
        customerTittleList.add("Ms");
        cmbTitle.setItems(customerTittleList);
        loadTable();

        tblCustomers.getSelectionModel().selectedItemProperty().addListener(((observableValue, customer, newValue) -> {
            if (newValue != null) {
                setValue(newValue);
            }
        }));
    }

    private void setValue(Customer customerValues) {
        txtCustomerId.setText(customerValues.getId());
        txtName.setText(customerValues.getName());
        txtAddress.setText(customerValues.getAddress());
        txtSalary.setText(customerValues.getSalary() + "");
        txtCity.setText(customerValues.getCity());
        txtPostalCode.setText(customerValues.getPostalCode());
        txtProvince.setText(customerValues.getProvince());
        dateDob.setValue(customerValues.getDob());
        cmbTitle.setValue(customerValues.getTitle());
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Customer customer = new Customer(txtCustomerId.getText(), cmbTitle.getValue(), txtName.getText(), dateDob.getValue(), Double.parseDouble(txtSalary.getText()), txtAddress.getText(), txtCity.getText(), txtProvince.getText(), txtPostalCode.getText());
        if (customerController.addCustomer(customer)) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Added :)").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Added!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (customerController.deleteCustomer(txtCustomerId.getText())) {
            new Alert(Alert.AlertType.INFORMATION,  txtCustomerId.getText() + ": Customer Deleted !!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION,  txtCustomerId.getText() + ": Customer not Deleted !!").show();
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadTable();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Customer customer = customerController.searchCustomer(txtCustomerId.getText());
        if (customer != null) {
            setValue(customer);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if (customerController.updateCustomer(new Customer(
                txtCustomerId.getText(),
                cmbTitle.getValue(),
                txtName.getText(),
                dateDob.getValue(),
                Double.parseDouble(txtSalary.getText()),
                txtAddress.getText(),
                txtCity.getText(),
                txtProvince.getText(),
                txtPostalCode.getText()
        ))) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Customer Not Updated!").show();
        }
    }

    private void loadTable() {

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));


        tblCustomers.setItems(customerController.getAllCustomers());

    }


}