/*
 * CSI 2999 - Sophomore Project
 * SBTracker
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
    
    // declare a new variable called selectedProduct that you can use throughout
    // this controller. 
    private Product selectedProduct;
    
    private Stage DelStage;

    
    @FXML
    void handleDelBtn(ActionEvent event) throws SQLException{
        // when the delete button is clicked, take the ID of selected product
        // and pass it to a SQL delete statement.
        // you don't need to refresh the table, because closing this window will
        // always do that.
        Connection conn = null;
        Statement statement = null;
        int q  = 0;
        
        try{
            // connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");
           
           
            // create the sql statement and execute it
            statement = conn.createStatement();
            q = statement.executeUpdate("DELETE FROM tracker.products WHERE ID = " +  selectedProduct.getId());
           
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        finally {
            // clean up
            if(conn!= null){
                conn.close();
            }
            if(statement != null){
                statement.close();
            }
            if(q!= 0){
                q= 0;
            }  
        }
        System.out.println(selectedProduct.getId());
        
        
        
        // after deleting the item, now you need to close the window.
        DelStage.close();
    }
    
    // create a new method called initData
    // it should expect to receive a Product object
    public void initData (Product product) {
        selectedProduct = product;        
    }
    
    @FXML
    void handleDelCancelButton(ActionEvent event) {
        DelStage.close();
    }
 
    
    public void setDelStage(Stage delStage){
       this.DelStage = delStage;
       
    }
}
