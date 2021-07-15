package com.app.cosmetics.core.orderitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM order_item where order_item.created > CURRENT_DATE - INTERVAL '6 months'")
    List<OrderItem> findAllBeforeSixMonths();
}
