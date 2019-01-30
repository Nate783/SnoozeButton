/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package tracker;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    private SimpleIntegerProperty Id, qtyOnHand;
    private SimpleStringProperty name;
    private SimpleDoubleProperty cost, price;

    public Product(int id, String name, double cost, double price, int qtyOnHand) {
        Id = new SimpleIntegerProperty(id);
        this.qtyOnHand = new SimpleIntegerProperty(qtyOnHand);
        this.name = new SimpleStringProperty(name);
        this.cost = new SimpleDoubleProperty(cost);
        this.price = new SimpleDoubleProperty(price);
    }   

    public Integer getId() {
        return Id.get();
    }

    public String getName() {
        return name.get();
    }

    public Double getCost() {
        return cost.get();
    }

    public Double getPrice() {
        return price.get();
    }
    
    public Integer getQtyOnHand() {
        return qtyOnHand.get();
    }

    public void setId(SimpleIntegerProperty Id) {
        this.Id = Id;
    }
   
    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    
    public void setCost(SimpleDoubleProperty cost) {
        this.cost = cost;
    }

    public void setPrice(SimpleDoubleProperty price) {
        this.price = price;
    }
    
    public void setQtyOnHand(SimpleIntegerProperty qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }  
    
    
    public void saveToDatabase() {
        // TO DO
    }
    
}
