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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import softwarec483.Inventory;
import softwarec483.model.*;

/**
 * FXML Controller class
 *
 * @author jakianorah
 */
public class ModifyProductController {
    private Inventory inventory;

    private ObservableList<Part> tempProductParts;
    private Product product;
    
    @FXML private TextField searchPartsField;
    
    @FXML private TextField productIDField;
    @FXML private TextField productNameField;
    @FXML private TextField productInventoryField;
    @FXML private TextField productCostField;
    @FXML private TextField productMinField;
    @FXML private TextField productMaxField;
    
    @FXML private TableView<Part> partsInventory; 
    @FXML private TableColumn<Part, Number> inventoryIDColumn;
    @FXML private TableColumn<Part, String> inventoryNameColumn;
    @FXML private TableColumn<Part, Number> inventoryInventoryColumn;
    @FXML private TableColumn<Part, Number> inventoryCostColumn;
    
    @FXML private TableView<Part> productParts; 
    @FXML private TableColumn<Part, Number> partsIDColumn;
    @FXML private TableColumn<Part, String> partsNameColumn;
    @FXML private TableColumn<Part, Number> partsInventoryColumn;
    @FXML private TableColumn<Part, Number> partsCostColumn;
    
    private Stage modifyProductStage;
        
    @FXML private void initialize() {
        inventoryIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        inventoryNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        inventoryInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInstockProperty());
        inventoryCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty());
        
        partsIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        partsNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partsInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInstockProperty());
        partsCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty());
    
    }
    
    public void setModifyProductStage(Stage modifyProductStage) {
        this.modifyProductStage = modifyProductStage;
    }

    
    public void setModifyProductPartsInventory(Inventory inventory) {
        this.inventory = inventory;
        partsInventory.setItems(inventory.getPartsInventory());
    }
    
    public void setProduct(Product product) {
        this.product = product;
        tempProductParts = product.getProductParts();
        productIDField.setText(Integer.toString(product.getProductID()));
        productNameField.setText(product.getProductName());
        productInventoryField.setText(Integer.toString(product.getProductInstock()));
        productCostField.setText(Double.toString(product.getProductPrice()));
        productMinField.setText(Integer.toString(product.getProductMin()));
        productMaxField.setText(Integer.toString(product.getProductMax()));
        showProductPartsTable();
    }
    
    private void showProductPartsTable() {
        productParts.setItems(tempProductParts);
        Product.updatePart();
    }
    

    
    @FXML private void handleSearchParts() {
        String errorMessage = "";
        Part part = null;
        
        if ((searchPartsField.getText() == null) || (searchPartsField.getText().length() == 0)) {
            errorMessage += "No part name entered\n";
        } else {
                part = Inventory.lookupPart(searchPartsField.getText());

        }
        
        if (part == null) {
            errorMessage += "No matching part name found";
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
            alert.setTitle("Error");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
        }
    }

    @FXML private void handleAdd() {
        Part selectedPart = partsInventory.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(inventory.getPrimaryStage());
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part from the first parts table.");
            
            alert.showAndWait();
        } else {
            tempProductParts.add(selectedPart);
            showProductPartsTable();
        }
    }
    
    @FXML private void handleDelete() {
        
        Part selectedPart = productParts.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(inventory.getPrimaryStage());
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part from the bottom parts table.");
            
            alert.showAndWait();
        } else {
            tempProductParts.remove(selectedPart);
            showProductPartsTable();
        }
    }
    
    @FXML private void handleSave() {

        if (isInputValid()) {
            
                product.setProductName(productNameField.getText());
                product.setProductInstock(Integer.parseInt(productInventoryField.getText()));
                product.setProductPrice(Double.parseDouble(productCostField.getText()));
                product.setProductMin(Integer.parseInt(productMinField.getText()));
                product.setProductMax(Integer.parseInt(productMaxField.getText()));
                product.setProductParts(tempProductParts);

                modifyProductStage.close();
            
        }
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
                errorMessage += "Invalid inventory level. Inventory level must be of type integer.\n";
            }
        }
        if ((productMinField.getText() == null) || (productMinField.getText().length() == 0)) {
            errorMessage += "Minimum cannot be empty\n";
        } else {
            try {
                Integer.parseInt(productMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid minimum. Minimum must be of type integer.\n";
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
        
        if ((tempProductParts == null) || (tempProductParts.isEmpty())) {
            errorMessage += "Product doesn't include any parts\n";
        }
        
        if (errorMessage.length() > 0) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            errorMessage = ""; 
            return false;
        }
        
        double costOfParts = 0;
        for (Part part : tempProductParts) {
            costOfParts += part.getPartPrice();
        }
        if (Double.parseDouble(productCostField.getText()) < costOfParts) {
            errorMessage += "Price cannot be less than total cost of the included parts\n";
        }
        
        if (Integer.parseInt(productMinField.getText()) < 0) {
            errorMessage += "Minimum cannot be below 0\n";
        }
        if (Integer.parseInt(productMinField.getText()) > Integer.parseInt(productMaxField.getText())) {
            errorMessage += "Minimum cannot be more than maximum\n";
        }
        
        if (Integer.parseInt(productInventoryField.getText()) < Integer.parseInt(productMinField.getText())) {
            errorMessage += "Inventory level cannot be below minimum\n";
        }
        if (Integer.parseInt(productInventoryField.getText()) > Integer.parseInt(productMaxField.getText())) {
            errorMessage += "Inventory level cannot be above maximum\n";
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
    
    @FXML private void handleCancel() {

            modifyProductStage.close();
      
    }
    
}