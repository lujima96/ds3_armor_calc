package com.promineotech.ds3_armor_calc.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "build_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildItem {

    @EmbeddedId
    private BuildItemId id;

    /**
     * Many build-items can be linked to a single build.
     */
    @ManyToOne
    @MapsId("buildId")   
    @JoinColumn(name = "build_id")
    private Build build;

    /**
     * Many build-items can refer to a single item.
     */
    @ManyToOne
    @MapsId("itemId")   
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "armor_slot")
    private String armorSlot;

    @Column(name = "slot_type")
    private String slotType;
}
