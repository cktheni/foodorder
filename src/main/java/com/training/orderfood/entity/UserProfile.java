package com.training.orderfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.training.orderfood.service.FoodItemProfileListSerializer;
import com.training.orderfood.service.OrdersListSerializer;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "USER_PROFILE")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "USER_NAME",nullable = false, length = 100)
    private String userName;

    @Column(name = "PASSWORD",nullable = false, length = 45)
    @JsonIgnore
    private String password;

    @Column(name = "MOBILE_NUMBER",nullable = false, length = 15)
    private String mobileNumber;

    @Column(name = "REGISTRATION_DATE",nullable = false)
    private LocalDate registrationDate;

    @Column(name = "ADDRESS",nullable = false, length = 200)
    private String address;


   @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
   @JsonSerialize(using = OrdersListSerializer.class)
   private List<Orders> orders;

}
