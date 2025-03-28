package com.promineotech.ds3_armor_calc.repositories;

import com.promineotech.ds3_armor_calc.entities.Build;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildRepository extends JpaRepository<Build, Long> {
   
}
