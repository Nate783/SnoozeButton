/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Tracker extends Application {
    
    public static String sqlStatement = "SELECT * FROM products";
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("images/icons8-scan-stock-96.png")));
        stage.setTitle("Small Business Tracker");
        
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
            conn = DriverManager.getConnection("jdbc:mysql://" + getDBhost(), getDBuser(), getDBpass());
            
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
            c2 = DriverManager.getConnection("jdbc:mysql://" + getDBhost(), getDBuser(), getDBpass());

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
                c3 = DriverManager.getConnection("jdbc:mysql://" + getDBhost(), getDBuser(), getDBpass());

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
    
    public static boolean checkPIN() {
        boolean valid = false;
        int providedPin = 1;
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Authorization Required");
        dialog.setHeaderText(null);
        dialog.setContentText("Please enter your manager PIN:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            providedPin = Integer.parseInt(result.get());
        }
        
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
            if (Objects.equals(Integer.parseInt(prop.getProperty("secret")), providedPin)) {
                valid = true;
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

        return valid;
    }
    
    public static String getDBhost() {
        Properties prop = new Properties();
        InputStream input = null;
        String host = null;

        // wrap file read in a try-catch
        try {
            // initialize the variable
            input = new FileInputStream("sql.config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and fill in the variable
            host = prop.getProperty("server");
            

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
        return host;
    }
    
    public static String getDBuser() {
        Properties prop = new Properties();
        InputStream input = null;
        String user = null;

        // wrap file read in a try-catch
        try {
            // initialize the variable
            input = new FileInputStream("sql.config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and fill in the variable
            user = prop.getProperty("dbuser");
            

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
        return user;
    }
    
    public static String getDBpass() {
        Properties prop = new Properties();
        InputStream input = null;
        String pass = null;

        // wrap file read in a try-catch
        try {
            // initialize the variable
            input = new FileInputStream("sql.config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and fill in the variable
            pass = prop.getProperty("dbpassword");
            

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
        return pass;
    }
}

