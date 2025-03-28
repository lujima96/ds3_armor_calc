package com.promineotech.ds3_armor_calc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BossProfile {
    private String bossName;
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
}