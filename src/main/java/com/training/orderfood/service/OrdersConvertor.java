package com.training.orderfood.service;

import com.fasterxml.jackson.databind.util.StdConverter;
import com.training.orderfood.entity.Orders;
import com.training.orderfood.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrdersConvertor extends StdConverter<Long, Orders> {

    @Autowired
    OrdersRepository ordersRepository;

    @Override
    public Orders convert(Long value) {
        return ordersRepository.findById(value).get();
    }
}
