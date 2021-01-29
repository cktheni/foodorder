package com.training.orderfood.service;

import com.training.orderfood.entity.FoodItemProfile;
import com.training.orderfood.entity.VendorProfile;
import com.training.orderfood.repository.VendorProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class MetaDataService {

    @Autowired
    VendorProfileRepository vendorProfileRepository;

    public Map getListOfVendorsAlongWithFoods() throws Exception {
        try {


            Map m = Optional.ofNullable(vendorProfileRepository.findAll()).orElseGet(Collections::emptyList).stream().collect(Collectors.groupingBy(VendorProfile::getVendorId));
            System.out.println("Map ="+m);
            return m;
        } catch (Exception e) {
            throw new Exception("getAllFoodItemsByVendor Exception:" + e.getMessage(), e);
        }
    }


}
