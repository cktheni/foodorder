package com.training.orderfood.endpoint;

import com.training.orderfood.entity.*;
import com.training.orderfood.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/foodorders")
public class OrderFoodEndPoint {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public List<UserProfile> getAllUsers()
    {
        return orderService.getAllUsers();
    }

    @RequestMapping(value = "/getAllVendors", method = RequestMethod.GET)
    public List<VendorProfile>  getAllVendors(){
        return orderService.getAllVendors();
    }

    @RequestMapping(value = "/getAllFoodItems", method = RequestMethod.GET)
    public List<FoodItemProfile> getAllFoodItems()
    {
        return orderService.getAllFoodItems();
    }

//-----------------------------------------//

    @RequestMapping(value = "/searchFoodItemName", method = RequestMethod.GET)
    public List<FoodItemProfile>  getFoodItemNameLike(String foodItemName) {
        return orderService.getFoodItemNameLike(foodItemName);

    }

    /*@RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public ResponseEntity placeOrder(@RequestBody Orders orders ) throws Exception {

        orders = orderService.paceOrders(orders);

        if (orders != null  ) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(orders);
        }
    }*/

    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST)
    public ResponseEntity placeOrder(@RequestBody Orders orders ) throws Exception {

        orders = orderService.paceOrders(orders);

        if (orders != null  ) {
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(orders);
        }
    }

    @RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
    public List<Orders>  getOrderHistory(Long userId) {
        return orderService.getOrderHistory(userId);

    }


}
