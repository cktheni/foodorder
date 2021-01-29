package com.training.orderfood.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.training.orderfood.service.FoodItemProfileListSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "VENDOR_PROFILE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VendorProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="VENDOR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    @Column(name = "VENDOR_NAME",nullable = false, length = 100)
    private String vendorName;

    @Column(name = "VENDOR_BANK_NAME",nullable = false, length = 50)
    private String vendorBankName;

    @Column(name = "VENDOR_BANK_CODE",nullable = false, length = 45)
    private String vendorBankCode;

    @Column(name = "VENDOR_ACCOUNT_NUMBER",nullable = false, length = 45)
    private String vendorAccountNumber;

    @Column(name = "VENDOR_ACCOUNT_CURRENCY",nullable = false, length = 45)
    private String vendorAccountCurrency;

   @OneToMany(mappedBy = "vendorProfile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JsonSerialize(using = FoodItemProfileListSerializer.class)
   private List<FoodItemProfile> foodItemProfile;

}
