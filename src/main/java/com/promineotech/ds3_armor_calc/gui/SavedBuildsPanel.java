package com.promineotech.ds3_armor_calc.gui;

import com.promineotech.ds3_armor_calc.entities.Build;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("serial")
public class SavedBuildsPanel extends Panel {

    private ArmorCalculatorGUI parentGui;  // reference to main frame
    private BuildRepository buildRepository;
    
    private TextArea resultArea;
    private Button refreshButton;
    private Button backButton;
    private TextField deleteIdField;
    private Button deleteButton;
    
    public SavedBuildsPanel(ArmorCalculatorGUI parentGui, BuildRepository buildRepository) {
        this.parentGui = parentGui;
        this.buildRepository = buildRepository;
        
        setLayout(new BorderLayout());

        // A label at the top
        Label title = new Label("Saved Builds:");
        add(title, BorderLayout.NORTH);
        
        // The center area to display builds
        resultArea = new TextArea(15, 60);
        resultArea.setEditable(false);
        add(resultArea, BorderLayout.CENTER);
        
        // A panel at the bottom for buttons and deletion
        Panel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        
        refreshButton = new Button("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBuilds();
            }
        });
        buttonPanel.add(refreshButton);
        
        backButton = new Button("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Return to the WeightPanel or whichever panel you prefer
                parentGui.showCard("weight");
            }
        });
        buttonPanel.add(backButton);
        
        // Add deletion controls: a text field to enter the build ID and a button to delete
        Label deleteLabel = new Label("Enter Build ID to delete:");
        buttonPanel.add(deleteLabel);
        
        deleteIdField = new TextField(10);
        buttonPanel.add(deleteIdField);
        
        deleteButton = new Button("Delete Build");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Long buildId = Long.valueOf(deleteIdField.getText().trim());
                    buildRepository.deleteById(buildId);
                    resultArea.append("Build with ID " + buildId + " deleted.\n");
                    loadBuilds();
                } catch (NumberFormatException ex) {
                    resultArea.append("Invalid build ID. Please enter a valid number.\n");
                } catch (Exception ex) {
                    resultArea.append("Error deleting build: " + ex.getMessage() + "\n");
                }
            }
        });
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Fetches all saved builds from the repository and displays them
     * in the text area.
     */
    private void loadBuilds() {
        resultArea.setText(""); // clear current text
        List<Build> builds = buildRepository.findAll();
        if (builds.isEmpty()) {
            resultArea.append("No saved builds found.\n");
            return;
        }
        for (Build b : builds) {
            resultArea.append("Build ID: " + b.getBuildId() + "\n");
            resultArea.append("Boss: " + b.getBossName() + "\n");
            resultArea.append("Roll Type: " + b.getRollType() + "\n");
            resultArea.append("Total Weight: " + b.getTotalWeight() + "\n");
            // Display armor piece details from the description field.
            resultArea.append("Armor Pieces:\n" + b.getDescription() + "\n");
            resultArea.append("-------------------------------------\n");
        }
    }
}
