package com.promineotech.ds3_armor_calc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.promineotech.ds3_armor_calc.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findByItemType(String string);
}

