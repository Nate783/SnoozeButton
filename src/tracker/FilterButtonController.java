/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author David
 */
public class FilterButtonController implements Initializable {

    private int filterCount = 0;
    @FXML
    private TextField FilterText1;
    @FXML
    private TextField FilterText2;
    @FXML
    private TextField FilterText3;
    @FXML
    private ComboBox FilterBox1;
    @FXML
    private ComboBox FilterBox2;
    @FXML
    private ComboBox FilterBox3;
    @FXML
    private ComboBox ModifierBox1;
    @FXML
    private ComboBox ModifierBox2;
    @FXML
    private ComboBox ModifierBox3;
    @FXML
    private Button Add1;
    @FXML
    private Button Add2;
    @FXML
    private Button ApplyButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Button ResetButton;
    @FXML
    private ComboBox AndOr1;
    @FXML
    private ComboBox AndOr2;
    private Stage addStage;
    
    public void setFilterStage(Stage filterStage){
        this.addStage = filterStage;
    }
    public void handleResetButton(){
        //empty all boxes and text fields
        ModifierBox3.setValue(null);
        ModifierBox2.setValue(null);
        ModifierBox1.setValue(null);
        FilterBox3.setValue(null);
        FilterBox2.setValue(null);
        FilterBox1.setValue(null);
        FilterText1.setText(null);
        FilterText2.setText(null);
        FilterText3.setText(null);
        
        //remove extra fields and boxes to return to default state
        AndOr1.setVisible(false);
        AndOr1.setDisable(true);
        AndOr2.setVisible(false);
        AndOr2.setDisable(true);
        ModifierBox2.setVisible(false);
        ModifierBox3.setVisible(false);
        FilterBox2.setVisible(false);
        FilterBox3.setVisible(false);
        FilterText2.setVisible(false);
        FilterText3.setVisible(false);
        ModifierBox2.setDisable(true);
        ModifierBox3.setDisable(true);
        FilterBox2.setDisable(true);
        FilterBox3.setDisable(true);
        FilterText2.setDisable(true);
        FilterText3.setDisable(true);
        filterCount=0;
        
        
    }
    public void handleCancelButton(){
        addStage.close();
    }
    public void handleApplyButton()throws SQLException{
        //connect to the database
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try{
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");
            statement = conn.createStatement();
        //detect number of filters and display results     
        switch(filterCount){
            case 0:
                //sql statement (display all)
            resultSet = statement.executeQuery("SELECT * FROM products");
            case 1: 
                //sql statement
            resultSet = statement.executeQuery("SELECT * FROM products WHERE "+FilterBox1.getValue()+" "+ModifierBox1.getValue()+" \'"+FilterText1.getText()+"\'");
                break;
            case 2:
                //sql statement
            resultSet = statement.executeQuery("SELECT * FROM products WHERE "+FilterBox1.getValue()+" "+ModifierBox1.getValue()+" \'"+FilterText1.getText()+"\' "+AndOr1.getValue()+" "+FilterBox2.getValue()+" "+ModifierBox2.getValue()+" \'"+FilterText2.getText()+"\'");
                break;
            case 3: 
                //sql statement
                resultSet = statement.executeQuery("SELECT * FROM products WHERE "+FilterBox1.getValue()+" "+ModifierBox1.getValue()+" \'"+FilterText1.getText()+"\' "+AndOr1.getValue()+" "+FilterBox2.getValue()+" "+ModifierBox2.getValue()+" \'"+FilterText2.getText()+"\' "+AndOr2.getValue()+" "+FilterBox3.getValue()+" "+ModifierBox3.getValue()+" \'"+FilterText3.getText()+"\'");
                break;
        }
        } catch(SQLException e){
            System.err.println(e);
        }
        //close connections
        finally{
        if (conn != null)
        conn.close();
        if(statement != null)
        statement.close();
        if(resultSet != null)
        resultSet.close();
        addStage.close();
        }
    }
    public void handleAdd1(){
        //adds the second line of filters and ensures the correct amount of filters
        FilterBox2.setVisible(true);
        FilterBox2.setDisable(false);
        ModifierBox2.setVisible(true);
        ModifierBox2.setDisable(false);
        FilterText2.setVisible(true);
        FilterText2.setDisable(false);
        Add1.setVisible(false);
        Add1.setDisable(true);
        Add2.setVisible(true);
        Add2.setDisable(false);
        AndOr1.setVisible(true);
        AndOr1.setDisable(false);
        filterCount=2;
    }
    public void handleAdd2(){
        //adds the third line of filters and ensures the correct amount of filters
        FilterBox3.setVisible(true);
        FilterBox3.setDisable(false);
        ModifierBox3.setVisible(true);
        ModifierBox3.setDisable(false);
        FilterText3.setVisible(true);
        FilterText3.setDisable(false);
        Add2.setVisible(false);
        Add2.setDisable(true);
        AndOr2.setVisible(true);
        AndOr2.setDisable(false);
        filterCount=3;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
