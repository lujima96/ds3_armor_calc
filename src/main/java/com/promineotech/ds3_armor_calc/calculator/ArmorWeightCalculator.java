package com.promineotech.ds3_armor_calc.calculator;

public class ArmorWeightCalculator {
    
    private double currentWeight; // Weight from rings, weapons, shields, etc.
    private double maxLoad;       // Maximum equip load provided by the user

    public ArmorWeightCalculator(double currentWeight, double maxLoad) {
        this.currentWeight = currentWeight;
        this.maxLoad = maxLoad;
    }

    /**
     * Checks if adding the candidate armor combination (armorWeight) to the current weight
     * keeps the total weight under the fast roll threshold (30% of maxLoad).
     * 
     * @param armorWeight the weight of the candidate armor combination.
     * @return true if total weight is within fast roll constraint, false otherwise.
     */
    public boolean isFastRoll(double armorWeight) {
        return (currentWeight + armorWeight) <= 0.30 * maxLoad;
    }
    
    /**
     * Checks if adding the candidate armor combination (armorWeight) to the current weight
     * keeps the total weight under the medium roll threshold (70% of maxLoad).
     * 
     * @param armorWeight the weight of the candidate armor combination.
     * @return true if total weight is within medium roll constraint, false otherwise.
     */
    public boolean isMediumRoll(double armorWeight) {
        return (currentWeight + armorWeight) <= 0.70 * maxLoad;
    }
    
    /**
     * Returns the remaining weight capacity for a fast roll.
     * 
     * @return remaining weight capacity (fast roll).
     */
    public double getRemainingCapacityFastRoll() {
        return 0.30 * maxLoad - currentWeight;
    }
    
    /**
     * Returns the remaining weight capacity for a medium roll.
     * 
     * @return remaining weight capacity (medium roll).
     */
    public double getRemainingCapacityMediumRoll() {
        return 0.70 * maxLoad - currentWeight;
    }
    
    /**
     * Adds additional weight to the current equipped weight.
     * 
     * @param weight the weight to add.
     */
    public void addToCurrentWeight(double weight) {
        this.currentWeight += weight;
    }
    
    // Getters (if needed)
    public double getCurrentWeight() {
        return currentWeight;
    }
    
    public double getMaxLoad() {
        return maxLoad;
    }
}
