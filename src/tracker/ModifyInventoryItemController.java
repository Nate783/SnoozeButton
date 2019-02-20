/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author obideuce
 */
public class ModifyInventoryItemController implements Initializable {
    private Product selectedProduct;
    private Stage modifyStage;
    
    
    @FXML
    private TextField txtID, txtName, txtCost, txtPrice, txtQty;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
              
    }    
    

    
    @FXML
    private void handleSaveClick(ActionEvent event) throws SQLException{
        // when the save button is clicked pass changes to a SQL statement.
        // you don't need to refresh the table, because closing this window will
        // always do that.
        int uid, qty;
        double cost,price;
        String name;
        
        uid = Integer.parseInt(txtID.getText()); 
        name = txtName.getText();
        cost = Double.parseDouble(txtCost.getText());
        price = Double.parseDouble(txtPrice.getText());
        qty = Integer.parseInt(txtQty.getText());
        
        //Creating the object and filling it with the necessary items
        Product modified = new Product(uid, name, cost, price, qty);
        Tracker.saveProdToDatabase(modified);

        
        // after modifying the item, now you need to close the window.
        modifyStage.close();
    }
    
    @FXML
    private void handleCancelClick(ActionEvent event) {
        modifyStage.close();
    }
    
    // create a new method called initData
    // it should expect to receive a Product object
    public void initData (Product product) {
        selectedProduct = product;
        
        // prefill all text boxes and quantity pickers
        txtID.setText(selectedProduct.getId().toString());
        txtName.setText(selectedProduct.getName().toString());
        txtCost.setText(selectedProduct.getCost().toString());
        txtPrice.setText(selectedProduct.getPrice().toString());
        txtQty.setText(selectedProduct.getQtyOnHand().toString());
                
    }
    
    public void setModifyStage(Stage modifyStage) throws SQLException {
        this.modifyStage = modifyStage;
       
    }


}
