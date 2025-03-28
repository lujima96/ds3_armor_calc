package com.promineotech.ds3_armor_calc.dto;

import com.promineotech.ds3_armor_calc.entities.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArmorCombination {
    private Item chest;
    private Item gauntlets;
    private Item helmet;
    private Item leggings;
    private double totalWeight;
    private double reductionScore;
    
    /**
     * Computes the total weight based on the individual armor piece weights.
     * This can be useful if you want to recalculate the weight of a combination.
     */
    public void computeTotalWeight() {
        double weight = 0.0;
        if (chest != null) {
            weight += chest.getWeight();
        }
        if (gauntlets != null) {
            weight += gauntlets.getWeight();
        }
        if (helmet != null) {
            weight += helmet.getWeight();
        }
        if (leggings != null) {
            weight += leggings.getWeight();
        }
        this.totalWeight = weight;
    }
}
