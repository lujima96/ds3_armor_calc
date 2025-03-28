package com.promineotech.ds3_armor_calc.gui;

import com.promineotech.ds3_armor_calc.model.EquipmentSelection;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import com.promineotech.ds3_armor_calc.repositories.PlayerRepository;
import com.promineotech.ds3_armor_calc.service.ArmorOptimizerService;
import com.promineotech.ds3_armor_calc.service.RetrieveChests;
import com.promineotech.ds3_armor_calc.service.RetrieveGauntlets;
import com.promineotech.ds3_armor_calc.service.RetrieveHelms;
import com.promineotech.ds3_armor_calc.service.RetrieveLeggings;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class ArmorCalculatorGUI extends Frame {

    private CardLayout cardLayout;
    private Panel cardPanel;
    private EquipmentSelection selection;

    // Spring-managed dependencies
    private final RetrieveGauntlets retrieveGauntlets;
    private final RetrieveHelms retrieveHelms;
    private final RetrieveLeggings retrieveLeggings;
    private final RetrieveChests retrieveChests;
    private final ArmorOptimizerService optimizerService;
    private final BuildRepository buildRepository;
    private final PlayerRepository playerRepository; // Added dependency

    public ArmorCalculatorGUI(RetrieveGauntlets retrieveGauntlets,
                              RetrieveHelms retrieveHelms,
                              RetrieveLeggings retrieveLeggings,
                              RetrieveChests retrieveChests,
                              ArmorOptimizerService optimizerService,
                              BuildRepository buildRepository,
                              PlayerRepository playerRepository) { // Updated constructor
        super("DS3 Armor Calculator");
        this.retrieveGauntlets = retrieveGauntlets;
        this.retrieveHelms = retrieveHelms;
        this.retrieveLeggings = retrieveLeggings;
        this.retrieveChests = retrieveChests;
        this.optimizerService = optimizerService;
        this.buildRepository = buildRepository;
        this.playerRepository = playerRepository;
        
        initGui();
    }
    
    private void initGui() {
        // Basic frame setup
        setLayout(new BorderLayout());
        selection = new EquipmentSelection();
        
        // Create the CardLayout container
        cardLayout = new CardLayout();
        cardPanel = new Panel(cardLayout);
        
        // Create panels (MaxEquipLoadPanel, RingSelectionPanel, WeaponSelectionPanel, ShieldSelectionPanel)
        MaxEquipLoadPanel maxPanel = new MaxEquipLoadPanel(selection); // Pass shared EquipmentSelection
        RingSelectionPanel ringPanel = new RingSelectionPanel();
        WeaponSelectionPanel weaponPanel = new WeaponSelectionPanel();
        ShieldSelectionPanel shieldPanel = new ShieldSelectionPanel();
        
        // Create WeightPanel, now with a valid PlayerRepository.
        WeightPanel weightPanel = new WeightPanel(
            selection,
            retrieveGauntlets,
            retrieveHelms,
            retrieveLeggings,
            retrieveChests,
            optimizerService,
            buildRepository, 
            playerRepository
        );

        // Create SavedBuildsPanel (for viewing saved builds)
        SavedBuildsPanel savedBuildsPanel = new SavedBuildsPanel(this, buildRepository);

        // Add panels to the card panel with unique names
        cardPanel.add(maxPanel, "max");
        cardPanel.add(ringPanel, "rings");
        cardPanel.add(weaponPanel, "weapons");
        cardPanel.add(shieldPanel, "shields");
        cardPanel.add(weightPanel, "weight");
        cardPanel.add(savedBuildsPanel, "savedBuilds");

        // Add the card panel to the frame
        add(cardPanel, BorderLayout.CENTER);

        // --- Define the flow logic ---
        
        // 1) MaxEquipLoadPanel: On confirm, store max equip load and move to rings panel
        maxPanel.getConfirmButton().addActionListener(e -> {
            try {
                double maxLoad = Double.parseDouble(maxPanel.getEquipLoad());
                selection.setMaxEquipLoad(maxLoad);
            } catch (NumberFormatException ex) {
                System.err.println("Invalid max equip load input.");
            }
            cardLayout.show(cardPanel, "rings");
        });

        // 2) RingSelectionPanel: On confirm, store rings weight and move to weapons panel
        ringPanel.getConfirmButton().addActionListener(e -> {
            double ringsWeight = ringPanel.getTotalSelectedWeight();
            selection.setRingsWeight(ringsWeight);
            cardLayout.show(cardPanel, "weapons");
        });

        // 3) WeaponSelectionPanel: On confirm, store weapons weight and move to shields panel
        weaponPanel.getConfirmButton().addActionListener(e -> {
            double weaponsWeight = weaponPanel.getTotalSelectedWeight();
            selection.setWeaponsWeight(weaponsWeight);
            cardLayout.show(cardPanel, "shields");
        });

        // 4) ShieldSelectionPanel: On confirm, store shields weight, update WeightPanel display, then move to weight panel
        shieldPanel.getConfirmButton().addActionListener(e -> {
            double shieldsWeight = shieldPanel.getTotalSelectedWeight();
            selection.setShieldsWeight(shieldsWeight);
            weightPanel.updateDisplay();
            cardLayout.show(cardPanel, "weight");
        });

        // 5) In WeightPanel, the "View Saved Builds" button navigates to the SavedBuildsPanel
        weightPanel.getViewBuildsButton().addActionListener(e -> {
            cardLayout.show(cardPanel, "savedBuilds");
        });

        // Basic frame settings
        setSize(1200, 1200);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    
    /**
     * Expose this method so that child panels (if needed) can switch panels.
     */
    public void showCard(String name) {
        cardLayout.show(cardPanel, name);
    }
}
