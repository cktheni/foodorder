package com.training.orderfood.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.training.orderfood.entity.FoodItemProfile;

import java.io.IOException;
import java.util.List;

public class FoodItemProfileListSerializer extends StdSerializer<List<FoodItemProfile>> {

    public FoodItemProfileListSerializer() {
        this(null);
    }

    public FoodItemProfileListSerializer(Class<List<FoodItemProfile>> t) {
        super(t);
    }

    @Override
    public void serialize(List<FoodItemProfile> foodItemProfiles, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        for (FoodItemProfile foodItemProfile : foodItemProfiles) {
            foodItemProfile.setVendorProfile(null);
        }
        jsonGenerator.writeObject(foodItemProfiles);
    }
}