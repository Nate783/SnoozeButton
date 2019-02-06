/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        
        try {
            // get products for table
            invTable.setItems(getProducts());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void handleAddClick(ActionEvent event) throws SQLException {
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
    
    public ObservableList<Product> getProducts() throws SQLException {
        // setup obsefvable list
        ObservableList<Product> products = FXCollections.observableArrayList();
        // to do - read from sql and loop through list
         Connection conn = null;
         Statement statement = null;
         ResultSet resultSet = null;
        try{
           conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull");
           
           statement = conn.createStatement();
           resultSet = statement.executeQuery("SELECT * FROM products");
           
   
              while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("ID"), 
                        resultSet.getString("Name"), 
                        resultSet.getDouble("Cost"), 
                        resultSet.getDouble("Price"), 
                        resultSet.getInt("Quantity")
                        )
                );
           }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally{
            if(conn!= null){
                conn.close();
            }
            if(statement != null){
                statement.close();
            }
            if(resultSet!= null){
                resultSet.close();
            }
           
        }
        // for testing, adds one item manually
        products.add(new Product(1001,"test product name", 3.5, 9.49, 125));
        products.add(new Product(1002,"ztest product name", 2, 20, 30));
        products.add(new Product(1003,"atest product name", 1, 15, 600));
        
        // return newly created observable list
        return products;
    }
}
