package com.promineotech.ds3_armor_calc.service;

import com.promineotech.ds3_armor_calc.entities.Build;
import com.promineotech.ds3_armor_calc.dto.ArmorCombination;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildService {

    @Autowired
    private BuildRepository buildRepository;

    public Build saveBuild(String rollType, String bossName, ArmorCombination combo) {
        // Create a textual description:
        String desc = String.format(
            "Chest: %s, Gauntlets: %s, Helmet: %s, Leggings: %s, Weight=%.1f",
            combo.getChest().getItemName(),
            combo.getGauntlets().getItemName(),
            combo.getHelmet().getItemName(),
            combo.getLeggings().getItemName(),
            combo.getTotalWeight()
        );

        Build build = Build.builder()
            .rollType(rollType)
            .bossName(bossName)
            .totalWeight(combo.getTotalWeight())
            .description(desc)
            .build();

        return buildRepository.save(build);
    }

    public Iterable<Build> findAllBuilds() {
        return buildRepository.findAll();
    }

    public void deleteBuild(Long buildId) {
        buildRepository.deleteById(buildId);
    }
}
