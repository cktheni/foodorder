package com.training.orderfood.repository;

import com.training.orderfood.entity.FoodItemProfile;
import com.training.orderfood.entity.id.FoodItemProfileId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodItemProfileRepository extends JpaRepository<FoodItemProfile, FoodItemProfileId> {

   @Query("select a.foodItemId, a.foodItemName, a.vendorId, b.vendorName, a.price from  FoodItemProfile a, VendorProfile b where  a.vendorId = b.vendorId and  upper(a.foodItemName) like upper(?1)")
   List<FoodItemProfile> findByFoodItemName(String foodItemName);

  // @Query(value="select a.*, b.vendor_name from food_item_profile a inner join vendor_profile b on  a.vendor_id=b.vendor_id and  upper(a.food_item_name) like upper(?1)" , nativeQuery = true)
  // List<FoodItemProfile> findByFoodItemName(String foodItemName);

   List<FoodItemProfile> findByFoodItemNameLike(String foodItemName);

   FoodItemProfile  findByFoodItemId(Long footItemId);

}
