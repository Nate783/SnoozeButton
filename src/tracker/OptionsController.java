/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author obideuce
 */
public class OptionsController implements Initializable {

    @FXML
    private Tab secTab, connTab;
    @FXML
    private Button secSave, secCancel;
    @FXML
    private TextField currentpin, newPin1, newPin2;

    @FXML
    private TextField sqlAddress, sqlUser, sqlPass;
    @FXML
    private Button connSave, connCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // since the default tab is the PIN one, do nothing
    }    

    @FXML
    private void onSecSave(ActionEvent event) {
        // load the binary file and check the current PIN
        // also validate the new PINS match
        // if all valid, try to change the PIN
        
        // if not all valid, provide non-leaky error
        
    }

    @FXML
    private void onSecCancel(ActionEvent event) {
        // warn user that all changes will be discarded
        
        // if okay, close window
    }

    @FXML
    private void onConnSave(ActionEvent event) {
        // validate format of hostname
        // validate that username and password filled in
        // if all valid, then save to binary file
        
        // if anything invalid, provide error
    }

    @FXML
    private void onConnCancel(ActionEvent event) {
        // warn user that all changes will be discarded
        
        // if okay, close window
    }

    @FXML
    private void onTabChange(Event event) {
        // check if the new tab is the connection tab
        
            // if so, fetch current data for all fields
    }
    
}
