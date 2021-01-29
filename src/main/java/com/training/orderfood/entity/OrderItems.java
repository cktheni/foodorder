package com.training.orderfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ORDER_ITEMS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderItems implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name="ORDER_ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;


    @Column(name="ORDER_ID", nullable = false)
    private Long orderId;


    @Column(name="VENDOR_ID", nullable = false)
    private Long vendorId;

    @Column(name="FOOD_ITEM_ID", nullable = false)
    private Long foodItemId;

    @Column(name = "QUANTITY",nullable = false)
    private Double quantity;

    @Column(name = "IS_PAYMENT_SUCCESS",nullable = false, length =5)
    private String isPaymentSuccess;


    @ManyToOne
    @JoinColumns(value = {@JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false)})
    @JsonIgnore
    private Orders orders;


    /*@OneToOne
    @JoinColumns(value = {@JoinColumn(name = "FOOD_ITEM_ID", nullable = false, insertable = false, updatable = false)})
    private FoodItemProfile foodItemProfile;*/

    @Transient
    private String vendorName;

    @Transient
    private String foodItemName;


}
