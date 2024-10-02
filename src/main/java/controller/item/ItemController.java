package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService {
    @Override
    public boolean addItem(Item item) {
        String sql = "INSERT INTO item VALUES(?,?,?,?,?)";
        try {
            return CrudUtil.execute(
                    sql,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(Item item) {

        String sql = "UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?";

        try {
            return CrudUtil.execute(sql,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode());
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public Item searchItem(String itemCode) {
        try {
            String sql = "SELECT * FROM item WHERE ItemCode=?";
            try (ResultSet resultSet = CrudUtil.execute(sql, itemCode)) {
                resultSet.next();
                return new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                );
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean deleteItem(String itemCode) {
        String sql = "DELETE FROM item WHERE ItemCode=?";
        try {
            if (CrudUtil.execute(sql, itemCode)) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }

    @Override
    public ObservableList<Item> getAllItem() {
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM item";

        try {
            try (ResultSet resultSet = CrudUtil.execute(sql)) {

                while (resultSet.next()) {
                    itemObservableList.add(new Item(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDouble(4),
                            resultSet.getInt(5)
                    ));
                }
            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}