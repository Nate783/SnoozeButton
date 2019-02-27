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
    

    public boolean isNumeric(String s) {  
        return s != null && s.matches("^[+-]?(([1-9]\\d*)|0)(\\.\\d+)?");  
        }  
    public boolean isInteger(String s){
        return s != null && s.matches("^[0-9]\\d*$");
       }
    @FXML
    private void handleSaveClick(ActionEvent event) throws SQLException{
        // when the save button is clicked pass changes to a SQL statement.
        // you don't need to refresh the table, because closing this window will
        // always do that.
        
        int uid, qty = 0;
        double cost = 0,price = 0;
        String name, sCost, sPrice, sQty;
        boolean nameValid, costValid, priceValid, qtyValid;
        
        uid = Integer.parseInt(txtID.getText()); 
        name = txtName.getText();
        if(name.matches("^[a-zA-Z]*$")){
            nameValid = true;
        }
        else{
            nameValid = false;
        }
        
        sCost = txtCost.getText();
        if(isNumeric(sCost)){
        cost = Double.parseDouble(txtCost.getText());
        costValid = true;
        }
        else{
            System.err.println("Enter a valid number for the cost");
            costValid = false;
        }
        
        sPrice = txtPrice.getText();
        if(isNumeric(sPrice)){
        price = Double.parseDouble(txtPrice.getText());
        priceValid = true;
        }
        else{
            System.err.println("Enter a valid number for the price");
            priceValid = false;
        }
        sQty = txtQty.getText();
        if(isInteger(sQty)){
        qty = Integer.parseInt(txtQty.getText());
        qtyValid = true;
        }
        else{
            System.err.println("Enter a valid number for the quantity");
            qtyValid = false;
        }
        
        
        //Creating the object and filling it with the necessary items
        if(nameValid && costValid && priceValid && qtyValid){
        Product modified = new Product(uid, name, cost, price, qty);
        Tracker.saveProdToDatabase(modified);

        
        // after modifying the item, now you need to close the window.
        modifyStage.close();
    }
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
