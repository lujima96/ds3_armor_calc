package com.promineotech.ds3_armor_calc.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @JsonProperty("name")
    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_type")
    private String itemType;

    // For weapon items
    @Column(name = "weapon_category")
    private String weaponCategory;

    // For armor items
    @Column(name = "armor_slot")
    private String armorSlot;

    // For shield items
    @Column(name = "shield_category")
    private String shieldCategory;

    private Double weight;
    private Double physical;
    private Double vsStrike;
    private Double vsSlash;
    private Double vsThrust;
    private Double magic;
    private Double fire;
    private Double lightning;
    private Double dark;
    private Double bleed;
    private Double poison;
    private Double frost;
    private Double curse;
    private Double poise;
    private Double durability;


    @JsonProperty("equipmentLoadBonus")
    private Double equipmentLoadBonus;


    @JsonProperty("equipmentLoadBonusPercent")
    private Double equipmentLoadBonusPercent;


    public void setWeaponCategory(String subType) {
        this.weaponCategory = subType;
    }

    public void setArmorSlot(String slot) {
        this.armorSlot = slot;
    }

    public void setShieldCategory(String shieldType) {
        this.shieldCategory = shieldType;
    }
}
