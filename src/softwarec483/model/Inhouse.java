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
public class Inhouse extends Part {
    private IntegerProperty machineID;
    
    public Inhouse() {
        this(null, 0, 0.0, 0, 0, 0);
    }
    
    public Inhouse(String partName, int partInstock,double partPrice, int partMin, int partMax, int machineID) {
        this.partID = new SimpleIntegerProperty(nextPart);
        this.partName = new SimpleStringProperty(partName);
        this.partInstock = new SimpleIntegerProperty(partInstock);
        this.partPrice = new SimpleDoubleProperty(partPrice);
        this.partMin = new SimpleIntegerProperty(partMin);
        this.partMax = new SimpleIntegerProperty(partMax);
        this.machineID = new SimpleIntegerProperty(machineID);
        
    }  
    
    
    public int getMachineID() {
        return machineID.get();
    }
    
    public IntegerProperty machineIDProperty() {
        return machineID;
    }
    
    public void setMachineID(int machineID) {
        this.machineID.set(machineID);
    }
    
    @Override
    public boolean isInhouse() {
        return true;
    }
    
}