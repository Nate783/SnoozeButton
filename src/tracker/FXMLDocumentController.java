/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Button invBtnAddToInventory;
    @FXML
    private TextField invTxtProdName;
    @FXML
    private TextField invTxtProdCost;
    @FXML
    private TextField invTxtProdPrice;
    @FXML
    private TextField invTxtProdQty;
    @FXML
    private TableColumn<?, ?> invColProdUID;
    @FXML
    private TableColumn<?, ?> invColProdName;
    @FXML
    private TableColumn<?, ?> invColProdCost;
    @FXML
    private TableColumn<?, ?> invColProdPrice;
    @FXML
    private TableColumn<?, ?> invColProdQty;
    @FXML
    private Button invBtnDelete;
    @FXML
    private Button invBtnModify;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
