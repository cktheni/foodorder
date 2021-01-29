package com.training.orderfood.repository;

import com.training.orderfood.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    @Query("select COALESCE(max(a.orderItemId),0)+1 from OrderItems a")
    public Long findMaxOrderItemId();
}
