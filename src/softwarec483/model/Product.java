/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483.model;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import softwarec483.Inventory;
import softwarec483.view.AddProductController;
import softwarec483.view.ModifyProductController;
/**
 *
 * @author jakianorah
 */
public class Product {
    private Inventory inventory;
    private AddProductController addProductController;
    private ModifyProductController modifyProductController;
    static ObservableList<Part> tempProductParts = FXCollections.observableArrayList();
    static ObservableList<Part> productParts = FXCollections.observableArrayList();

    private StringProperty productName;
    private IntegerProperty productID;
    private DoubleProperty productPrice;
    private IntegerProperty productInstock;
    private IntegerProperty productMin;
    private IntegerProperty productMax;
    private static int nextProduct = 1;
    
    public static ObservableList<Part> getTempProductParts() {
        return tempProductParts;
    }
    
    public Product() {
        this(null, 0, 0.0, 0, 0, null);
        
        
    }
    
    public Product(String productName, int productInstock, double productPrice, int productMin, int productMax, ObservableList<Part> productParts) {
        this.productID = new SimpleIntegerProperty(nextProduct);
        this.productName = new SimpleStringProperty(productName);
        this.productInstock = new SimpleIntegerProperty(productInstock);
        this.productPrice = new SimpleDoubleProperty(productPrice);
        this.productMin = new SimpleIntegerProperty(productMin);
        this.productMax = new SimpleIntegerProperty(productMax);
        this.productParts = productParts;
        
        
        
    }
    
    public static int getNextProduct() {
        return nextProduct;
    }
    
    public void setProductID() {
        this.productID.set(nextProduct++);
    }
    public void setProductID(int productID) {
        this.productID.set(productID);
    }
    
    public int getProductID() {
        return productID.get();
    }
    
    public IntegerProperty productIDProperty() {
        return productID;
    }
    
    public void setProductName(String productName) {
        this.productName.set(productName);
    }
    public String getProductName() {
        return productName.get();
    }
    
    public StringProperty productNameProperty() {
        return productName;
    }
    
    public void setProductInstock(int productInstock) {
        this.productInstock.set(productInstock);
    }
    
    public int getProductInstock() {
        return productInstock.get();
    }
    
    public IntegerProperty productInstockProperty() {
        return productInstock;
    }
    
    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }
    
    public double getProductPrice() {
        return productPrice.get();
    }
    
    public DoubleProperty productPriceProperty() {
        return productPrice;
    }
    
    public void setProductMin(int productMin) {
        this.productMin.set(productMin);
    }
    
    public int getProductMin() {
        return productMin.get();
    }
    
    public IntegerProperty productMinProperty() {
        return productMin;
    }
    
    
    public void setProductMax(int productMax) {
        this.productMax.set(productMax);
    }
    
    public int getProductMax() {
        return productMax.get();
    }
    
    public IntegerProperty productMaxProperty() {
        return productMax;
    }
    
    public void setProductParts(ObservableList<Part> productParts) {
        this.productParts = productParts;
    }
    public ObservableList<Part> getProductParts() {
        return productParts;
    }
    
    
    public static void removePart(Part part) { 
        tempProductParts.remove(part);
    }



    public static void updatePart(){ 
        System.out.print("part updated");
    }
    
public static void addParts(Part part) {
        tempProductParts.add(part);
    }  
  public static Part lookupPart(String partName) {
        Part lookupPart = null;
        Inventory r = new Inventory();
        for (Part part : r.getPartsInventory()) {
            
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
            
}