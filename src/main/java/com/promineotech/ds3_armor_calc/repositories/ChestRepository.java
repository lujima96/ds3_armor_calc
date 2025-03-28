package com.promineotech.ds3_armor_calc.repositories;

import com.promineotech.ds3_armor_calc.entities.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChestRepository extends JpaRepository<Item, Long> {

    /**
     * Retrieves all armor items that match the given item type and armor slot.
     */
    List<Item> findByItemTypeAndArmorSlot(String itemType, String armorSlot);
}
