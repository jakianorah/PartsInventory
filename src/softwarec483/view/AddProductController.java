/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import softwarec483.Inventory;
import softwarec483.model.Inhouse;
import softwarec483.model.Outsourced;
import softwarec483.model.Part;
import softwarec483.model.Product;

/**
 * FXML Controller class
 *
 * @author jakianorah
 */
public class AddProductController {
    private Inventory inventory;
    private Product product;
    @FXML public TextField searchPartsField;
    @FXML public TextField productIDField;
    @FXML public TextField productNameField;
    @FXML public TextField productInventoryField;
    @FXML public TextField productCostField;
    @FXML public TextField productMinField;
    @FXML public TextField productMaxField;
    
    //parts table with original data from parts table in main screen
    @FXML private TableView<Part> partsInventory; 
    @FXML private TableColumn<Part, Number> inventoryIDColumn;
    @FXML private TableColumn<Part, String> inventoryNameColumn;
    @FXML private TableColumn<Part, Number> inventoryInventoryColumn;
    @FXML private TableColumn<Part, Number> inventoryCostColumn;
    //table containing parts associated with a given product
    @FXML private TableView<Part> productParts; 
    @FXML private TableColumn<Part, Number> partsIDColumn;
    @FXML private TableColumn<Part, String> partsNameColumn;
    @FXML private TableColumn<Part, Number> partsInventoryColumn;
    @FXML private TableColumn<Part, Number> partsCostColumn;
    
    private Stage addProductStage;
        
    @FXML private void initialize() {
        //initialize part table
        inventoryIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        inventoryNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        inventoryInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInstockProperty());
        inventoryCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty());
        
        //initialize table that displays parts associated with given product
        partsIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        partsNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partsInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInstockProperty());
        partsCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty());
        
        //generates incremented product ID for product
        productIDField.setText(Integer.toString(Product.getNextProduct()));
    }
    

    
    public void setAddProductStage(Stage addProductStage) {
        this.addProductStage = addProductStage;
    }

    public void setAddProductPartsInventory(Inventory inventory) {
        //dispays avaialble parts in parts table
        this.inventory = inventory;
        Inventory r = new Inventory();
        partsInventory.setItems(r.getPartsInventory());
    }
    
    private void showProductPartsTable() {
        //used to display parts associated with a product in a table
        productParts.setItems(Product.getTempProductParts());
    }
    
    
    @FXML private void handleSearchParts() {
        //search parts table and return results in message prompt
        String errorMessage = "";
        Part part = null;
        
        if ((searchPartsField.getText() == null) || (searchPartsField.getText().length() == 0)) {
            errorMessage += "No part name entered\n";
        } else {
                part = Product.lookupPart(searchPartsField.getText());

        }
        
        if (part == null) {
            errorMessage += "No part matched the entered name";
        }
        
        if (errorMessage.length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part found in Inventory");
            if (part.isInhouse()) {
                alert.setContentText("ID " + part.getPartID() + "\nName: " + part.getPartName() + "\nPrice: " + part.getPartPrice() + "\nInventory: " + part.getPartInstock() + "\nMin: " + part.getPartMin() + "\nMax: " + part.getPartMax() + "\nMachine ID: " + ((Inhouse) part).getMachineID());
            } else {
                alert.setContentText("ID " + part.getPartID() + "\nName: " + part.getPartName() + "\nPrice: " + part.getPartPrice() + "\nInventory: " + part.getPartInstock() + "\nMin: " + part.getPartMin() + "\nMax: " + part.getPartMax() + "\nCompany Name: " + ((Outsourced) part).getCompanyName());
            }

            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
        }
    }
    
    @FXML private void handleAdd() {
        //add parts to product from parts table
        Part selectedPart = partsInventory.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(inventory.getPrimaryStage());
            alert.setTitle("No part selected");
            alert.setContentText("Select a part from the first table.");
            
            alert.showAndWait();
        } else {
            Product.addParts(selectedPart);
            showProductPartsTable();
        }
    }
    
    @FXML private void handleDelete() {
        //remove a part from the bottom table
        Part selectedPart = productParts.getSelectionModel().getSelectedItem();
        
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(inventory.getPrimaryStage());
            alert.setTitle("No part selected");
            alert.setContentText("Select a part from the bottom table.");
            
            alert.showAndWait();
        } else {
            Product r=new Product();
            r.removePart(selectedPart);
            showProductPartsTable();
        }
    }
    
    @FXML private void handleSave() {

        if (isInputValid()) {

                Product product = new Product();
                
                product.setProductID();
                product.setProductName(productNameField.getText());
                product.setProductInstock(Integer.parseInt(productInventoryField.getText()));
                product.setProductPrice(Double.parseDouble(productCostField.getText()));
                product.setProductMin(Integer.parseInt(productMinField.getText()));
                product.setProductMax(Integer.parseInt(productMaxField.getText()));
                product.setProductParts(Product.getTempProductParts());

                Inventory.addProducts(product);
                addProductStage.close();
            } 
        } 
    @FXML private void handleCancel() {

            addProductStage.close();
        
    }

    private boolean isInputValid() {
        
        String errorMessage = "";
        
        if ((productNameField.getText() == null) || (productNameField.getText().length() == 0)) {
            errorMessage += "Invalid product name\n";
        }

        if ((productInventoryField.getText() == null) || (productInventoryField.getText().length() == 0)) {
            errorMessage += "Inventory cannot be empty\n";
        } else {
            try {
                Integer.parseInt(productInventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid inventory. Inventory must be of type integer\n";
            }
        }
        if ((productMinField.getText() == null) || (productMinField.getText().length() == 0)) {
            errorMessage += "Minimum cannot be empty\n";
        } else {
            try {
                Integer.parseInt(productMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid minimum. Minimum must be of type integer\n";
            }
        }
        if ((productMaxField.getText() == null) || (productMaxField.getText().length() == 0)) {
            errorMessage += "Maximum cannot be empty\n";
        } else {
            try {
                Integer.parseInt(productMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid maximum. Maximum must be of type integer\n";
            }
        }
        
        if ((productCostField.getText() == null) || (productCostField.getText().length() == 0)) {
            errorMessage += "Cost cannot be empty\n";
        } else {
            try {
                Double.parseDouble(productCostField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Cost is invalid. Cost must be of type double\n";
            }        
        }
        
        if ((Product.getTempProductParts() == null) || (Product.getTempProductParts().size() == 0)) {
            errorMessage += "Product must include parts\n";
        }
        
        if (errorMessage.length() > 0) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            errorMessage = "";
            return false;
        }
        
        double costOfParts = 0;
        for (Part part : Product.getTempProductParts()) {
            costOfParts += part.getPartPrice();
        }
        if (Double.parseDouble(productCostField.getText()) < costOfParts) {
            errorMessage += "Product price is less than total price of its parts\n";
        }
        
        if (Integer.parseInt(productMinField.getText()) < 0) {
            errorMessage += "Minimum cannot be below 0\n";
        }
        if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) {
            errorMessage += "Minimum cannot exceed  maximum\n";
        }
        
        if (Integer.parseInt(productInventoryField.getText()) < Integer.parseInt(productMinField.getText())) {
            errorMessage += "Inventory cannot be below minimum\n";
        }
        if (Integer.parseInt(productInventoryField.getText()) > Integer.parseInt(productMaxField.getText())) {
            errorMessage += "Inventory cannot be above maximum\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            errorMessage = "";
            return false;
        }
        
    }
    
    
    
}
