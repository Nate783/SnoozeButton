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
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        int oldPin, intNewPin;
        Boolean match,valid,unique;
        
        //initialize variables
        match = false;
        valid = false;
        unique = false;
        oldPin = 1;
        intNewPin = 1;
        
        // first, check that the pin is valid
        if (newPin1.getText().matches("^\\d{4,8}$")) {
            valid = true;
        }

        //now check that the two new pins match
        // if so, set the new PIN variable
        if (valid && Objects.equals(newPin1.getText(), newPin2.getText())) {
            intNewPin = Integer.parseInt(newPin1.getText());
            match = true;
        }
        
        // If valid and a match, now check that the new PIN is not the same as the old one
        if (match && valid) {
            
            // open the security properties file
            // define variables for later use
            Properties prop = new Properties();
            InputStream input = null;
            
            // wrap file read in a try-catch
            try {
                // initialize the variable
		input = new FileInputStream("pin.config.properties");

		// load a properties file
		prop.load(input);

		// get the property value and fill in the text fields
		oldPin = Integer.parseInt(prop.getProperty("secret"));
                if (!Objects.equals(oldPin, intNewPin)) {
                    unique = true;
                }
		
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
        
        // if all tests pass, set the new PIN 
        if (match && unique && valid) {
            Properties prop = new Properties();
            OutputStream output = null;
            try {
                // initialize output variable
		output = new FileOutputStream("pin.config.properties");

		// set the properties value
		prop.setProperty("secret", String.valueOf(intNewPin) );
		

		// save properties to project root folder
		prop.store(output, null);
                output.close();
                output = null;
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("New PIN Saved");
                alert.setHeaderText(null);
                alert.setContentText("Your changes have been saved. This Window will now close. ");
                alert.showAndWait();
                ((Node)(event.getSource())).getScene().getWindow().hide();
                
            } catch (IOException io) {
		io.printStackTrace();
            } 
            
        } else {
            // if not all valid, provide non-leaky error
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setHeaderText(null);
            alert.setContentText("There is a problem with your entry. At least one of the following has failed:\n"
                    + "1. The new PINs did not match.\n"
                    + "2. The current PIN was incorrect.\n"
                    + "3. The new PIN was the same as the old PIN.\n"
                    + "4. Your new PIN was not 4-8 numbers. ");

            alert.showAndWait();
            
        }
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to discard your changes?\n"
                + "\nClick OK to Cancel your edits.\n"
                + "Click Cancel to return to the options window.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            // if okay, close window
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    
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
