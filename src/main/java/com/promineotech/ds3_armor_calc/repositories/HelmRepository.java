package com.promineotech.ds3_armor_calc.repositories;

import com.promineotech.ds3_armor_calc.entities.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HelmRepository extends JpaRepository<Item, Long> {

    /**
     * Retrieves all armor items matching itemType and armorSlot.
     */
    List<Item> findByItemTypeAndArmorSlot(String itemType, String armorSlot);
}
