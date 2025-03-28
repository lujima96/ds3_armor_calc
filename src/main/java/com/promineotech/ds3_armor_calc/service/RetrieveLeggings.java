package com.promineotech.ds3_armor_calc.service;

import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.repositories.LeggingsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieveLeggings {

    private final LeggingsRepository leggingsRepository;

    @Autowired
    public RetrieveLeggings(LeggingsRepository leggingsRepository) {
        this.leggingsRepository = leggingsRepository;
    }

    public List<Item> getLeggings() {
        return leggingsRepository.findByItemTypeAndArmorSlot("armor", "leggings");
    }
}
