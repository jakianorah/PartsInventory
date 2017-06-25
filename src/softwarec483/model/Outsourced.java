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
public class Outsourced extends Part {
    private StringProperty companyName;
    
    public Outsourced() {
        this(null, 0, 0.0, 0, 0, null);
    }
    
    public Outsourced(String partName, int partInstock, double partPrice, int partMin, int partMax, String companyName) {
        this.partID = new SimpleIntegerProperty(nextPart);
        this.partName = new SimpleStringProperty(partName);
        this.partInstock = new SimpleIntegerProperty(partInstock);
        this.partPrice = new SimpleDoubleProperty(partPrice);
        this.partMin = new SimpleIntegerProperty(partMin);
        this.partMax = new SimpleIntegerProperty(partMax);
        this.companyName = new SimpleStringProperty(companyName);
        
    }  


    public String getCompanyName() {
        return companyName.get();
    }
    
    public StringProperty companyNameProperty() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
    @Override
    public boolean isInhouse() {
        return false;
    }
    
}