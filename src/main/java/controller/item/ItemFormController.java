package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {
    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPacSize;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtUnitPrice;

    ItemService itemController = new ItemController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPacSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        loadTable();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observableValue, item, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void setValueToText(Item newValue) {
        txtItemCode.setText(newValue.getItemCode());
        txtDescription.setText(newValue.getDescription());
        txtPackSize.setText(newValue.getPackSize());
        txtUnitPrice.setText(newValue.getUnitPrice().toString());
        txtQty.setText(newValue.getQtyOnHand().toString());
    }

    private void loadTable() {
        ObservableList<Item> items = itemController.getAllItem();
        tblItems.setItems(items);
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );
        Boolean isAdd = itemController.addItem(item);
        if (isAdd) {
            new Alert(Alert.AlertType.INFORMATION, "Item Added!!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Added!!").show();
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        if (itemController.deleteItem(txtItemCode.getText())) {
            new Alert(Alert.AlertType.INFORMATION, txtItemCode.getText() + " Item Deleted").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Not Deleted").show();
        }

    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        Item item = itemController.searchItem(txtItemCode.getText());
        if (item != null) {
            setValueToText(item);
        } else {
            new Alert(Alert.AlertType.ERROR, txtItemCode.getText() + " Item Not Found").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        Item item = new Item(
                txtItemCode.getText(),
                txtDescription.getText(),
                txtPackSize.getText(),
                Double.parseDouble(txtUnitPrice.getText()),
                Integer.parseInt(txtQty.getText())
        );

        if (itemController.updateItem(item)) {
            new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Item not Updated!").show();
        }
    }


}