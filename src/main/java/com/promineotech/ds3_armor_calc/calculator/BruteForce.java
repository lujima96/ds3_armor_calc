package com.promineotech.ds3_armor_calc.calculator;

import com.promineotech.ds3_armor_calc.dto.ArmorCombination;
import com.promineotech.ds3_armor_calc.dto.BossProfile;
import com.promineotech.ds3_armor_calc.entities.Item;
import java.util.List;

public class BruteForce {

    private ArmorOptimizer optimizer = new ArmorOptimizer();

    /**
     * Iterates over all possible combinations of chest, gauntlets, helmet, and leggings,
     * and returns the combination with the highest reduction score that meets the weight constraint.
     *
     * @param chestList      List of chest armor items.
     * @param gauntletsList  List of gauntlet armor items.
     * @param helmetList     List of helmet armor items.
     * @param leggingsList   List of legging armor items.
     * @param currentWeight  The current equipped weight (rings, weapons, shields, etc.).
     * @param maxLoad        The maximum equip load as entered by the user.
     * @param thresholdFactor 0.30 for fast roll, 0.70 for medium roll.
     * @param boss           The selected boss profile.
     * @return the best ArmorCombination meeting the weight constraint, or null if none qualifies.
     */
    public ArmorCombination optimizeArmorCombination(
            List<Item> chestList,
            List<Item> gauntletsList,
            List<Item> helmetList,
            List<Item> leggingsList,
            double currentWeight,
            double maxLoad,
            double thresholdFactor,
            BossProfile boss) {
        
        double threshold = thresholdFactor * maxLoad;
        ArmorCombination bestCombination = null;
        double bestScore = -Double.MAX_VALUE;

        for (Item chest : chestList) {
            for (Item gauntlets : gauntletsList) {
                for (Item helmet : helmetList) {
                    for (Item leggings : leggingsList) {
                        double comboWeight = chest.getWeight() + gauntlets.getWeight() 
                                             + helmet.getWeight() + leggings.getWeight();
                        double totalWeight = currentWeight + comboWeight;
                        if (totalWeight <= threshold) {
                            // Build the armor combination.
                            ArmorCombination combination = ArmorCombination.builder()
                                    .chest(chest)
                                    .gauntlets(gauntlets)
                                    .helmet(helmet)
                                    .leggings(leggings)
                                    .totalWeight(comboWeight)
                                    .reductionScore(0.0) 
                                    .build();
                            
                            // Calculate reduction score for this combination against the boss.
                            double score = optimizer.calculateReductionScore(combination, boss);
                            if (score > bestScore) {
                                bestScore = score;
                                bestCombination = combination;
                            }
                        }
                    }
                }
            }
        }
        
        if (bestCombination != null) {
            bestCombination.setReductionScore(bestScore);
        }
        
        return bestCombination;
    }
}
