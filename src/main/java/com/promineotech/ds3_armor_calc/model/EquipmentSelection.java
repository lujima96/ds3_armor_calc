package com.promineotech.ds3_armor_calc.model;

public class EquipmentSelection {
    private String characterName;
    private double maxEquipLoad;
    private double ringsWeight;
    private double weaponsWeight;
    private double shieldsWeight;
    
    public String getCharacterName() {
        return characterName;
    }
    
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    
    public double getMaxEquipLoad() {
        return maxEquipLoad;
    }
    
    public void setMaxEquipLoad(double maxEquipLoad) {
        this.maxEquipLoad = maxEquipLoad;
    }
    
    public double getRingsWeight() {
        return ringsWeight;
    }
    
    public void setRingsWeight(double ringsWeight) {
        this.ringsWeight = ringsWeight;
    }
    
    public double getWeaponsWeight() {
        return weaponsWeight;
    }
    
    public void setWeaponsWeight(double weaponsWeight) {
        this.weaponsWeight = weaponsWeight;
    }
    
    public double getShieldsWeight() {
        return shieldsWeight;
    }
    
    public void setShieldsWeight(double shieldsWeight) {
        this.shieldsWeight = shieldsWeight;
    }
    
    public double getTotalWeight() {
        return ringsWeight + weaponsWeight + shieldsWeight;
    }
    
    /**
     * Clears or resets all fields to zero (or null for characterName).
     */
    public void clear() {
        this.characterName = null;
        this.maxEquipLoad = 0.0;
        this.ringsWeight = 0.0;
        this.weaponsWeight = 0.0;
        this.shieldsWeight = 0.0;
    }
}
