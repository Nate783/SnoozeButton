/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author David
 */
public class LowStockViewController implements Initializable {
    @FXML private TableView<Product> lowInvView;
    @FXML private TableColumn<Product, Integer> lowInvColProdUID;
    @FXML private TableColumn<Product, String> lowInvColProdName;
    @FXML private TableColumn<Product, Double> lowInvColProdCost;
    @FXML private TableColumn<Product, Double> lowInvColProdPrice;
    @FXML private TableColumn<Product, Integer> lowInvColProdQty;
    @FXML private Stage lowStage;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lowInvColProdUID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        lowInvColProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        lowInvColProdCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        lowInvColProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        lowInvColProdQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        try {
            lowInvView.setItems(getLowProducts());
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ObservableList<Product> getLowProducts() throws SQLException {
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
            resultSet = statement.executeQuery("SELECT * FROM Products WHERE Quanitiy < 5");
           
   
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
        public void setLowStockStage(Stage lowStockStage){
        this.lowStage = lowStockStage;
    }
    
}
