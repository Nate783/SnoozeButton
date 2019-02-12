/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Tracker extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Checks if the product ID of a passed product is already in use on the database.
     * If so, it calls the DBUpdate method, if new it calls the DBInsert method.
     * @param p A product object
     * @throws SQLException 
     * @author Dale Kauffman
     */
    public static void saveProdToDatabase(Product p) throws SQLException {
        // Define  variables to hold connection state and for uid check
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        Boolean exists = false;
        
        try{
            // connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");
            
            // create statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT  ID FROM tracker.products WHERE ID = " + p.getId() );
            
            // if statement has results, set the boolean
            exists = resultSet.next();
                                    
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            // close all connections
            if (conn != null)
                conn.close();
            if(statement != null)
                statement.close();
            if(resultSet != null)
                resultSet.close();
        }
        
        // if UID already exists, then do a modify
        if (exists == true) {
            dbProductUpdate(p);            
        }
        
        // if UID does not exist, do an insert
        if (exists == false) {
            dbProductInsert(p);
        }
        
    }
    
    public static void dbProductUpdate(Product p) throws SQLException {
        Connection c2 = null;
        PreparedStatement ps = null;
        
        try{
            //1.  connect to the DB
            c2 = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE tracker.products SET Name = ?, Cost = ?, Price =?, Quantity = ? WHERE ID = " + p.getId() ;

            //3. prepare the query against SQL injection
            ps = c2.prepareStatement(sql);

            //5. bind the parameters
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getCost());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getQtyOnHand());


            //6. run the command on the SQL server
            ps.executeUpdate();
            ps.close();
            
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (c2 != null)
                c2.close();
            if (ps != null)
                ps.close();
        }
    }
    
    public static void dbProductInsert(Product p) throws SQLException {
        Connection c3 = null;
        PreparedStatement ps2 = null;
        
            try{
                //1.  connect to the DB
                c3 = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

                //2.  create a String that holds our SQL update command with ? for user inputs
                String sql = "INSERT INTO tracker.products (ID, `Name`, Cost, Price, Quantity) VALUES (?, ?, ?, ?, ?);";

                //3. prepare the query against SQL injection
                ps2 = c3.prepareStatement(sql);

                //5. bind the parameters
                ps2.setInt(1, p.getId());
                ps2.setString(2, p.getName());
                ps2.setDouble(3, p.getCost());
                ps2.setDouble(4, p.getPrice());
                ps2.setInt(5, p.getQtyOnHand());
                               

                //6. run the command on the SQL server
                ps2.executeUpdate();
                ps2.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally {
                if (c3 != null)
                    c3.close();
                if (ps2 != null)
                    ps2.close();
            }
    }
    
    public static void AddInventoryController() throws Exception {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Tracker.class.getResource("AddInventory.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage addStage = new Stage();
            addStage.setTitle("Add Inventory");
            addStage.initModality(Modality.WINDOW_MODAL);
            Window primaryStage = null;
            addStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            addStage.setScene(scene);

            // Set the person into the controller.
            AddInventoryController controller;
            controller = loader.getController();
            controller.setAddStage(addStage);
            //controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            addStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
  }
    public static void Delete() throws Exception {
        try {
             // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Tracker.class.getResource("DeleteInventory.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage delStage = new Stage();
            delStage.setTitle("Delete Item");
            delStage.initModality(Modality.WINDOW_MODAL);
            Window primaryStage = null;
            delStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            delStage.setScene(scene);

            // Set the person into the controller.
            Delete delcontroller;
            delcontroller = loader.getController();
            delcontroller.setDelStage(delStage);
            //controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            delStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
  } 
}
