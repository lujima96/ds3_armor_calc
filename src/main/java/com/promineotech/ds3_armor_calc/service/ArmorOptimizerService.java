package com.promineotech.ds3_armor_calc.service;

import com.promineotech.ds3_armor_calc.calculator.BruteForce;
import com.promineotech.ds3_armor_calc.dto.ArmorCombination;
import com.promineotech.ds3_armor_calc.dto.BossProfile;
import com.promineotech.ds3_armor_calc.entities.Item;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ArmorOptimizerService {
    private final BruteForce bruteForce = new BruteForce();
    
    public ArmorCombination optimizeFastRollArmor(BossProfile boss,
            List<Item> chests,
            List<Item> gauntlets,
            List<Item> helms,
            List<Item> leggings,
            double currentWeight,
            double maxLoad) {
        return bruteForce.optimizeArmorCombination(chests, gauntlets, helms, leggings,
                currentWeight, maxLoad, 0.30, boss);
    }
    
    public ArmorCombination optimizeMediumRollArmor(BossProfile boss,
            List<Item> chests,
            List<Item> gauntlets,
            List<Item> helms,
            List<Item> leggings,
            double currentWeight,
            double maxLoad) {
        return bruteForce.optimizeArmorCombination(chests, gauntlets, helms, leggings,
                currentWeight, maxLoad, 0.70, boss);
    }
}
