package tracker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilterButtonController implements Initializable {
    @FXML
    private ResourceBundle resources;
    @FXML
    private CheckBox NameBox;
    @FXML
    private CheckBox CostBox;
    @FXML
    private CheckBox PriceBox;
    @FXML
    private CheckBox QuantityBox;
    @FXML
    private ChoiceBox<?> NameChoice;
    @FXML
    private ChoiceBox<?> CostChoice;
    @FXML
    private ChoiceBox<?> PriceChoice;
    @FXML
    private ChoiceBox<?> QuantityChoice;
    @FXML
    private TextField NameText;
    @FXML
    private TextField CostText;
    @FXML
    private TextField PriceText;
    @FXML
    private TextField QuantityText;
    @FXML
    
    private Stage filterStage;
    
    
    public void setFilterStage(Stage FilterStage){
        this.filterStage=FilterStage;
    }
    
    public void resetButtonPushed(){
        NameBox.setSelected(false);
        CostBox.setSelected(false);
        PriceBox.setSelected(false);
        QuantityBox.setSelected(false);
        NameChoice.setValue(null);
        CostChoice.setValue(null);
        PriceChoice.setValue(null);
        QuantityChoice.setValue(null);
        NameText.setText("");
        CostText.setText("");
        PriceText.setText("");
        QuantityText.setText("");
    }
    public void applyButtonPushed(){
        //todo
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
