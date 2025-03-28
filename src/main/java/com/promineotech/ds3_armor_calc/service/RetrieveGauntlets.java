package com.promineotech.ds3_armor_calc.service;

import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.repositories.GauntletRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieveGauntlets {

    private final GauntletRepository gauntletRepository;

    @Autowired
    public RetrieveGauntlets(GauntletRepository gauntletRepository) {
        this.gauntletRepository = gauntletRepository;
    }


    public List<Item> getGauntlets() {
        return gauntletRepository.findByItemTypeAndArmorSlot("armor", "gauntlets");
    }
}
