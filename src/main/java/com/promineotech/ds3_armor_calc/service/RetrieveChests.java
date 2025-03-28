package com.promineotech.ds3_armor_calc.service;

import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.repositories.ChestRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetrieveChests {

    private final ChestRepository chestRepository;

    @Autowired
    public RetrieveChests(ChestRepository chestRepository) {
        this.chestRepository = chestRepository;
    }

    /**
     * Retrieves all armor items with item_type "armor" and armor_slot "chests".
     */
    public List<Item> getChests() {
        // Updated to lowercase "armor"
        return chestRepository.findByItemTypeAndArmorSlot("armor", "chests");
    }
}
