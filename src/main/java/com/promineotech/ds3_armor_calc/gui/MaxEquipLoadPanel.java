package com.promineotech.ds3_armor_calc.gui;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.promineotech.ds3_armor_calc.model.EquipmentSelection;

@SuppressWarnings("serial")
public class MaxEquipLoadPanel extends Panel {

    private TextField equipLoadField;
    private TextField characterNameField;
    private Button confirmButton;
    
    // Shared EquipmentSelection instance.
    private EquipmentSelection equipmentSelection;

   
    public MaxEquipLoadPanel(EquipmentSelection equipmentSelection) {
        this.equipmentSelection = equipmentSelection;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        Label nameLabel = new Label("Character Name:");
        characterNameField = new TextField(15);  // Adjust size as needed
        
        Label equipLabel = new Label("Max Equip Load:");
        equipLoadField = new TextField(10);
        
        confirmButton = new Button("Confirm");

        add(nameLabel);
        add(characterNameField);
        add(equipLabel);
        add(equipLoadField);
        add(confirmButton);

        // When the Confirm button is clicked, capture and store the inputs.
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = characterNameField.getText();
                String equipLoadStr = equipLoadField.getText();
                double equipLoad = 0.0;
                
                try {
                    equipLoad = Double.parseDouble(equipLoadStr);
                } catch (NumberFormatException ex) {
                    // Handle invalid input (e.g., log error or notify the user).
                    System.err.println("Invalid number format for Max Equip Load: " + equipLoadStr);
                }
                
                equipmentSelection.setCharacterName(name);
                equipmentSelection.setMaxEquipLoad(equipLoad);
                
             
                System.out.println("Updated EquipmentSelection: " 
                        + equipmentSelection.getCharacterName() 
                        + ", Max Equip Load: " + equipmentSelection.getMaxEquipLoad());
            }
        });
    }

    public Button getConfirmButton() {
        return confirmButton;
    }
    
    public String getEquipLoad() {
        return equipLoadField.getText();
    }
    
    public String getCharacterName() {
        return characterNameField.getText();
    }
    
    public EquipmentSelection getEquipmentSelection() {
        return equipmentSelection;
    }
}
