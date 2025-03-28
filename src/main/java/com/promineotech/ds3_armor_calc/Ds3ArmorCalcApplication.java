package com.promineotech.ds3_armor_calc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.promineotech.ds3_armor_calc.gui.ArmorCalculatorGUI;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import com.promineotech.ds3_armor_calc.repositories.PlayerRepository;
import com.promineotech.ds3_armor_calc.service.RetrieveChests;
import com.promineotech.ds3_armor_calc.service.RetrieveGauntlets;
import com.promineotech.ds3_armor_calc.service.RetrieveHelms;
import com.promineotech.ds3_armor_calc.service.RetrieveLeggings;
import com.promineotech.ds3_armor_calc.service.ArmorOptimizerService;

@SpringBootApplication
public class Ds3ArmorCalcApplication {

    @Autowired
    private RetrieveGauntlets retrieveGauntlets;

    @Autowired
    private RetrieveHelms retrieveHelms;

    @Autowired
    private RetrieveLeggings retrieveLeggings;

    @Autowired
    private RetrieveChests retrieveChests;

    @Autowired
    private ArmorOptimizerService optimizerService;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private PlayerRepository playerRepository; 

    public static void main(String[] args) {
        SpringApplication.run(Ds3ArmorCalcApplication.class, args);
    }

    // Start GUI after DB is filled
    @EventListener(ApplicationReadyEvent.class)
    public void startGui() {
        new ArmorCalculatorGUI(
            retrieveGauntlets,
            retrieveHelms,
            retrieveLeggings,
            retrieveChests,
            optimizerService,
            buildRepository,
            playerRepository
        );
    }
}
