package com.promineotech.ds3_armor_calc.controller;

import com.promineotech.ds3_armor_calc.entities.Build;
import com.promineotech.ds3_armor_calc.repositories.BuildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/builds")
public class BuildController {

  @Autowired
  private BuildRepository buildRepository;

  // GET: list all builds in the DB
  @GetMapping
  public ResponseEntity<?> getAllBuilds() {
    return ResponseEntity.ok(buildRepository.findAll());
  }

  // POST: create a new build

  @PostMapping
  public ResponseEntity<Build> createBuild(@RequestBody Build build) {
    Build saved = buildRepository.save(build);
    return ResponseEntity.ok(saved);
  }

  // GET: retrieve a specific build
  @GetMapping("/{buildId}")
  public ResponseEntity<Build> getBuildById(@PathVariable Long buildId) {
    return buildRepository.findById(buildId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // DELETE: remove a build
  @DeleteMapping("/{buildId}")
  public ResponseEntity<Void> deleteBuild(@PathVariable Long buildId) {
    if (buildRepository.existsById(buildId)) {
      buildRepository.deleteById(buildId);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();
  }
}
