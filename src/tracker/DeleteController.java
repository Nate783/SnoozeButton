/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author Wakhiel
 */
public class DeleteController {

    @FXML
    private Button invDel;

    @FXML
    private Button invDelCancel;
    
    // declare a new variable called selectedProduct that you can use throughout
    // this controller. 
    private Product selectedProduct;
    
    private Stage DelStage;
    
    @FXML
    private Label prodLabel;

    
    @FXML
    void handleDelBtn(ActionEvent event) throws SQLException{
        // when the delete button is clicked, take the ID of selected product
        // and pass it to a SQL delete statement.
        // you don't need to refresh the table, because closing this window will
        // always do that.
        Connection conn = null;
        Statement statement = null;
        int q  = 0;
        
        // first, prompt for and check the PIN. 
        if (Tracker.checkPIN()) {
            try{
                // connect to the DB
                conn = DriverManager.getConnection("jdbc:mysql://" + Tracker.getDBhost(), Tracker.getDBuser(), Tracker.getDBpass());


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
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Entry");
            alert.setHeaderText(null);
            alert.setContentText("You have an entered an incorrect PIN. \n This window will now close.");
            alert.showAndWait();
        }
        
        // after deleting the item, now you need to close the window.
        DelStage.close();
    }
    
    // create a new method called initData
    // it should expect to receive a Product object
    public void initData (Product product) {
        selectedProduct = product;
        prodLabel.setText(selectedProduct.getName());
    }
    
    @FXML
    void handleDelCancelButton(ActionEvent event) {
        DelStage.close();
    }
 
    
    public void setDelStage(Stage delStage){
       this.DelStage = delStage;
       
    }
    
}
