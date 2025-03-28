package com.promineotech.ds3_armor_calc.calculator;

import com.promineotech.ds3_armor_calc.dto.BossProfile;
import com.promineotech.ds3_armor_calc.dto.ArmorCombination;
import com.promineotech.ds3_armor_calc.entities.Item;
import java.util.List;

public class ArmorOptimizer {

    /**
     * Calculates a reduction score for a given armor combination against the boss's damage profile.
     * A higher score means that the armor set is more effective at reducing the boss's damage.
     *
     * @param combination the armor combination (chest, gauntlets, helmet, leggings)
     * @param boss the boss profile containing the damage the boss deals in each category
     * @return a reduction score (higher is better)
     */
    public double calculateReductionScore(ArmorCombination combination, BossProfile boss) {
        // Retrieve each armor piece.
        Item chest = combination.getChest();
        Item gauntlets = combination.getGauntlets();
        Item helmet = combination.getHelmet();
        Item leggings = combination.getLeggings();
        
        // For each damage type, sum the reductions from all armor pieces.
        
        double physicalReduction = chest.getPhysical() + gauntlets.getPhysical() 
                                  + helmet.getPhysical() + leggings.getPhysical();
        double vsStrikeReduction = chest.getVsStrike() + gauntlets.getVsStrike() 
                                  + helmet.getVsStrike() + leggings.getVsStrike();
        double vsSlashReduction = chest.getVsSlash() + gauntlets.getVsSlash() 
                                  + helmet.getVsSlash() + leggings.getVsSlash();
        double vsThrustReduction = chest.getVsThrust() + gauntlets.getVsThrust() 
                                  + helmet.getVsThrust() + leggings.getVsThrust();
        double magicReduction = chest.getMagic() + gauntlets.getMagic() 
                                  + helmet.getMagic() + leggings.getMagic();
        double fireReduction = chest.getFire() + gauntlets.getFire() 
                                  + helmet.getFire() + leggings.getFire();
        double lightningReduction = chest.getLightning() + gauntlets.getLightning() 
                                  + helmet.getLightning() + leggings.getLightning();
        double darkReduction = chest.getDark() + gauntlets.getDark() 
                                  + helmet.getDark() + leggings.getDark();
        double bleedReduction = chest.getBleed() + gauntlets.getBleed() 
                                  + helmet.getBleed() + leggings.getBleed();
        double poisonReduction = chest.getPoison() + gauntlets.getPoison() 
                                  + helmet.getPoison() + leggings.getPoison();
        double frostReduction = chest.getFrost() + gauntlets.getFrost() 
                                  + helmet.getFrost() + leggings.getFrost();
        
       
        double score = 0.0;
        
        score += physicalReduction   * boss.getPhysical();
        score += vsStrikeReduction   * boss.getVsStrike();
        score += vsSlashReduction    * boss.getVsSlash();
        score += vsThrustReduction   * boss.getVsThrust();
        score += magicReduction      * boss.getMagic();
        score += fireReduction       * boss.getFire();
        score += lightningReduction  * boss.getLightning();
        score += darkReduction       * boss.getDark();
        score += bleedReduction      * boss.getBleed();
        score += poisonReduction     * boss.getPoison();
        score += frostReduction      * boss.getFrost();

        return score;
    }

    /**
     * Finds the optimal armor combination for fast roll.
     * Uses a threshold factor of 0.30.
     */
    public ArmorCombination optimizeFastRollArmor(BossProfile selectedBoss,
            List<Item> availableChests,
            List<Item> availableGauntlets,
            List<Item> availableHelms,
            List<Item> availableLeggings,
            double currentWeight,
            double maxLoad) {
        
        return optimizeArmorCombination(availableChests, availableGauntlets, availableHelms, availableLeggings,
                currentWeight, maxLoad, 0.30, selectedBoss);
    }

    /**
     * Finds the optimal armor combination for medium roll.
     * Uses a threshold factor of 0.70.
     */
    public ArmorCombination optimizeMediumRollArmor(BossProfile selectedBoss,
            List<Item> availableChests,
            List<Item> availableGauntlets,
            List<Item> availableHelms,
            List<Item> availableLeggings,
            double currentWeight,
            double maxLoad) {
        
        return optimizeArmorCombination(availableChests, availableGauntlets, availableHelms, availableLeggings,
                currentWeight, maxLoad, 0.70, selectedBoss);
    }

    /**
     * Helper method that iterates over all possible combinations of armor pieces and returns
     * the one with the highest reduction score that meets the weight threshold.
     *
     * @param chestList list of available chest items
     * @param gauntletsList list of available gauntlet items
     * @param helmetList list of available helmet items
     * @param leggingsList list of available legging items
     * @param currentWeight current equipped weight (other than armor)
     * @param maxLoad maximum equipment load
     * @param thresholdFactor factor (0.30 for fast roll, 0.70 for medium roll) to determine weight threshold
     * @param boss the boss profile against which to optimize
     * @return the best ArmorCombination or null if no combination qualifies
     */
    private ArmorCombination optimizeArmorCombination(
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
                            ArmorCombination combination = ArmorCombination.builder()
                                    .chest(chest)
                                    .gauntlets(gauntlets)
                                    .helmet(helmet)
                                    .leggings(leggings)
                                    .totalWeight(comboWeight)
                                    .reductionScore(0.0) 
                                    .build();
                            
                            double score = calculateReductionScore(combination, boss);
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
