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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author obideuce
 */
public class ModifyInventoryItemController implements Initializable {
    private Product selectedProduct;
    private Stage modifyStage;
    
    private TextField mProductID;
    private TextField mProductName;
    private TextField mProductCost;
    private TextField mSalesPrice;
    private TextField mQuantity;
    private Button mSaveBtn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    

    void handleGetProduct(ActionEvent event) throws SQLException{
        int prodID = Integer.parseInt(mProductID.getText());
    }
    
    void handleSaveBtn(ActionEvent event) throws SQLException{
        // when the save button is clicked pass changes to a SQL statement.
        // you don't need to refresh the table, because closing this window will
        // always do that.
        
       
        String prodName = mProductName.getText();
        int prodCost = Integer.parseInt(mProductCost.getText());
        int salesPrice = Integer.parseInt(mSalesPrice.getText());
        int quantity = Integer.parseInt(mQuantity.getText());
        
        
       
        // after modifying the item, now you need to close the window.
    
    // create a new method called initData
    // it should expect to receive a Product object
//    public void initData (Product product) {
        //selectedProduct = product;
        
        // prefill all text boxes and quantity pickers
        
    }
    
    public void setModifyStage(Stage modifyStage) throws SQLException {
        this.modifyStage = modifyStage;
       
    }
    
}
