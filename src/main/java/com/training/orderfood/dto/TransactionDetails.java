package com.training.orderfood.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Slf4j
@Data
public class TransactionDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private String countryCode;
    private String transactionId;
    private String relationshipNo;


    private String mobileNumber;
    private String fromAccountNumber;
    private String fromAccountCurrency;

    private String toAccountNumber;
    private String toAccountCurrency;

    private String toCustomerName;
    private Double transactionAmount;
    private Date transactionDate;
    private String referenceNumber;
    private String transactionType;

    private String remarks;

    private String createdBy;
    private Date createdDate;

    private String updatedBy;
    private Date updatedDate;

    private List<String> errors;
    private String response;

}
