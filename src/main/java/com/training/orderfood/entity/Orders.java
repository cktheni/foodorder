package com.training.orderfood.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ORDERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name="ORDER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name="USER_ID")
    private Long userId;

    @Column(name = "TOTAL_PRICE",nullable = false)
    private Double  totalPrice;

    @Column(name = "ORDER_DATE_TIME",nullable = false)
    private Date orderDateTime;


    @Column(name = "FROM_ACCOUNT_NO",nullable = false, length = 100)
    private String fromAccountNumber;

    @Column(name = "FROM_ACCOUNT_CURRENCY",nullable = false, length = 50)
    private String fromAccountCurrecy;


    @ManyToOne
    @JoinColumns(value = {@JoinColumn(name = "USER_ID", nullable = false, insertable = false, updatable = false)})
    @JsonIgnore
    private UserProfile userProfile;


    //@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "orders" ,fetch = FetchType.LAZY)
    //@JsonSerialize(using = OrderItemsSerializer.class)
    private List<OrderItems> orderItems;


    @Transient
    private String userName;



    @PrePersist
    void preInsert() {
        if (this.orderDateTime == null) {
            //setOrderDateTime(LocalDateTime.now(ZoneId.systemDefault()));
            setOrderDateTime(new Date());
        }


    }

   }
