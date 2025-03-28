package com.promineotech.ds3_armor_calc.gui;

import com.promineotech.ds3_armor_calc.dto.ArmorCombination;
import com.promineotech.ds3_armor_calc.dto.BossProfile;
import com.promineotech.ds3_armor_calc.dto.BossProfiles;
import com.promineotech.ds3_armor_calc.entities.Build;
import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.entities.Player;
import com.promineotech.ds3_armor_calc.model.EquipmentSelection;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import com.promineotech.ds3_armor_calc.repositories.PlayerRepository;
import com.promineotech.ds3_armor_calc.service.ArmorOptimizerService;
import com.promineotech.ds3_armor_calc.service.RetrieveChests;
import com.promineotech.ds3_armor_calc.service.RetrieveGauntlets;
import com.promineotech.ds3_armor_calc.service.RetrieveHelms;
import com.promineotech.ds3_armor_calc.service.RetrieveLeggings;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.util.List;

@SuppressWarnings("serial")
public class WeightPanel extends Panel {

    private final EquipmentSelection selection;
    private final RetrieveGauntlets retrieveGauntlets;
    private final RetrieveHelms retrieveHelms;
    private final RetrieveLeggings retrieveLeggings;
    private final RetrieveChests retrieveChests;
    private final ArmorOptimizerService optimizerService;
    private final BuildRepository buildRepository;
    private final PlayerRepository playerRepository; 

    // UI Components
    private Label weightLabel;
    private TextArea resultArea;
    private Choice bossChoice;
    private Button optimizeButton;
    private Button viewBuildsButton;
    private Button saveBuildButton;  

    // Variables to hold computed combos and boss
    private ArmorCombination fastCombo;
    private ArmorCombination mediumCombo;
    private String currentBossName;

