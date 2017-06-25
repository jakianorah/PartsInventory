/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package softwarec483.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import softwarec483.Inventory;
import softwarec483.model.Inhouse;
import softwarec483.model.Outsourced;
import softwarec483.model.Part;

/**
 * FXML Controller class
 *
 * @author jakianorah
 */
public class AddPartController {

    @FXML private TextField partIDField;
    @FXML private TextField partNameField;
    @FXML private TextField partInventoryField;
    @FXML private TextField partCostField;
    @FXML private TextField partMinField;
    @FXML private TextField partMaxField;
    @FXML private RadioButton rdoInhouse;
    @FXML private RadioButton rdoOutsourced;
    @FXML private Label lblInhouseOutsourced;
    @FXML private TextField inhouseOutsourcedField;
    @FXML private ToggleGroup partToggle;
    private Stage addPartStage;
    private boolean isInhouse = true;
        
    @FXML private void initialize() {
        partIDField.setText(Integer.toString(Part.getNextPart()));
    }
    
    public void setAddPartStage(Stage addPartStage) {
        this.addPartStage = addPartStage;
    }

    @FXML private void handleInhouse() {
        isInhouse = true;
        lblInhouseOutsourced.setText("Machine ID");
    }
    
    @FXML private void handleOutsourced() {
        isInhouse = false;
        lblInhouseOutsourced.setText("Company Name");
    }
    
    @FXML private void handleCancel() {
       addPartStage.close();
        
    }
    
    @FXML private void handleSave() {

        if (isInputValid()) {

                if (isInhouse) {
                    Inhouse inhouse = new Inhouse();
                    saveParts(inhouse);
                    inhouse.setMachineID(Integer.parseInt(inhouseOutsourcedField.getText()));

                    Inventory.addParts(inhouse);
                    addPartStage.close();
                } else {
                    Outsourced outsourced = new Outsourced();
                    saveParts(outsourced);
                    outsourced.setCompanyName(inhouseOutsourcedField.getText());

                    Inventory.addParts(outsourced);
                    addPartStage.close();
                } 
            } 
        } 

    
    private void saveParts(Part part) {
        part.setPartID();
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
        
        if ((inhouseOutsourcedField.getText() == null) || (inhouseOutsourcedField.getText().length() == 0)) {
            errorMessage += "Invalid machine ID or company name\n";
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

        if (isInhouse) {
           try {
                Integer.parseInt(inhouseOutsourcedField.getText());
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
    
    
    
}
