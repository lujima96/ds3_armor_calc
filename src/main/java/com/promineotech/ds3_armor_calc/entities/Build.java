package com.promineotech.ds3_armor_calc.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "build")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Build {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "build_id")
    private Long buildId;

    @Column(name = "roll_type")
    private String rollType;  

    @Column(name = "boss_name")
    private String bossName;

    @Column(name = "total_weight")
    private Double totalWeight;

    @Column(name = "description", length = 1000)
    private String description; 


    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Many builds can belong to one Player.
     * The foreign key 'player_id' in the build table references player.player_id.
     */
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    /**
     * One build can have many BuildItems.
     */
    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BuildItem> buildItems;
}
