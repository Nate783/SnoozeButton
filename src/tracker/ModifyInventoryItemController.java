/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author obideuce
 */
public class ModifyInventoryItemController implements Initializable {
    private Product selectedProduct;
    private Stage modifyStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    void handleSaveBtn(ActionEvent event) throws SQLException{
        // when the save button is clicked pass changes to a SQL statement.
        // you don't need to refresh the table, because closing this window will
        // always do that.
        
        
        
        // after modifying the item, now you need to close the window.
    }
    
    // create a new method called initData
    // it should expect to receive a Product object
    public void initData (Product product) {
        selectedProduct = product;
        
        // prefill all text boxes and quantity pickers
        
    }
    
    public void setModifyStage(Stage modifyStage){
        this.modifyStage=this.modifyStage;
    }
    
}
