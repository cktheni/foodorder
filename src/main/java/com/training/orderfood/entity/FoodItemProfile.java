package com.training.orderfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.training.orderfood.entity.id.FoodItemProfileId;
import com.training.orderfood.service.OrderItemsSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@IdClass(FoodItemProfileId.class)
@Table(name = "FOOD_ITEM_PROFILE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FoodItemProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="FOOD_ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodItemId;

    @Id
    @Column(name="VENDOR_ID",nullable = false)
    private Long vendorId;

    @Column(name = "FOOD_ITEM_NAME",nullable = false, length = 100)
    private String foodItemName;

    @Column(name = "PRICE",nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumns(value = {@JoinColumn(name = "VENDOR_ID", nullable = false, insertable = false, updatable = false)})
    @JsonIgnore
    private VendorProfile vendorProfile;


   @Transient
   private String vendorName;


  /*  @OneToOne(mappedBy = "foodItemProfile", cascade = CascadeType.ALL)
    private OrderItems orderItems;*/

}
