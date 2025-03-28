package com.promineotech.ds3_armor_calc.controller;

import com.promineotech.ds3_armor_calc.entities.Build;
import com.promineotech.ds3_armor_calc.entities.BuildItem;
import com.promineotech.ds3_armor_calc.entities.Item;
import com.promineotech.ds3_armor_calc.repositories.BuildItemRepository;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import com.promineotech.ds3_armor_calc.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/build-items")
public class BuildItemController {

    @Autowired
    private BuildItemRepository buildItemRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private ItemRepository itemRepository;

    // POST: Add an Item to a Build
    // The DB will throw a uniqueness constraint violation if (build_id, item_id) is duplicated
    @PostMapping("/add")
    public ResponseEntity<BuildItem> addItemToBuild(@RequestParam Long buildId, 
                                                    @RequestParam Long itemId) {
        Optional<Build> buildOpt = buildRepository.findById(buildId);
        Optional<Item> itemOpt = itemRepository.findById(itemId);
        
        if (buildOpt.isPresent() && itemOpt.isPresent()) {
            // Create the BuildItem. 
            BuildItem buildItem = BuildItem.builder()
                .build(buildOpt.get())
                .item(itemOpt.get())
                .build();
            
            try {
                // Attempt to insert. If (build_id, item_id) is already in the table,
                // the DB will raise an exception due to the unique constraint.
                BuildItem saved = buildItemRepository.save(buildItem);
                return ResponseEntity.ok(saved);
            } 
            catch (DataIntegrityViolationException ex) {
                // We can handle the unique constraint violation here
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

    // DELETE: Remove an Item from a Build using the custom query
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeItemFromBuild(@RequestParam Long buildId,
                                                    @RequestParam Long itemId) {
        BuildItem found = buildItemRepository.findByBuildAndItem(buildId, itemId);
        if (found != null) {
            buildItemRepository.delete(found);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // GET: List all Items for a given Build
    @GetMapping("/build/{buildId}")
    public ResponseEntity<List<Item>> getItemsForBuild(@PathVariable Long buildId) {
        Optional<Build> buildOpt = buildRepository.findById(buildId);
        if (buildOpt.isPresent()) {
            Build build = buildOpt.get();
            // The Build entity has a one-to-many relationship to BuildItem,
            // so we can map them to Items
            List<Item> items = build.getBuildItems()
                .stream()
                .map(BuildItem::getItem)
                .collect(Collectors.toList());
            return ResponseEntity.ok(items);
        }
        return ResponseEntity.notFound().build();
    }
}
