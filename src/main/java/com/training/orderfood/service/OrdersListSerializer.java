package com.training.orderfood.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.training.orderfood.entity.FoodItemProfile;
import com.training.orderfood.entity.Orders;

import java.io.IOException;
import java.util.List;

public class OrdersListSerializer extends StdSerializer<List<Orders>>  {

    public OrdersListSerializer() {
        this(null);
    }
    public OrdersListSerializer(Class<List<Orders>> t) {
        super(t);
    }


    @Override
    public void serialize(List<Orders> orders, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        for (Orders order : orders) {
            order.setUserProfile(null);
        }
        jsonGenerator.writeObject(orders);
    }
}