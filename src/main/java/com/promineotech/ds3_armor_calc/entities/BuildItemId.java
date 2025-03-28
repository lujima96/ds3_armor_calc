package com.promineotech.ds3_armor_calc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildItemId implements Serializable {

    @Column(name = "build_id")
    private Long buildId;

    @Column(name = "item_id")
    private Long itemId;
}
