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
public class RingSelectionPanel extends Panel {

    private List<Checkbox> ringCheckboxes;
    private Map<String, Double> weightMap;
    private Button confirmButton;

    public RingSelectionPanel() {
        setLayout(new BorderLayout());

        // A label at the top
        Label ringsLabel = new Label("Select Rings (up to 4):");
        add(ringsLabel, BorderLayout.NORTH);

        ringCheckboxes = new ArrayList<>();
        weightMap = new HashMap<>();

        // 1) Fetch all rings from the API
        List<Item> rings = ApiClient.fetchRingItems();

        // 2) Build a checkbox panel
        Panel ringsCheckboxPanel = new Panel(new GridLayout(0, 1, 0, 2));
        for (Item ring : rings) {
            // Use ring.getItemName() as label
            String name = ring.getItemName() != null ? ring.getItemName() : "(Unnamed Ring)";
            Checkbox cb = new Checkbox(name);
            ringsCheckboxPanel.add(cb);
            ringCheckboxes.add(cb);

            // Store weight (could be null if the ring is missing weight)
            Double weight = ring.getWeight();
            if (weight == null) {
                weight = 0.0;
            }
            weightMap.put(name, weight);
        }

        // 3) Wrap checkboxes in a scrollable area
        ScrollPane ringScrollPane = new ScrollPane();
        ringScrollPane.add(ringsCheckboxPanel);
        add(ringScrollPane, BorderLayout.CENTER);

        // 4) Confirm button at the bottom
        confirmButton = new Button("Confirm");
        add(confirmButton, BorderLayout.SOUTH);
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    /**
     * Calculates total weight of all selected rings
     */
    public double getTotalSelectedWeight() {
        double total = 0.0;
        for (Checkbox cb : ringCheckboxes) {
            if (cb.getState()) {
                String name = cb.getLabel();
                Double w = weightMap.get(name);
                if (w != null) {
                    total += w;
                }
            }
        }
        return total;
    }
}
