/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import softwarec483.Inventory;

/**
 *
 * @author jnorah
 */
public abstract class Part {
    IntegerProperty partID;
    StringProperty partName;
    IntegerProperty partInstock;
    DoubleProperty partPrice;
    IntegerProperty partMin;
    IntegerProperty partMax;
    static int nextPart = 1;
    private Inventory inventoryMain;
    
    
    
    
    public int getPartID() {
        return partID.get();
    }
     public static int getNextPart() {
        return nextPart;
    }
    
    public void setPartID() {
       this.partID.set(nextPart++);
   }
   
   public void setPartID(int partID) {
       
       this.partID.set(partID);
   }
   public IntegerProperty partIDProperty() {
        return partID;
    }
   
   public void setPartName(String partName) {
        this.partName.set(partName);
   }
 
    public String getPartName() {
        return partName.get();
    }
    
    public StringProperty partNameProperty() {
        return partName;
    }
    
    public void setPartInstock(int partInstock) {
       this.partInstock.set(partInstock);
   }
    
    public int getPartInstock() {
        return partInstock.get();
    }
    
    public IntegerProperty partInstockProperty() {
        return partInstock;
    }
    
   public void setPartPrice(double partPrice) {
       this.partPrice.set(partPrice);
   }
   
    public double getPartPrice() {
        return partPrice.get();
    }
    
    public DoubleProperty partPriceProperty() {
        return partPrice;
    }
    
    public void setPartMin(int partMin) {
       this.partMin.set(partMin);
   }
    
    public int getPartMin() {
        return partMin.get();
    }
    
    public IntegerProperty partMinProperty() {
        return partMin;
    }
    
    public void setPartMax(int partMax) {
       this.partMax.set(partMax);
   }
    
    public int getPartMax() {
        return partMax.get();
    }
       
    public IntegerProperty partMaxProperty() {
        return partMax;
    }
    
    public abstract boolean isInhouse();
}