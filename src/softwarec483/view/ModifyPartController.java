/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import softwarec483.model.Inhouse;
import softwarec483.model.Outsourced;
import softwarec483.model.Part;
import java.util.ArrayList;
import java.util.Optional;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import softwarec483.Inventory;

/**
 * FXML Controller class
 *
 * @author jakianorah
 */
public class ModifyPartController {
    @FXML private TextField partIDField;
    @FXML private TextField partNameField;
    @FXML private TextField partInventoryField;
    @FXML private TextField partCostField;
    @FXML private TextField partMinField;
    @FXML private TextField partMaxField;

    @FXML private Label lblInhouseOutsourced;
    @FXML private TextField txtInhouseOutsourced;
    @FXML private RadioButton rdoInhouse;
    @FXML private RadioButton rdoOutsourced;
    
    private boolean isInhouse = true;
    
    private Stage modifyPartStage;
    private Part part;
    
    private void initialize() {
        
    }
     
    public void setModifyPartStage(Stage modifyPartStage) {
        this.modifyPartStage = modifyPartStage;
    }
       
    public void setPartModify(Part part) {
        this.part = part;
        
        partIDField.setText(Integer.toString(part.getPartID()));
        partNameField.setText(part.getPartName());
        partInventoryField.setText(Integer.toString(part.getPartInstock()));
        partCostField.setText(Double.toString(part.getPartPrice()));
        partMinField.setText(Integer.toString(part.getPartMin()));
        partMaxField.setText(Integer.toString(part.getPartMax()));
        
        if (part instanceof Inhouse) {
            setPart(((Inhouse) part));
        } else {
            setPart(((Outsourced) part));
        }
    }
    
    public void setPart(Inhouse part) {
        handleInhouse();
        txtInhouseOutsourced.setText(Integer.toString(part.getMachineID()));
    }
    
    public void setPart(Outsourced part) {
        handleOutsourced();
        txtInhouseOutsourced.setText(part.getCompanyName());
    }
 
    @FXML private void handleInhouse() {
        isInhouse = true;
        lblInhouseOutsourced.setText("Machine ID");
        rdoInhouse.setSelected(true);
        rdoOutsourced.setSelected(false);

    }
    
    @FXML private void handleOutsourced() {
        isInhouse = false;
        lblInhouseOutsourced.setText("Company Name");
        rdoOutsourced.setSelected(true);
        rdoInhouse.setSelected(false);
    }  
    
    @FXML private void handleSave() {
      
        if (isInputValid()) {

                if (part instanceof Inhouse) {
                    if (isInhouse) {
                        (((Inhouse) part)).setMachineID(Integer.parseInt(txtInhouseOutsourced.getText()));
                    } else { 
                        Outsourced outsourcedPart = new Outsourced();
                        outsourcedPart.setPartID(part.getPartID()); 
                        Inventory.deleteParts(part); 
                        
                        outsourcedPart.setCompanyName(txtInhouseOutsourced.getText());
                        saveModifyParts(outsourcedPart);

                        Inventory.addParts(outsourcedPart);
                        modifyPartStage.close();
                    }
                } else { 
                    if (!isInhouse) { 
                        (((Outsourced) part)).setCompanyName(txtInhouseOutsourced.getText());
                    } else { 
                        Inhouse inhousePart = new Inhouse();
                        inhousePart.setPartID(part.getPartID());
                        Inventory.deleteParts(part);
                        
                        inhousePart.setMachineID(Integer.parseInt(txtInhouseOutsourced.getText()));
                        saveModifyParts(inhousePart);

                        Inventory.addParts(inhousePart);
                        modifyPartStage.close();
                    }
                }

                saveModifyParts(part); 

                modifyPartStage.close(); 
        }
    }
     
    private void saveModifyParts(Part part) {
        part.setPartName(partNameField.getText());
        part.setPartInstock(Integer.parseInt(partInventoryField.getText()));
        part.setPartPrice(Double.parseDouble(partCostField.getText()));
        part.setPartMin(Integer.parseInt(partMinField.getText()));
        part.setPartMax(Integer.parseInt(partMaxField.getText()));
    }
    
        private boolean isInputValid() {
        
        String errorMessage = "";
        
        if ((partNameField.getText() == null) || (partNameField.getText().length() == 0)) {
            errorMessage += "Invalid part name\n";
        }

        if ((partInventoryField.getText() == null) || (partInventoryField.getText().length() == 0)) {
            errorMessage += "Inventory cannot be empty\n";
        } else {
            try {
                Integer.parseInt(partInventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid inventory level. Inventory must be of type integer\n";
            }
        }
        if ((partMinField.getText() == null) || (partMinField.getText().length() == 0)) {
            errorMessage += "Minimum cannot be empty\n";
        } else {
            try {
                Integer.parseInt(partMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid minimum. Minimum must be of type integer\n";
            }
        }
        if ((partMaxField.getText() == null) || (partMaxField.getText().length() == 0)) {
            errorMessage += "Maximum cannot be empty\n";
        } else {
            try {
                Integer.parseInt(partMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Invalid maximum. Maximum must be of type integer\n";
            }
        }
        
        if ((partCostField.getText() == null) || (partCostField.getText().length() == 0)) {
            errorMessage += "Cost cannot be empty\n";
        } else {
            try {
                Double.parseDouble(partCostField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Cost is invalid. Cost must be of type double\n";
            }        
        }
        
        if (errorMessage.length() > 0) {
           Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorMessage);
            
            alert.showAndWait();
            errorMessage = "";
            return false;
        }
        
        
        if (Integer.parseInt(partMinField.getText()) < 0) {
            errorMessage += "Minimum is below 0\n";
        }
        if (Integer.parseInt(partMinField.getText()) > Integer.parseInt(partMaxField.getText())) {
            errorMessage += "Minimum cannot exceed maximum\n";
        }
        
        
        if (Integer.parseInt(partInventoryField.getText()) < Integer.parseInt(partMinField.getText())) {
            errorMessage += "Inventory cannot be below minimum\n";
        }
        if (Integer.parseInt(partInventoryField.getText()) > Integer.parseInt(partMaxField.getText())) {
            errorMessage += "Inventory cannot be above maximum\n";
        }

        
        if ((txtInhouseOutsourced.getText() == null) || (txtInhouseOutsourced.getText().length() == 0)) {
            errorMessage += "Invalid machine ID or company name\n";
        } else if (isInhouse) { 
            try {
                Integer.parseInt(txtInhouseOutsourced.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Machine ID must be of type integer";
            } 
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
            modifyPartStage.close();
    }
    
}

