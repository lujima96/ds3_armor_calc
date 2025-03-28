package com.promineotech.ds3_armor_calc.controller;


import com.promineotech.ds3_armor_calc.entities.Player;
import com.promineotech.ds3_armor_calc.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/players")

public class PlayerController {
	
	@Autowired
	private PlayerRepository playerRepository;

	// GET all players
	@GetMapping
	public List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}
	
	// Getting specific player by ID
	 @GetMapping("/{id}")
	 public ResponseEntity<Player> getPlayerId(@PathVariable Long id){
	        return playerRepository.findById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	 }
	 
	 // POST create a new player
	 @PostMapping
	 public Player createPlayer(@RequestBody Player player) {
		 return playerRepository.save(player);
	 }
	 
	 // PUT updating an existing player
	 @PutMapping("/{id}")
	 public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player updatedPlayer) {
	        return playerRepository.findById(id)
	                .map(existingPlayer -> {
	                    existingPlayer.setPlayerName(updatedPlayer.getPlayerName());
	                    Player saved = playerRepository.save(existingPlayer);
	                    return ResponseEntity.ok(saved);
	                })
	                .orElse(ResponseEntity.notFound().build());
	 }
	 
	 // DELETE a player bv ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
	        return playerRepository.findById(id)
	                .map(existingPlayer -> {
	                    playerRepository.delete(existingPlayer);
	                    return ResponseEntity.ok().<Void>build();
	                })
	                .orElse(ResponseEntity.notFound().build());
	    }
	 }

