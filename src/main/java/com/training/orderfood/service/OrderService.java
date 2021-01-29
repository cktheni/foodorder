package com.training.orderfood.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.orderfood.dto.TransactionDetails;
import com.training.orderfood.entity.*;
import com.training.orderfood.entity.id.FoodItemProfileId;
import com.training.orderfood.interfaces.BankClient;
import com.training.orderfood.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderService {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    VendorProfileRepository vendorProfileRepository;

    @Autowired
    FoodItemProfileRepository foodItemProfileRepository;

    @Autowired
    OrdersRepository ordersRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Autowired
    MetaDataService mtaDataService;



    @Autowired
    BankClient bankClient;

    public List<UserProfile> getAllUsers() {

        return userProfileRepository.findAll();
    }

    public List<VendorProfile> getAllVendors() {

        return vendorProfileRepository.findAll();
    }

    public List<FoodItemProfile> getAllFoodItems() {
        return foodItemProfileRepository.findAll();
    }

    public List<FoodItemProfile> getFoodItemNameLike(String foodItemName) {

        List<FoodItemProfile> foodItems = Optional.ofNullable(foodItemProfileRepository.findByFoodItemNameLike("%" + foodItemName + "%")).orElseGet(Collections::emptyList).stream().collect(Collectors.toList());
        for (FoodItemProfile foodItemProfile : foodItems) {
            Optional<VendorProfile> vendorProfile = vendorProfileRepository.findById(foodItemProfile.getVendorId());
            if (vendorProfile.isPresent()) {
                foodItemProfile.setVendorName(vendorProfile.get().getVendorName());
            }
        }
        // List<FoodItemProfile> foodItems= Optional.ofNullable(foodItemProfileRepository.findByFoodItemNameLike("%"+foodItemName+"%")).orElseGet(Collections::emptyList).stream().collect(Collectors.toList());
        return foodItems;
    }

    @Transactional
    public Orders paceOrders(Orders orders) throws Exception {
        try {
            List<TransactionDetails> transDetailsList = new ArrayList();

            String mobileNumber =null;
            String toAccountName=null;
            String toAccountNumber=null;
            String toAccountCurrency=null;

            log.info("Trying to Save Order");
            log.info("From Account Number :"+orders.getFromAccountNumber());
            log.info("From  Account Currency:"+orders.getFromAccountCurrecy());
            orders.setTotalPrice(0.0);
            orders =  ordersRepository.save(orders);
            log.info("Order ID generated :"+orders.getOrderId() +" For the User :"+orders.getUserId());
            log.info("Trying to Place Order Items for the Order :"+orders.getOrderId() +" Where order is created on:"+orders.getOrderDateTime());

            double totalPrice =0.0;
            Optional<UserProfile> userProfile = userProfileRepository.findById(orders.getUserId());

            if(userProfile.isPresent())
            {
                log.info("The Transaction User is Registered with "+userProfile.get().getMobileNumber());
                mobileNumber = userProfile.get().getMobileNumber();
            }
            int c=1;

            ObjectMapper objectMapper = new ObjectMapper();
            String json = new ObjectMapper().writeValueAsString(orders);
            //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            orders = objectMapper.readValue(json, new TypeReference<Orders>() {});


            for (OrderItems orderItem: orders.getOrderItems())
            {
                TransactionDetails transactionDetails = new TransactionDetails();

                orderItem.setOrderId(orders.getOrderId());
                log.info("The Order Items for the Order ID is"+orderItem.getOrderId());
                log.info("Vendor Id:"+orderItem.getVendorId());
                log.info("Food Item Id:"+orderItem.getFoodItemId());
                orderItem.setIsPaymentSuccess("N");
                Optional<FoodItemProfile> foodItemProfile =foodItemProfileRepository.findById(new FoodItemProfileId(orderItem.getFoodItemId(),orderItem.getVendorId()));
                if(foodItemProfile.isPresent())
                {
                    totalPrice =totalPrice+ foodItemProfile.get().getPrice() * orderItem.getQuantity();
                }
                Optional<VendorProfile> vendorProfile = vendorProfileRepository.findById(orderItem.getVendorId());
                if(vendorProfile.isPresent())
                {
                    log.info("To Account Name:"+ vendorProfile.get().getVendorName());
                    log.info("To Account Number:"+ vendorProfile.get().getVendorAccountNumber());
                    log.info("To Account Currency:"+ vendorProfile.get().getVendorAccountCurrency());
                    toAccountName=vendorProfile.get().getVendorName();
                    toAccountNumber=vendorProfile.get().getVendorAccountNumber();
                    toAccountCurrency=vendorProfile.get().getVendorAccountCurrency();
                }
                orderItem.setIsPaymentSuccess("N");
                log.info("Trying to Save:"+c+ " order Item:"+orderItem);
                orderItem = orderItemsRepository.save(orderItem);
                log.info("Order Item:"+orderItem.getOrderItemId() +" Saved for the Order Id:"+orders.getOrderId());

                //Setting Transaction Details
                transactionDetails.setMobileNumber(mobileNumber);
                transactionDetails.setFromAccountNumber(orders.getFromAccountNumber());
                transactionDetails.setFromAccountCurrency(orders.getFromAccountCurrecy());
                transactionDetails.setToAccountNumber(toAccountNumber);
                transactionDetails.setToAccountCurrency(toAccountCurrency);
                transactionDetails.setToCustomerName(toAccountName);
                transactionDetails.setTransactionAmount(foodItemProfile.get().getPrice() * orderItem.getQuantity());
                //transactionDetails.setTransactionAmount(totalPrice);
                transactionDetails.setRemarks("Food Delivery Payment");
                transDetailsList.add(transactionDetails);
                c++;
            }

            log.info("All Order Items Saved Successfully");

            log.info("Order Total Price:"+totalPrice);
            orders.setTotalPrice(totalPrice);
            log.info("Updating Total Price for the Order");
            orders =  ordersRepository.save(orders);
            log.info("The Placed Order:"+orders  + ", And its Order Items size:"+ orders.getOrderItems().size());

            log.info("Trying to Do the Fund Transfer");

            Map<String, List<TransactionDetails>> transactionMap = Optional.ofNullable(transDetailsList).orElseGet(Collections::emptyList).stream().collect(Collectors.groupingBy(TransactionDetails::getToAccountNumber));

            List<TransactionDetails> transactionstoPay = new ArrayList();

            for (Map.Entry<String, List<TransactionDetails>> entry : transactionMap.entrySet()) {

                List<TransactionDetails> lst = transactionMap.get(entry.getKey());
                System.out.println("To Account Number :" + entry.getKey() +"No of Transactions:"+lst.size());
                double totalVendorAmount =0.0;

                for (TransactionDetails transactions : lst) {
                    totalVendorAmount=totalVendorAmount+transactions.getTransactionAmount();
                }
                TransactionDetails trnsDetails = new TransactionDetails();
                trnsDetails = lst.get(0);
                trnsDetails.setTransactionAmount(totalVendorAmount);
                trnsDetails.setRemarks("Food Delivery Payment");
                transactionstoPay.add(trnsDetails);
            }
            /*TransactionDetails transactionDetails = new TransactionDetails();

            transactionDetails.setMobileNumber(mobileNumber);
            transactionDetails.setFromAccountNumber(orders.getFromAccountNumber());
            transactionDetails.setFromAccountCurrency(orders.getFromAccountCurrecy());
            transactionDetails.setToAccountNumber(toAccountNumber);
            transactionDetails.setToAccountCurrency(toAccountCurrency);
            transactionDetails.setToCustomerName(toAccountName);
            transactionDetails.setTransactionAmount(totalPrice);
            transactionDetails.setRemarks("Food Delivery Payment");*/

            for (TransactionDetails transactionDetails : transactionstoPay) {

                log.info("Going to call Banking Fund Transfer Service");
                ResponseEntity responseEntity = bankClient.postTransaction(transactionDetails);
                if (responseEntity.getStatusCode() == HttpStatus.OK) {
                    for (OrderItems orderItem : orders.getOrderItems()) {
                        orderItem.setIsPaymentSuccess("Y");
                    }
                } else {
                    for (OrderItems orderItem : orders.getOrderItems()) {
                        orderItem.setIsPaymentSuccess("N");
                    }
                }
                log.info("Updating payment Status of Order Item:");
                orders = ordersRepository.save(orders);
            }
            log.info("Payment Success for the Order:"+orders.getOrderId());
            return orders;

        } catch (Exception e) {
            log.error("Exception Occurs while paceOrders", e);
            throw new Exception("Unable to paceOrders !! Please contact Support Team..");
        }

    }

    public List<Orders>  getOrderHistory(Long userId)
    {
        //List<Orders> ordersList = ordersRepository.findTop5ByOrderByOrderDateTimeDesc();
        List<Orders> ordersList = ordersRepository.findOrderHistory(userId);
        List<Orders> firstNOrdersList = ordersList.stream().limit(5).collect(Collectors.toList());

        for (Orders order : firstNOrdersList) {
            order.setUserName(order.getUserProfile().getUserName());

            for (OrderItems orderItem : order.getOrderItems()) {
                VendorProfile vendorProfile = vendorProfileRepository.getOne(orderItem.getVendorId());
                FoodItemProfile foodItemProfile = foodItemProfileRepository.findByFoodItemId(orderItem.getFoodItemId());
                orderItem.setVendorName(vendorProfile.getVendorName());
                orderItem.setFoodItemName(foodItemProfile.getFoodItemName());
            }
        }
        return firstNOrdersList;
        //return ordersRepository.findOrderHistory(userId);
    }
}
