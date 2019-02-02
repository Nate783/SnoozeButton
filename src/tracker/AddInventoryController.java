/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AddInventoryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button invBtnAdd;

    @FXML
    private Button invBtnCancel;
    
    private Stage addStage;
    private boolean handleAddBtn;

    
    void handleAddBtn(ActionEvent event) {

    }

    @FXML
    void handleCancelButton(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert invBtnAdd != null : "fx:id=\"invBtnAdd\" was not injected: check your FXML file 'AddInventory.fxml'.";
        assert invBtnCancel != null : "fx:id=\"invBtnCancel\" was not injected: check your FXML file 'AddInventory.fxml'.";

    }
    

    public boolean handleAddBtn() {
       return handleAddBtn;
    }
    
    public void setAddStage(Stage addStage){
       this.addStage = addStage; 
    }


}



