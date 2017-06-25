/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
public class MainScreenController {
    private Inventory inventory; 
    //Parts Table
    @FXML private TextField searchPartsField;
    @FXML private TextField searchProductsField;
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Number> partIDColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Number> partInventoryColumn;
    @FXML private TableColumn<Part, Number> partCostColumn;
  // Product Table
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Number> productIDColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Number> productInventoryColumn;
    @FXML private TableColumn<Product, Number> productCostColumn;
    
    public MainScreenController() {   
    }
    
    @FXML private void initialize() { 
        //Initialize parts table
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInstockProperty());
        partCostColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty());
        
        //initialize product table
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().productInstockProperty());
        productCostColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty());
               
    }
    
    public void setInventory(Inventory inventory) {
        //display items in tables
        this.inventory = inventory;
        partsTable.setItems(inventory.getPartsInventory());
        productsTable.setItems(inventory.getProductsInventory());
    }


    
    @FXML private void handleAddPart() {
        //add part button opens new scene
        inventory.showAddPart();
    }
    
    @FXML private void handleModifyPart() {
        //modify part button opens new screen and pulls data from rows selected
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        
        if (selectedPart == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(inventory.getPrimaryStage());
            alert.setTitle("No part selected");
            alert.setContentText("Select a part from the table.");
            
            alert.showAndWait();
        } else inventory.showModifyPart(selectedPart);
  
    }    
     
    @FXML private void handleDeletePart() {
        //delete selected row from parts table
        int selectedIndex = partsTable.getSelectionModel().getSelectedIndex();
        
        if (selectedIndex >= 0) {
            partsTable.getItems().remove(selectedIndex);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setContentText("Please select a part from the table");
            
            alert.showAndWait();
        }

    }
   
    
    @FXML private void handleAddProduct() {
        //Open add product scene
        inventory.showAddProduct();
    }
    
    @FXML private void handleModifyProduct() {
        //Pulls data from selected from to fill new scene that opens when you press modify button
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        
        if (selectedProduct == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(inventory.getPrimaryStage());
            alert.setTitle("No product selected");
            alert.setContentText("Select a product from the table.");
            
            alert.showAndWait();
        } else inventory.showModifyProduct(selectedProduct);
    }
   
    @FXML private void handleDeleteProduct() {
        //deletes selected product. Gives warning if there is a part associated with the product already and doesn't delete it. Since a product must have a part this means no products can be deleted. 
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            
    Product tempProduct = productsTable.getSelectionModel().getSelectedItem();
                if (tempProduct.getProductParts().size() > 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setTitle("Error");
            alert.setContentText("This product has a part assigned to it and cannot be deleted!");
            
            alert.showAndWait();
                } 
        }
                else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected");
            alert.setContentText("Please select a product from the table");
            
            alert.showAndWait();
        }
       

    }

    
    @FXML private void handleExit() {
        //Close program
        inventory.exit();
    }
    
    @FXML private void handleSearchParts() {
        //Search parts by part name and returns a message box displaying the results of the search
        String errorMessage = "";
        Part part = null;
        
        if ((searchPartsField.getText() == null) || (searchPartsField.getText().length() == 0)) {
            errorMessage += "No part name entered\n";
        } else {
                part = Inventory.lookupPart(searchPartsField.getText());
           
        }
        
        if (part == null) {
            errorMessage += "No matching part found";
        }
        
        if (errorMessage.length() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Part found in Inventory");
            if (part.isInhouse()) {
                alert.setContentText("ID " + part.getPartID() + "\nName: " + part.getPartName() + "\nPrice: " + part.getPartPrice() + "\nInventory: " + part.getPartInstock() + "\nMin: " + part.getPartMin() + "\nMax: " + part.getPartMax() + "\nMachine ID: " + ((Inhouse) part).getMachineID());
            } else {
                alert.setContentText("ID " + part.getPartID() + "\nName: " + part.getPartName() + "\nPrice: " + part.getPartPrice() + "\nInventory: " + part.getPartInstock() + "\nMin: " + part.getPartMin() + "\nMax: " + part.getPartMax() + "\nCompany Name: " + ((Outsourced) part).getCompanyName());
            }

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
        }
    }
    
    @FXML private void handleSearchProducts() {
        //searched products by product name and displays results in message box
        String errorMessage = "";
        Product product = null;
        
        if ((searchProductsField.getText() == null) || (searchProductsField.getText().length() == 0)) {
            errorMessage += "No product name entered\n";
        } else {
          
                product = Inventory.lookupProduct(searchProductsField.getText());
           
        }
        
        if (product == null) {
            errorMessage += "No matching product found";
        }
        
        if (errorMessage.length() == 0) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Product found in Inventory");
            alert.setContentText("ID " + product.getProductID() + "\nName: " + product.getProductName() + "\nPrice: " + product.getProductPrice() + "\nInventory: " + product.getProductInstock() + "\nMin: " + product.getProductMin() + "\nMax: " + product.getProductMax());

            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
        }
    }
    
}