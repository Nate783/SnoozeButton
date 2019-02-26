/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import static java.awt.SystemColor.text;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddInventoryController {

    @FXML
    private Button invBtnAdd;

    @FXML
    private Button invBtnCancel;

    private Stage addStage;

    @FXML
    private TextField labelID;

    @FXML
    private TextField productName;

    @FXML
    private TextField cost;

    @FXML
    private TextField quantity;

    @FXML
    private TextField salesPrice;
    
    @FXML
    private Label errMsgLabel;
    
    @FXML
    private void handleAddBtn(ActionEvent event) throws SQLException {
        // check if input is valid
        //      if yes, do logic
        //      if no, error
        
        try {
            //Creating variables that convert string to double/int
            double cos = Double.parseDouble(cost.getText());
            double pri = Double.parseDouble(salesPrice.getText());
            int qty = Integer.parseInt(quantity.getText());

            //Creating the object and filling it with the necessary items
            Product p = new Product(nextID(), productName.getText(), cos, pri, qty);

            // Sending the object to the database
            Tracker.saveProdToDatabase(p);

            // close window
            addStage.close();
        } 
        
        catch (Exception e) {
            errMsgLabel.setText("Please fill in all fields");
        } 
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        errMsgLabel.setText("asd");
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
        addStage.close();
    }

    public void setAddStage(Stage addStage) throws SQLException {
        this.addStage = addStage;
        labelID.setText(Integer.toString(nextID()));

    }

    public static int nextID() throws SQLException {

        int next = 0;
        // Define  variables to hold connection state and for uid check
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            // create statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT max(ID)+1 as \"New ID\" FROM products");

            // set the max value
            resultSet.next();
            next = resultSet.getInt("New ID");

        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            // close all connections
            if (conn != null) {
                conn.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return next;
    }

}
