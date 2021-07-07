package com.app.cosmetics.core.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM item ORDER BY created DESC")
    List<Item> findNewItems();

    @Query(nativeQuery = true, value = "SELECT * FROM item ORDER BY sold DESC")
    List<Item> findHotItems();
}
