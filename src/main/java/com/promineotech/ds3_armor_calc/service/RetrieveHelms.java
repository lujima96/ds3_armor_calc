package com.promineotech.ds3_armor_calc.service;

import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.repositories.HelmRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetrieveHelms {

    private final HelmRepository helmRepository;

    @Autowired
    public RetrieveHelms(HelmRepository helmRepository) {
        this.helmRepository = helmRepository;
    }


    public List<Item> getHelms() {
        return helmRepository.findByItemTypeAndArmorSlot("armor", "helmets");
    }
}
