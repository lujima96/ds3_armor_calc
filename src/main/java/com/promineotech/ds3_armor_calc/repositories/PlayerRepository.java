package com.promineotech.ds3_armor_calc.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.promineotech.ds3_armor_calc.entities.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByPlayerName(String characterName);
}
