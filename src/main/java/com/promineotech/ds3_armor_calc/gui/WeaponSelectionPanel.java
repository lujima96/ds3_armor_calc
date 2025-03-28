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
public class WeaponSelectionPanel extends Panel {

    private ArmorCalculatorGUI parent;

    private Button confirmButton;
    private List<Checkbox> itemCheckboxes = new ArrayList<>();
    private Map<String, Double> weightMap = new HashMap<>();

    public WeaponSelectionPanel() {
        this.parent = parent;
        setLayout(new BorderLayout());

        Label weaponsLabel = new Label("Select Weapons:");
        add(weaponsLabel, BorderLayout.NORTH);

        List<Item> weapons = ApiClient.fetchWeaponItems();

        Panel weaponTypesPanel = new Panel(new GridLayout(0, 1));
        for (Item item : weapons) {
            String name = item.getItemName();
            Checkbox cb = new Checkbox(name);
            weaponTypesPanel.add(cb);
            itemCheckboxes.add(cb);
            weightMap.put(name, item.getWeight());
        }

        ScrollPane weaponScrollPane = new ScrollPane();
        weaponScrollPane.add(weaponTypesPanel);
        add(weaponScrollPane, BorderLayout.CENTER);
        
        confirmButton = new Button("Confirm");
        // confirmButton.addActionListener(e -> parent.showCard("shields"));
        add(confirmButton, BorderLayout.SOUTH);
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public double getTotalSelectedWeight() {
        double total = 0.0;
        for (Checkbox cb : itemCheckboxes) {
            if (cb.getState()) {
                String name = cb.getLabel();
                Double w = weightMap.get(name);
                if (w != null) total += w;
            }
        }
        return total;
    }
}
