package com.promineotech.ds3_armor_calc.gui;

import com.promineotech.ds3_armor_calc.entities.Item;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class ShieldSelectionPanel extends Panel {

    private ArmorCalculatorGUI parent;
    
    private Button confirmButton;
    private List<Checkbox> shieldCheckboxes = new ArrayList<>();
    private Map<String, Double> weightMap = new HashMap<>();

    public ShieldSelectionPanel() {
        this.parent = parent;
        setLayout(new BorderLayout());

        Label shieldsLabel = new Label("Select Shields:");
        add(shieldsLabel, BorderLayout.NORTH);

        List<Item> shields = ApiClient.fetchShieldItems();

        Panel shieldPanel = new Panel(new GridLayout(0, 1));
        for (Item s : shields) {
            String name = s.getItemName();
            Checkbox cb = new Checkbox(name);
            shieldPanel.add(cb);
            shieldCheckboxes.add(cb);
            weightMap.put(name, s.getWeight());
        }
        
        ScrollPane shieldScrollPane = new ScrollPane();
        shieldScrollPane.add(shieldPanel);
        add(shieldScrollPane, BorderLayout.CENTER);

        confirmButton = new Button("Confirm");
        // confirmButton.addActionListener(e -> parent.showCard("weight"));
        add(confirmButton, BorderLayout.SOUTH);
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public double getTotalSelectedWeight() {
        double total = 0.0;
        for (Checkbox cb : shieldCheckboxes) {
            if (cb.getState()) {
                String name = cb.getLabel();
                Double w = weightMap.get(name);
                if (w != null) total += w;
            }
        }
        return total;
    }
}
