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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AddInventoryController {

    @FXML
    private Button invBtnAdd, invBtnCancel;

    private Stage addStage;

    @FXML
    private TextField labelID, productName, cost, quantity, salesPrice;

    @FXML
    private void handleAddBtn(ActionEvent event) throws SQLException {

        // Creating the variables we'll need to check if the input is valid and to convert string to int/double
        String pName, pCost, pPrice, pQty, nameError, costError, priceError, qtyError;
        double cos = Double.parseDouble(cost.getText());
        double pri = Double.parseDouble(salesPrice.getText());
        int qty = Integer.parseInt(quantity.getText());
        boolean nameValid, costValid, priceValid, qtyValid;

        //Checking if the users product name entry is valid
        pName = productName.getText();
        if (pName.matches("[A-Za-z]+") && pName != null ) {
            nameValid = true;
            nameError = "";
        } else {
            nameValid = false;
            nameError = "Product name must consist of characters A-Z";
        }

        // Checking if the users product cost entry is valid
        pCost = cost.getText();
        if (isNumeric(pCost) && pri > cos) {
            costValid = true;
            costError = "";
        } else {
            costValid = false;
            costError = "Cost must be less than price and not negative.";
        }

        // Checking if the users product price entry is valid
        pPrice = salesPrice.getText();
        if (isNumeric(pPrice) && pri > cos) {
            priceValid = true;
            priceError = "";
        } else {
            priceValid = false;
            priceError = "Sales price must be greater than cost";
        }

        // Checking if the users product quantity is valid
        pQty = quantity.getText();
        if (isNumeric(pQty) && qty > 0) {
            qtyValid = true;
            qtyError = "";
        } else {
            qtyValid = false;
            qtyError = "Quantity must be greater than 0";
        }

        // Check if all entries are valid, if yes create object and save to database, otherwise display error
        if (nameValid && costValid && priceValid && qtyValid && pri>cos) {
            Product p = new Product(nextID(), productName.getText(), cos, pri, qty);
            Tracker.saveProdToDatabase(p);
            addStage.close();
        } 
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Looks like a few fields need to be corrected.");
            alert.setContentText(nameError + "\n" + costError + "\n" + priceError + "\n" + qtyError );
            alert.showAndWait();
        }

    }

    //Boolean to check the validity of users numeric entries
    public boolean isNumeric(String p) {
        return p != null && p.matches("^[+]?(([1-9]\\d*)|0)(\\.\\d+)?");
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
