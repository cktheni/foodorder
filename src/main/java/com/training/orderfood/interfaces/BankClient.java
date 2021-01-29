package com.training.orderfood.interfaces;


import com.training.orderfood.dto.TransactionDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//@FeignClient(value="bank-service",url="http://localhost:6001/banking/fundTransfer")
@FeignClient(name="http://BANKING-SERVICE/banking/fundTransfer")
public interface BankClient {
    @RequestMapping(value = "/intraBank", method = RequestMethod.POST)
    public ResponseEntity postTransaction(TransactionDetails transactionDetails);
}


