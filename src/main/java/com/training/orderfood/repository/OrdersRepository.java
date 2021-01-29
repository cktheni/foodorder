package com.training.orderfood.repository;

import com.training.orderfood.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

//    @Query("select COALESCE(max(a.orderId),0)+1 from Orders a")
//    public Long findMaxOrderId();

    @Query("select a from Orders a where a.userId=?1 order by a.orderDateTime Desc")
    List<Orders> findOrderHistory(Long userId);

    //List<Orders> findTop5ByOrderByOrderDateTimeDesc();


}
