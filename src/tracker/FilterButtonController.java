package tracker;
import java.sql.SQLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    @FXML
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
        Add2.setVisible(false);
        Add2.setDisable(true);
        
        
    }
    @FXML
    public void handleCancelButton(){
        addStage.close();
    }
    @FXML
    public void handleApplyButton()throws SQLException{
        //input validation
        
        //Detect filters and set the sql statement to match selected filters  
        String temp, P1, P2, P3;
        switch(filterCount){
            case 0:
                //sql statement (display all)
            Tracker.sqlStatement = "SELECT * FROM products";
            case 1: 
                //input validation
                 if (FilterText1.getText().matches("[-A-Za-z0-9.\\s]*$")){
                //sql statement
                if(FilterBox1.getValue().equals("Name"))
            temp="SELECT * FROM products WHERE "+FilterBox1.getValue()+" "+ModifierBox1.getValue()+" '%"+FilterText1.getText()+"%\'";
                else{
            temp="SELECT * FROM products WHERE "+FilterBox1.getValue()+" "+ModifierBox1.getValue()+" "+FilterText1.getText();                    
                }
                temp=temp.replaceAll("Item Price","price");
            Tracker.sqlStatement=temp.replaceAll("Contains", "LIKE");
                break;
                 }
                 else{
                     Alert alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Filter Error");
                     alert.setHeaderText("Invalid Input");
                     alert.setContentText("Filter Text MUST be alphanumeric");
                     alert.showAndWait();
                     break;
                 }
            case 2:
                //input validation
                
                if (FilterText1.getText().matches("[-A-Za-z0-9.\\s]*$")&&FilterText2.getText().matches("[-A-Za-z0-9.\\s]*$")){
                //sql statement
                //set first part of string
                if(FilterBox1.getValue().equals("Name"))
                    P1 = FilterBox1.getValue()+" "+ModifierBox1.getValue()+" '%"+FilterText1.getText()+"%'";
                else{
                    P1 = FilterBox1.getValue()+" "+ModifierBox1.getValue()+" "+FilterText1.getText();
                }
                if(FilterBox2.getValue().equals("Name"))
                    P2 = FilterBox2.getValue()+" "+ModifierBox2.getValue()+" '%"+FilterText2.getText()+"%'";
                else{
                    P2 = FilterBox2.getValue()+" "+ModifierBox2.getValue()+" "+FilterText2.getText();
                }   
                P1=P1.replaceAll("Item Price", "price");
                P2=P2.replaceAll("Item Price", "price");
                temp = "SELECT * FROM products WHERE "+P1+" "+AndOr1.getValue()+" "+P2;
                Tracker.sqlStatement=temp.replaceAll("Contains", "LIKE");
            break;
                }
                 else{
                     Alert alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Filter Error");
                     alert.setHeaderText("Invalid Input");
                     alert.setContentText("Filter Text MUST be alphanumeric");
                     alert.showAndWait();
                     break;
                 }            
            case 3: 
                //input validation
                if (FilterText1.getText().matches("[-A-Za-z0-9.\\s]*$")&&FilterText2.getText().matches("[-A-Za-z0-9.\\s]*$")&&FilterText3.getText().matches("[-A-Za-z0-9.\\s]*$")){
                //sql statement
                if (FilterText1.getText().matches("[-A-Za-z0-9.\\s]*$")&&FilterText2.getText().matches("[-A-Za-z0-9.\\s]*$")){
                //set first part of string
                if(FilterBox1.getValue().equals("Name"))
                    P1 = FilterBox1.getValue()+" "+ModifierBox1.getValue()+" '%"+FilterText1.getText()+"%'";
                else{
                    P1 = FilterBox1.getValue()+" "+ModifierBox1.getValue()+" "+FilterText1.getText();
                }
                if(FilterBox2.getValue().equals("Name"))
                    P2 = FilterBox2.getValue()+" "+ModifierBox2.getValue()+" '%"+FilterText2.getText()+"%'";
                else{
                    P2 = FilterBox2.getValue()+" "+ModifierBox2.getValue()+" "+FilterText2.getText();
                }   
                if(FilterBox3.getValue().equals("Name"))
                    P3 = FilterBox3.getValue()+" "+ModifierBox3.getValue()+" '%"+FilterText3.getText()+"%'";
                else{
                    P3 = FilterBox3.getValue()+" "+ModifierBox3.getValue()+" "+FilterText3.getText();
                }   
                P1=P1.replaceAll("Item Price", "price");
                P2=P2.replaceAll("Item Price", "price");
                P3=P3.replaceAll("Item Price", "price");
                temp = "SELECT * FROM products WHERE "+P1+" "+AndOr1.getValue()+" "+P2+" "+AndOr2.getValue()+" "+P3;
                Tracker.sqlStatement=temp.replaceAll("Contains", "LIKE");
                break;
            }
                }
                 else{
                     Alert alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Filter Error");
                     alert.setHeaderText("Invalid Input");
                     alert.setContentText("Filter Text MUST be alphanumeric");
                     alert.showAndWait();
                     break;
                 } 
        
    }
        addStage.close();
    }
    @FXML
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
    @FXML
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
