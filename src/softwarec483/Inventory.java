/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483;

import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import softwarec483.model.Inhouse;
import softwarec483.model.Outsourced;
import softwarec483.model.Part;
import softwarec483.model.Product;
import softwarec483.view.AddPartController;
import softwarec483.view.AddProductController;
import softwarec483.view.MainScreenController;
import softwarec483.view.ModifyPartController;
import softwarec483.view.ModifyProductController;

/**
 *
 * @author jnorah
 */
public class Inventory extends Application {
    
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    static ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    static ObservableList<Product> productsInventory = FXCollections.observableArrayList();
    
    public static ObservableList<Product> getProductsInventory() {
        return productsInventory;
    }
    
    public ObservableList<Part> getPartsInventory() {
        return partsInventory;
    }
   
    
    public static void addParts(Part part) {
        partsInventory.add(part);
    } 
    
    
     public static void addProducts(Product product) {
        productsInventory.add(product);
    }
    
    public static void deleteParts(Part part) { 
        partsInventory.remove(part);
    }
        
    public static Part lookupPart(String partName) {
        Part lookupPart = null;
        
        for (Part part : partsInventory) {
            
            if (part.getPartName().equals(partName)) { 
                
                if (part.isInhouse()) { 
                    lookupPart = new Inhouse();
                    ((Inhouse) lookupPart).setMachineID(((Inhouse) part).getMachineID());
                } else { 
                    lookupPart = new Outsourced();
                    ((Outsourced) lookupPart).setCompanyName(((Outsourced) part).getCompanyName());
                } 
            
                lookupPart.setPartID(part.getPartID());
                lookupPart.setPartName(part.getPartName());
                lookupPart.setPartPrice(part.getPartPrice());
                lookupPart.setPartInstock(part.getPartInstock());
                lookupPart.setPartMin(part.getPartMin());
                lookupPart.setPartMax(part.getPartMax());
                
                break;
            } 
            
        } 
        
        return lookupPart;
    }
    
    public static Product lookupProduct(String productName) {
        
        Product lookupProduct = null; 
        
        for (Product product : productsInventory) {
            
            if (product.getProductName().equals(productName)) {
                lookupProduct = new Product();
                
                lookupProduct.setProductID(product.getProductID());
                lookupProduct.setProductName(product.getProductName());
                lookupProduct.setProductPrice(product.getProductPrice());
                lookupProduct.setProductInstock(product.getProductInstock());
                lookupProduct.setProductMin(product.getProductMin());
                lookupProduct.setProductMax(product.getProductMax());
                lookupProduct.setProductParts(product.getProductParts());
                
                break;
            } 
            
        } 
        
        return lookupProduct;
    }
    
    public void startRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Inventory Management System");
        startRootLayout();
        showMainScreen();
    }
    
    public void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view/MainScreen.fxml"));
            AnchorPane mainScreen = (AnchorPane) loader.load();
            rootLayout.setCenter(mainScreen);
            MainScreenController controller = loader.getController();
            controller.setInventory(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAddPart() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view/AddPart.fxml"));
            AnchorPane addPartPane = (AnchorPane) loader.load();
            
            Stage addPartStage = new Stage();
            addPartStage.setTitle("Add Part");
            addPartStage.initModality(Modality.WINDOW_MODAL);
            addPartStage.initOwner(primaryStage);
            
            Scene scene = new Scene(addPartPane);
            addPartStage.setScene(scene);
            
            AddPartController controller = loader.getController();
            controller.setAddPartStage(addPartStage);
            
            addPartStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showModifyPart(Part part) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view/ModifyPart.fxml"));
            AnchorPane modifyPartPane = (AnchorPane) loader.load();
            
            Stage modifyPartStage = new Stage();
            modifyPartStage.setTitle("Modify Part");
            modifyPartStage.initModality(Modality.WINDOW_MODAL);
            modifyPartStage.initOwner(primaryStage);
            
            Scene scene = new Scene(modifyPartPane);
            modifyPartStage.setScene(scene);
            
            ModifyPartController controller = loader.getController();
            controller.setModifyPartStage(modifyPartStage);
            controller.setPartModify(part);
            
            modifyPartStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showAddProduct() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view/AddProduct.fxml"));
            AnchorPane addProductPane = (AnchorPane) loader.load();
            
            Stage addProductStage = new Stage();
            addProductStage.setTitle("Add Product");
            addProductStage.initModality(Modality.WINDOW_MODAL);
            addProductStage.initOwner(primaryStage);
            
            Scene scene = new Scene(addProductPane);
            addProductStage.setScene(scene);
            
            AddProductController controller = loader.getController();
            controller.setAddProductStage(addProductStage);
            controller.setAddProductPartsInventory(this);
                                   
            addProductStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showModifyProduct(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view/ModifyProduct.fxml"));
            AnchorPane modifyProductPane = (AnchorPane) loader.load();
            
            Stage modifyProductStage = new Stage();
            modifyProductStage.setTitle("Modify Product");
            modifyProductStage.initModality(Modality.WINDOW_MODAL);
            modifyProductStage.initOwner(primaryStage);
            
            Scene scene = new Scene(modifyProductPane);
            modifyProductStage.setScene(scene);
            
            ModifyProductController controller = loader.getController();
            controller.setModifyProductStage(modifyProductStage);
            controller.setProduct(product);
            controller.setModifyProductPartsInventory(this);
                                   
            modifyProductStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exit() {
        primaryStage.close();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
        
    } 
    
}