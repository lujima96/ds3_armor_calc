package com.promineotech.ds3_armor_calc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.promineotech.ds3_armor_calc.entities.BuildItem;

@Repository
public interface BuildItemRepository extends JpaRepository<BuildItem, Long> {

    @Modifying
    @Query("DELETE FROM BuildItem bi WHERE bi.build.buildId = :buildId AND bi.item.itemId = :itemId")
    void deleteByBuildAndItem(Long buildId, Long itemId);


    @Query("SELECT bi FROM BuildItem bi WHERE bi.build.buildId = :buildId AND bi.item.itemId = :itemId")
    BuildItem findByBuildAndItem(Long buildId, Long itemId);
}
