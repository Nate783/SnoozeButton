/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.io.IOException;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
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
    private void handleModifyClick(ActionEvent event) throws SQLException {
        if (invTable.getSelectionModel().getSelectedItem() != null) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Tracker.class.getResource("ModifyInventoryItem.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modify Inventory Item");
                modifyStage.initModality(Modality.WINDOW_MODAL);
                Window primaryStage = null;
                modifyStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                modifyStage.setScene(scene);

                // Send the product into the controller.
                ModifyInventoryItemController controller;
                controller = loader.getController();
                controller.setModifyStage(modifyStage);
                controller.initData(invTable.getSelectionModel().getSelectedItem());


                // Show the dialog and wait until the user closes it
                modifyStage.showAndWait();

            } catch (IOException e) {
                System.err.println(e);
            } finally {
                invTable.setItems(getProducts());
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection required");
            alert.setHeaderText(null);
            alert.setContentText("You must select an item to modify.");
            alert.showAndWait();
        }
        
        
    }
    
    @FXML
    private void handleDeleteClick(ActionEvent event) throws SQLException{  
        
        try {
            // move all the fxml loading java code from Tracker.java to here. 
            
            // just before showing the dialog, add a new line which calls the 
            // initData method in DeleteController.java. You can use 
            // "invTable.getSelectionModel().getSelectedItem()" to get the 
            // selected item while invoking the method. 
            
            // Show the dialog and wait until the user closes it
            
            
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            // after showing the dialog, refresh the table
            invTable.setItems(getProducts());
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
        catch(SQLException e){
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
