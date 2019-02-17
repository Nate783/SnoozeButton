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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tracker.Product;
/**
 *
 * @author Wakhiel
 */
public class DeleteController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button invDel;

    @FXML
    private Button invDelCancel;
    
    
    private Stage DelStage;

    
    @FXML
    void handleDelBtn(ActionEvent event) throws SQLException{
        
    }

    @FXML
    void handleDelCancelButton(ActionEvent event) {
        DelStage.close();
    }
 
    
    public void setDelStage(Stage delStage){
       this.DelStage = delStage;
       
    }
}
