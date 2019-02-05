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
/**
 *
 * @author Wakhiel
 */
public class Delete {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button invDel;

    @FXML
    private Button invDelCancel;
    
    private Stage DelStage;
    private boolean handleDelBtn;

    
   void handleDelBtn(ActionEvent event) {

    }

    @FXML
    void handleDelCancelButton(ActionEvent event) {

    }
    @FXML
    void initialize() {
        assert invDel != null : "fx:id=\"invBtnAdd\" was not injected: check your FXML file 'AddInventory.fxml'.";
        assert invDelCancel != null : "fx:id=\"invBtnCancel\" was not injected: check your FXML file 'AddInventory.fxml'.";

    }
    

    public boolean handleDelBtn() {
       return handleDelBtn;
    }
    
    public void setAddStage(Stage addStage){
       this.DelStage = DelStage; 
    }
}
