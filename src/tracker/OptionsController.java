/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author obideuce
 */
public class OptionsController implements Initializable {

    @FXML
    private Tab secTab, connTab;
    @FXML
    private Button secSave;
    @FXML
    private TextField currentpin, newPin1, newPin2;

    @FXML
    private TextField sqlAddress, sqlUser, sqlPass;
    @FXML
    private Button connSave, connCancel;
    
    private Stage optionsStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // since the default tab is the PIN one, do nothing
    }    

    public void setOptionsStage(Stage optionsStage) {
        this.optionsStage = optionsStage;

    }
    
    @FXML
    private void onSecSave(ActionEvent event) {
        // load the binary file and check the current PIN
        // also validate the new PINS match
        // if all valid, try to change the PIN
        
        // if not all valid, provide non-leaky error
        
    }

   

    @FXML
    private void onConnSave(ActionEvent event) {
        // validate format of hostname
        // validate that username and password filled in
        
        if (sqlAddress.getText().matches("^(([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])\\.)*([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])(:[0-9]+)\\/(\\w|\\d)*$") 
                && !sqlUser.getText().trim().isEmpty() 
                && !sqlPass.getText().trim().isEmpty() 
            )
        {
            // IF valid, then lets write to properties
            // defint variables for use later
            Properties prop = new Properties();
            OutputStream output = null;

            // this can throw an exception, so we'll wrap in a try-catch
            try {
                // initialize output variable
		output = new FileOutputStream("sql.config.properties");

		// set the properties value
		prop.setProperty("server", sqlAddress.getText().trim() );
		prop.setProperty("dbuser", sqlUser.getText().trim() );
		prop.setProperty("dbpassword", sqlPass.getText().trim() );

		// save properties to project root folder
		prop.store(output, null);
                output.close();
                output = null;
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Connection Properties Saved");
                alert.setHeaderText(null);
                alert.setContentText("Your changes have been saved. Please restart the application.");
                alert.showAndWait();
                
            } catch (IOException io) {
		io.printStackTrace();
            } 
            
            
            
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setHeaderText(null);
            alert.setContentText("One or more of your entries is invalid. \n\n"
                    + "The server address must be in the following format: \n "
                    + "\"hostname:port/databasename\" or \"ip.add.ress:port/databasename\" \n\n"
                    + "Additionally, the username and password must not be blank");

            alert.showAndWait();
        }
        
        // after saving, clear the password field back out
        sqlPass.setText("");
        
    }

    @FXML
    private void onConnCancel(ActionEvent event) {
        // warn user that all changes will be discarded
        
        // if okay, close window
    }

    @FXML
    private void onTabChange(Event event) {
        // check if the new tab is the connection tab
        if (connTab.isSelected()) {
            // open the connection properties file
            
            // define variables for later use
            Properties prop = new Properties();
            InputStream input = null;

            // wrap file read in a try-catch
            try {
                // initialize the variable
		input = new FileInputStream("sql.config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and fill in the text fields
		sqlAddress.setText(prop.getProperty("server"));
		sqlUser.setText(prop.getProperty("dbuser"));

            } catch (IOException ex) {
		ex.printStackTrace();
            } finally {
		if (input != null) {
                    try {
			input.close();
                    } catch (IOException e) {
			e.printStackTrace();
                    }
		}
            }
        }
        
            // if so, fetch current data for all fields
    }
    
}
