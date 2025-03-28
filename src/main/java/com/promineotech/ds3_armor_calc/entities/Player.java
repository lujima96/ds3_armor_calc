package com.promineotech.ds3_armor_calc.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  
    private Long id;  

    @Column(name = "player_name", nullable = false)
    private String playerName;

    /**
     * One player can have many builds. The foreign key in 'build' is 'user_id'.
     */
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Build> builds;

    @Transient
    public Long getPlayerId() {
        return id;
    }
}
