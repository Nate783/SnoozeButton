/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static tracker.Tracker.*;

public class FXMLDocumentController implements Initializable {
    
    @FXML private Button invBtnAddToInventory, invBtnModify, invBtnDelete, invBtnFilters;
    
    @FXML private TableView<Product> invTable;
    @FXML private TableColumn<Product, Integer> invColProdUID;
    @FXML private TableColumn<Product, String> invColProdName;
    @FXML private TableColumn<Product, Double> invColProdCost;
    @FXML private TableColumn<Product, Double> invColProdPrice;
    @FXML private TableColumn<Product, Integer> invColProdQty;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // prepare columns in table
        invColProdUID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        invColProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColProdCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        invColProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        invColProdQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        
        // get products for table
        invTable.setItems(getProducts());
    }    
    
    @FXML
    private void handleAddClick(ActionEvent event) {
        try {
            AddInventoryController();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            invTable.setItems(getProducts());
        }
    }
    
    @FXML
    private void handleModifyClick(ActionEvent event) {
        //todo
    }
    
    @FXML
    private void handleDeleteClick(ActionEvent event) {
        //todo
    }
    
    @FXML
    private void handleFilterClick(ActionEvent event) {
        //todo
    }
    
    public ObservableList<Product> getProducts() {
        // setup obsefvable list
        ObservableList<Product> products = FXCollections.observableArrayList();
        // to do - read from sql and loop through list
        // for testing, adds one item manually
        products.add(new Product(1001,"test product name", 3.5, 9.49, 125));
        
        // return newly created observable list
        return products;
    }
}
