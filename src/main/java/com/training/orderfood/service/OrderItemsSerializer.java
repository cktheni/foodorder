package com.training.orderfood.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.training.orderfood.entity.OrderItems;

import java.io.IOException;
import java.util.List;

public class OrderItemsSerializer extends StdSerializer<List<OrderItems>>
{

   protected OrderItemsSerializer(Class<List<OrderItems>> t) {
        super(t);
    }

    protected OrderItemsSerializer() {
        this(null);
    }

    @Override
    public void serialize(List<OrderItems> orderItems, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        for (OrderItems orderItem : orderItems) {
            orderItem.setOrders(null);
        }
        jsonGenerator.writeObject(orderItems);
    }

}