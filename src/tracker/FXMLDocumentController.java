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
         try {
            Delete();
        } catch (Exception e) {
            System.err.println(e);
        } 
    }
    
    @FXML
    private void handleFilterClick(ActionEvent event) {
        try{
            Filters();
        }catch (Exception e){
            System.err.println(e);
        }
    }
    
    public ObservableList<Product> getProducts() throws SQLException {
        // setup observable list
        ObservableList<Product> products = FXCollections.observableArrayList();
        
        // read from sql and loop through list
        
        // setup the SQL variables
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        
        try{
            // connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");
           
           
            // create the sql statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM products");
           
   
            // loop through the results and create products for each line
            while (resultSet.next()) {products.add(new Product(
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
        finally {
            // clean up
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
        
        // return newly created observable list
        return products;
    }
}
