
/*
 * CSI 2999 - Sophomore Project
 * SBTracker
 */
package tracker;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import static tracker.Tracker.*;
import java.text.DecimalFormat;
import javafx.application.Platform;
import javafx.scene.text.Font;
import javafx.scene.control.MenuItem;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button invBtnAddToInventory, invBtnModify, invBtnDelete, invBtnFilters;

    @FXML
    private TableView<Product> invTable;
    @FXML
    private TableColumn<Product, Integer> invColProdUID;
    @FXML
    private TableColumn<Product, String> invColProdName;
    @FXML
    private TableColumn<Product, Double> invColProdCost;
    @FXML
    private TableColumn<Product, Double> invColProdPrice;
    @FXML
    private TableColumn<Product, Integer> invColProdQty;
    @FXML
    private Button invBtnFiltersReset;
    @FXML
    private Label totalCpst;
    @FXML
    private Label totalSale;
    @FXML
    private Label totalStock;
    @FXML
    private Button lowStock;
    @FXML
    private Tab tabReport;
    @FXML
    private Tab tabInventory;
    @FXML
    private Font x1;
    @FXML
    private Font x2;
    @FXML
    private Font x3;
    @FXML
    private MenuItem menuBtn;
    @FXML
    private MenuItem menuItem;
    @FXML
    private MenuItem menuAbout;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // prepare columns in table
        invColProdUID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        invColProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        invColProdCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        invColProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        invColProdQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        // since the inventory is the default tab, go ahead and get it's data
        try {
            invTable.setItems(getProducts());

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @FXML
    private void handleAddClick(ActionEvent event) throws SQLException {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Tracker.class.getResource("AddInventory.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage addStage = new Stage();

            addStage.setTitle("Add Inventory");
            addStage.initModality(Modality.WINDOW_MODAL);
            addStage.getIcons().add(new Image(getClass().getResourceAsStream("images/icons8-add-property-48.png")));
            Window primaryStage = null;
            addStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            addStage.setScene(scene);

            // Set the person into the controller.
            AddInventoryController controller;
            controller = loader.getController();
            controller.setAddStage(addStage);

            // Show the dialog and wait until the user closes it
            addStage.showAndWait();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            invTable.setItems(getProducts());
        }
    }

    @FXML
    private void handleModifyClick(ActionEvent event) throws SQLException {
        if (invTable.getSelectionModel().getSelectedItem() != null) {
            try {
                // Load the fxml file and create a new stage for the popup dialog.
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Tracker.class.getResource("ModifyInventoryItem.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage modifyStage = new Stage();
                modifyStage.setTitle("Modify Inventory Item");
                modifyStage.getIcons().add(new Image(getClass().getResourceAsStream("images/icons8-edit-property-48.png")));

                modifyStage.initModality(Modality.WINDOW_MODAL);
                Window primaryStage = null;
                modifyStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                modifyStage.setScene(scene);

                // Send the product into the controller.
                ModifyInventoryItemController controller;
                controller = loader.getController();
                controller.setModifyStage(modifyStage);
                controller.initData(invTable.getSelectionModel().getSelectedItem());

                // Show the dialog and wait until the user closes it
                modifyStage.showAndWait();

            } catch (IOException e) {
                System.err.println(e);
            } finally {
                invTable.setItems(getProducts());
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection required");
            alert.setHeaderText(null);
            alert.setContentText("You must select an item to modify.");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleDeleteClick(ActionEvent event) throws SQLException {
        if (invTable.getSelectionModel().getSelectedItem() != null) {
            try {

                // Show the dialog and wait until the user closes it
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Tracker.class.getResource("DeleteInventory.fxml"));
                AnchorPane page = (AnchorPane) loader.load();

                // Create the dialog Stage.
                Stage delStage = new Stage();
                delStage.setTitle("Delete Item");
                delStage.getIcons().add(new Image(getClass().getResourceAsStream("images/icons8-delete-document-48.png")));
                delStage.initModality(Modality.WINDOW_MODAL);
                Window primaryStage = null;
                delStage.initOwner(primaryStage);
                Scene scene = new Scene(page);
                delStage.setScene(scene);

                // Set the product into the controller.
                DeleteController controller;
                controller = loader.getController();
                controller.setDelStage(delStage);
                controller.initData(invTable.getSelectionModel().getSelectedItem());

                // Show the dialog and wait until the user closes it
                delStage.showAndWait();

            } catch (Exception e) {
                System.err.println(e);
            } finally {
                // after showing the dialog, refresh the table
                invTable.setItems(getProducts());
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Selection required");
            alert.setHeaderText("No item selected!");
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleFilterClick(ActionEvent event) throws SQLException {
        try {
            // @David - move filter window opening here.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Tracker.class.getResource("FilterButton.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage filterStage = new Stage();
            filterStage.setTitle("Set Filters");
            filterStage.initModality(Modality.WINDOW_MODAL);
            Window primaryStage = null;
            filterStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            filterStage.setScene(scene);

            FilterButtonController controller;
            controller = loader.getController();
            controller.setFilterStage(filterStage);
            //controller.setPerson(person);

            filterStage.showAndWait();

        } catch (Exception e) {
            System.err.println(e);
        } finally {
            System.out.println(Tracker.sqlStatement);
            invTable.setItems(getProducts());

            // TODO: write if that checks if "sql statement" is modified
            // if not, do nothing, if yes, display reset button
            if (!Objects.equals(sqlStatement, "SELECT * FROM products")) {
                invBtnFiltersReset.setVisible(true);
            }

        }
    }

    @FXML
    private void handleResetClick(ActionEvent event) throws SQLException {
        sqlStatement = "SELECT * FROM products";
        invTable.setItems(getProducts());
        invBtnFiltersReset.setVisible(false);

    }

    public ObservableList<Product> getProducts() throws SQLException {
        // setup observable list
        ObservableList<Product> products = FXCollections.observableArrayList();

        // read from sql and loop through list
        // setup the SQL variables
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // connect to the DB
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            // create the sql statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery(Tracker.sqlStatement);

            // loop through the results and create products for each line
            while (resultSet.next()) {
                products.add(new Product(
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getDouble("Cost"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("Quantity")
                )
                );
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            // clean up
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

        // return newly created observable list
        return products;
    }

    private int getTotalStock() throws SQLException {
        int stockCount = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            // create statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select sum(quantity) as \"Total Quantity\" from products");

            // set the max value
            resultSet.next();
            stockCount = resultSet.getInt("Total Quantity");

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
        return stockCount;
    }

    private String getTotalCost() throws SQLException {
        DecimalFormat df2 = new DecimalFormat("#,###.00"); //2 decimal format
        String cost = "";
        double costValue = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            // create statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select sum(Cost * Quantity) as \"Total Cost\" from products");

            // set the max value
            resultSet.next();
            costValue = resultSet.getDouble("Total Cost");

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
        cost = (df2.format(costValue));
        return cost;
    }

    private String getTotalSale() throws SQLException {
        DecimalFormat df2 = new DecimalFormat("#,###.##"); //2 decimal format
        double saleValue = 0;
        String sales = "";
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            // create statement and execute it
            statement = conn.createStatement();
            resultSet = statement.executeQuery("select sum(Price * Quantity) as \"Total Sale\" from products");

            // set the max value
            resultSet.next();
            saleValue = resultSet.getDouble("Total Sale");

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
        sales = df2.format(saleValue);
        return sales;
    }

    private int getLowStock() throws SQLException {
        int lowStock = 0;
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://157.230.232.127:3306/tracker?zeroDateTimeBehavior=convertToNull", "tracker", "TGhcVxRXf4uVDG");

            // create statement and execute it to find out which items have less than 5 in stock 
            statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT count(quantity) as \"Low Stock\" from products where quantity < 5");

            // Set the number of low stock items equal to lowStock
            resultSet.next();
            lowStock = resultSet.getInt("Low Stock");

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
        //pass the value to the label
        return lowStock;
    }

    @FXML
    private void handleLowStockClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Tracker.class.getResource("LowStockView.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage lowStockStage = new Stage();
            lowStockStage.setTitle("Low Stock Items");
            lowStockStage.initModality(Modality.WINDOW_MODAL);
            Window primaryStage = null;
            lowStockStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            lowStockStage.setScene(scene);

            LowStockViewController controller;
            controller = loader.getController();
            controller.initData();
            controller.setLowStockStage(lowStockStage);

            lowStockStage.showAndWait();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML
    private void handleTabChange(Event event) throws SQLException {
        // when the tabs change, check if the report tab is now selected
        if (tabReport.isSelected()) {
            // if so, go get the total stock data
            totalStock.setText(String.valueOf(getTotalStock()) + " items");
            totalCpst.setText(("$" + getTotalCost()));
            totalSale.setText(("$" + getTotalSale()));
            lowStock.setText(String.valueOf(getLowStock()) + " items");
        }

    }

    @FXML
    private void handleOptionsClick(ActionEvent event) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Tracker.class.getResource("Options.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage optionsStage = new Stage();

            optionsStage.setTitle("Options");
            optionsStage.initModality(Modality.WINDOW_MODAL);
            optionsStage.getIcons().add(new Image(getClass().getResourceAsStream("images/icons8-add-property-48.png")));
            Window primaryStage = null;
            optionsStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            optionsStage.setScene(scene);

            // Set the person into the controller.
            OptionsController controller;
            controller = loader.getController();
            controller.setOptionsStage(optionsStage);

            // Show the dialog and wait until the user closes it
            optionsStage.showAndWait();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @FXML // When the menubar About button is clicked, this handler will execute
    private void handleAboutClick(ActionEvent event) throws SQLException {

        try {
            //This will create a new information alert
            Alert alert = new Alert(AlertType.INFORMATION);
            //This sets the title of your alert box
            alert.setTitle("About");
            //This sets the header of your alert box
            alert.setHeaderText("Tracker 1.0");
            //Create a string of everything you want your alert to display
            String s = "Build Information:\n Version 1.0 \n Date: 2019-04-02\n\n © 2019, Snoozebutton Industries";
            //This sets the content equal to your string
            alert.setContentText(s);
            //This will physically show your alert
            alert.show();
        } catch (Exception e) {
            //If something went wrong print out the error
            System.out.print(e);

        }
    }

    @FXML // When the menubar Close button is clicked, this handler will execute
    private void handleCloseClick(ActionEvent event) {
        //Platform.exit(); kills the application, requires a seperate package import
        Platform.exit();

    }

}