    public WeightPanel(EquipmentSelection selection,
                       RetrieveGauntlets retrieveGauntlets,
                       RetrieveHelms retrieveHelms,
                       RetrieveLeggings retrieveLeggings,
                       RetrieveChests retrieveChests,
                       ArmorOptimizerService optimizerService,
                       BuildRepository buildRepository,
                       PlayerRepository playerRepository) {  
        this.selection = selection;
        this.retrieveGauntlets = retrieveGauntlets;
        this.retrieveHelms = retrieveHelms;
        this.retrieveLeggings = retrieveLeggings;
        this.retrieveChests = retrieveChests;
        this.optimizerService = optimizerService;
        this.buildRepository = buildRepository;
        this.playerRepository = playerRepository;

        setLayout(new BorderLayout());

        // --- TOP: Weight label ---
        Panel topPanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        weightLabel = new Label("Equipped: 0 / 0");
        topPanel.add(weightLabel);
        add(topPanel, BorderLayout.NORTH);

        // --- CENTER: Results text area ---
        resultArea = new TextArea(12, 60);
        resultArea.setEditable(false);
        add(resultArea, BorderLayout.CENTER);

        // --- BOTTOM: Boss selection and buttons ---
        Panel bottomPanel = new Panel(new FlowLayout(FlowLayout.CENTER));

        Label bossLabel = new Label("Select Boss:");
        bottomPanel.add(bossLabel);

        bossChoice = new Choice();
        for (BossProfile bp : BossProfiles.PROFILES) {
            bossChoice.add(bp.getBossName());
        }
        bottomPanel.add(bossChoice);

        optimizeButton = new Button("Optimize Armor");
        optimizeButton.addActionListener(e -> runOptimization());
        bottomPanel.add(optimizeButton);

        viewBuildsButton = new Button("View Saved Builds");
        bottomPanel.add(viewBuildsButton);

        saveBuildButton = new Button("Save Build(s)");
        saveBuildButton.addActionListener(e -> saveBuilds());
        bottomPanel.add(saveBuildButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public Button getViewBuildsButton() {
        return viewBuildsButton;
    }

    public void updateDisplay() {
        double total = selection.getTotalWeight();
        double max = selection.getMaxEquipLoad();
        weightLabel.setText("Equipped: " + total + " / " + max);
    }

    private void runOptimization() {
        resultArea.setText(""); 
        
        currentBossName = bossChoice.getSelectedItem();
        BossProfile boss = findBossByName(currentBossName);
        if (boss == null) {
            appendResult("ERROR: Boss profile not found: " + currentBossName);
            return;
        }

        List<Item> chests = retrieveChests.getChests();
        List<Item> gauntlets = retrieveGauntlets.getGauntlets();
        List<Item> helms = retrieveHelms.getHelms();
        List<Item> leggings = retrieveLeggings.getLeggings();

        double currentWeight = selection.getTotalWeight();
        double maxLoad = selection.getMaxEquipLoad();

        fastCombo = optimizerService.optimizeFastRollArmor(
            boss, chests, gauntlets, helms, leggings, currentWeight, maxLoad
        );
        mediumCombo = optimizerService.optimizeMediumRollArmor(
            boss, chests, gauntlets, helms, leggings, currentWeight, maxLoad
        );

        displayCombo(fastCombo, "Fast Roll");
        displayCombo(mediumCombo, "Medium Roll");
    }

    private BossProfile findBossByName(String name) {
        for (BossProfile bp : BossProfiles.PROFILES) {
            if (bp.getBossName().equalsIgnoreCase(name)) {
                return bp;
            }
        }
        return null;
    }

    private void displayCombo(ArmorCombination combo, String rollType) {
        if (combo == null) {
            appendResult("\nNo viable " + rollType + " armor combination found.\n");
            return;
        }
        appendResult("\nBest " + rollType + " armor combination:");
        appendResult("  Chest:    " + combo.getChest().getItemName());
        appendResult("  Gauntlets:" + combo.getGauntlets().getItemName());
        appendResult("  Helmet:   " + combo.getHelmet().getItemName());
        appendResult("  Leggings: " + combo.getLeggings().getItemName());
        appendResult("  Total Armor Weight: " + combo.getTotalWeight());
    }

    private void appendResult(String text) {
        resultArea.append(text + "\n");
    }

    /**
     * Saves the optimized builds to the database.
     * It looks up (or creates) a Player record using the character name stored in the selection.
     */
    private void saveBuilds() {
        if (fastCombo == null && mediumCombo == null) {
            appendResult("No build has been optimized to save.");
            return;
        }
        
        // Retrieve the character name from the shared EquipmentSelection.
        String characterName = selection.getCharacterName();
        if (characterName == null || characterName.trim().isEmpty()) {
            appendResult("Character name is required to save build.");
            return;
        }
        
        // Look up the Player by character name. If not found, create a new Player record.
        Player player = playerRepository.findByPlayerName(characterName)
            .orElseGet(() -> {
                Player newPlayer = Player.builder().playerName(characterName).build();
                return playerRepository.save(newPlayer);
            });
        
        // Save the Fast Roll build if available.
        if (fastCombo != null) {
            Build buildFast = Build.builder()
                .rollType("FAST")
                .bossName(currentBossName)
                .totalWeight(fastCombo.getTotalWeight())
                .description(resultArea.getText())
                .userId(player.getPlayerId())  // Save using the player's id.
                .player(player)
                .build();
            buildRepository.save(buildFast);
            appendResult("Fast Roll build saved with ID: " + buildFast.getBuildId());
        }
        
        // Save the Medium Roll build if available.
        if (mediumCombo != null) {
            Build buildMedium = Build.builder()
                .rollType("MEDIUM")
                .bossName(currentBossName)
                .totalWeight(mediumCombo.getTotalWeight())
                .description(resultArea.getText())
                .userId(player.getPlayerId())
                .player(player)
                .build();
            buildRepository.save(buildMedium);
            appendResult("Medium Roll build saved with ID: " + buildMedium.getBuildId());
        }
    }
}
