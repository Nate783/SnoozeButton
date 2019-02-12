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
import javafx.scene.control.TextField;
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


    
   void handleDelBtn(ActionEvent event) {
        
    }

    @FXML
    void handleDelCancelButton(ActionEvent event) {
        DelStage.close();
    }
 
    
    public void setDelStage(Stage delStage){
       this.DelStage = delStage;
       
    }
}
