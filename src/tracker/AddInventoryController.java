/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    void handleAddBtn(ActionEvent event) throws SQLException {

        //Creating variables that convert string to double/int
        double cos = Double.parseDouble(cost.getText());
        double pri = Double.parseDouble(salesPrice.getText());
        int qty = Integer.parseInt(quantity.getText());

        //Creating the object and filling it with the necessary items
        Product p = new Product(nextID(), productName.getText(), cos, pri, qty);

        // Sending the object to the database
        //   p.saveProdToDatabase();
        //testing
        //  System.out.println("asd/n" + productName.getText()+cos+pri+qty);
        
        
//        Connection conn = null;
//        
//        String id = labelID.getText();
//        String name = productName.getText();
//        String cos = cost.getText();
//        String quant = quantity.getText(); 
//        String salePrice = salesPrice.getText();
//        
//        System.out.print(id + " " + name + " " + cos + " " + quant + " " + salePrice);
//        
//        // connect to the DB
//        conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG"); 
    }

    @FXML
    void handleCancelButton(ActionEvent event) {
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
