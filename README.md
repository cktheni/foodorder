# foodorder
Order service
**********************************************************************

CREATE TABLE `orders`.`user_profile` (
  `USER_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_NAME` VARCHAR(100) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `MOBILE_NUMBER` VARCHAR(15) NOT NULL,
  `REGISTRATION_DATE` DATETIME NOT NULL,
  `ADDRESS` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`USER_ID`));
 
INSERT INTO `orders`.`user_profile` (`USER_ID`, `USER_NAME`, `PASSWORD`, `MOBILE_NUMBER`, `REGISTRATION_DATE`, `ADDRESS`) VALUES ('1', 'Chandra Kumar', 'India$123', '9677152375', '2021-01-27', 'URAPAKKAM, CHENNAI');
INSERT INTO `orders`.`user_profile` (`USER_ID`, `USER_NAME`, `PASSWORD`, `MOBILE_NUMBER`, `REGISTRATION_DATE`, `ADDRESS`) VALUES ('2', 'Siva Sankaran', 'America$123', '9940372228', '2021-01-27', 'CHENNAI');


**********************************************************************


CREATE TABLE `orders`.`vendor_profile` (
  `VENDOR_ID` INT NOT NULL AUTO_INCREMENT,
  `VENDOR_NAME` VARCHAR(100) NOT NULL,
  `VENDOR_BANK_NAME` VARCHAR(50) NOT NULL,
  `VENDOR_BANK_CODE` VARCHAR(45) NOT NULL,
  `VENDOR_ACCOUNT_NUMBER` VARCHAR(45) NOT NULL,
  VENDOR_ACCOUNT_CURRENCY VARCHAR(45) NOT NULL default 'RS',
  PRIMARY KEY (`VENDOR_ID`));
  
  
INSERT INTO `orders`.`vendor_profile` (`VENDOR_ID`, `VENDOR_NAME`, `VENDOR_BANK_NAME`, `VENDOR_BANK_CODE`, `VENDOR_ACCOUNT_NUMBER`,`VENDOR_ACCOUNT_CURRENCY`) VALUES ('1', 'Anjappar', 'ICICI', 'CHNICIC8010987', '100002','RS');
INSERT INTO `orders`.`vendor_profile` (`VENDOR_ID`, `VENDOR_NAME`, `VENDOR_BANK_NAME`, `VENDOR_BANK_CODE`, `VENDOR_ACCOUNT_NUMBER`,`VENDOR_ACCOUNT_CURRENCY`) VALUES ('2', 'Hyderabad house ', 'SCB', 'CNHSCB8751086', '100003','RS');


**********************************************************************

CREATE TABLE `orders`.`food_item_profile` (
  `FOOD_ITEM_ID` INT NOT NULL AUTO_INCREMENT,
  `FOOD_ITEM_NAME` VARCHAR(100) NOT NULL,
  `VENDOR_ID` INT NOT NULL,
  `PRICE` DOUBLE NOT NULL,
  PRIMARY KEY (`FOOD_ITEM_ID`,VENDOR_ID),
  INDEX `VENDOR_FK_idx` (`VENDOR_ID` ASC) VISIBLE,
  CONSTRAINT `VENDOR_FK`
    FOREIGN KEY (`VENDOR_ID`)
    REFERENCES `orders`.`vendor_profile` (`VENDOR_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('1', 'chicken biryani', '1', '200');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('2', 'paneer', '1', '175');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('3', 'chicken 65', '1', '210');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('4', 'gobi manchurian', '1', '185');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('5', 'Badam Milk', '1', '110');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('7', 'chicken biryani', '2', '210');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('8', 'mutton biryani', '2', '220');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('9', 'Badam Milk', '2', '120');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('10', 'Hot Chocolate', '2', '130');
INSERT INTO `orders`.`food_item_profile` (`FOOD_ITEM_ID`, `FOOD_ITEM_NAME`, `VENDOR_ID`, `PRICE`) VALUES ('11', 'mutton biryani', '1', '210');


*************************************************************************

CREATE TABLE `orders`.`orders` (
  `ORDER_ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `TOTAL_PRICE` DOUBLE NOT NULL,
  `ORDER_DATE_TIME` DATETIME NOT NULL,
  FROM_ACCOUNT_NO  VARCHAR(50) NOT NULL,
  FROM_ACCOUNT_CURRENCY  VARCHAR(10) NOT NULL,
  PRIMARY KEY (`ORDER_ID`),
  INDEX `USER_ID_FK_idx` (`USER_ID` ASC) VISIBLE,
  CONSTRAINT `USER_ID_FK`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `orders`.`user_profile` (`USER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

*************************************************************************
CREATE TABLE `orders`.`order_items` (
  `ORDER_ITEM_ID` INT NOT NULL AUTO_INCREMENT,
  `ORDER_ID` INT NOT NULL,
  `FOOD_ITEM_ID` INT NOT NULL,
   `VENDOR_ID`    INT NOT NULL,
  `QUANTITY` INT NULL,
  `IS_PAYMENT_SUCCESS` CHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`ORDER_ITEM_ID`),
  INDEX `ORDER_ID_FK_idx` (`ORDER_ID` ASC) INVISIBLE,
  INDEX `FOOD_ITEM_ID_FK_idx` (`FOOD_ITEM_ID` ASC) VISIBLE,
  CONSTRAINT `ORDER_ID_FK`
    FOREIGN KEY (`ORDER_ID`)
    REFERENCES `orders`.`orders` (`ORDER_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FOOD_ITEM_ID_FK`
    FOREIGN KEY (`FOOD_ITEM_ID`)
    REFERENCES `orders`.`food_item_profile` (`FOOD_ITEM_ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

*************************************************************************

http://localhost:8761/
http://localhost:6003/orders/swagger-ui.html

	1 Cust
	      many accounts


at [Source: (PushbackInputStream); line: 11, column: 2] (through reference chain: com.training.orderfood.entity.Orders["orderItems"]->java.util.ArrayList[1])]


{
  "orderId": 0,
  "userId": 0,
  "totalPrice": 0,
  "orderDateTime": "2021-01-28T05:18:52.222Z",
  "fromAccountNumber": "string",
  "fromAccountCurrecy": "string",
  "orderItems": [
    {
      "orderItemId": 0,
      "orderId": 0,
      "vendorId": 0,
      "foodItemId": 0,
      "quantity": 0,
      "isPaymentSuccess": "string"
    }
  ]
}


=============================================

{
  "userId": 1,
  "fromAccountNumber": "100000",
  "fromAccountCurrecy": "RS",
  "orderItems": [
    {
      "vendorId": 1,
      "foodItemId": 1,
      "quantity": 2
    },
    {
      "vendorId": 1,
      "foodItemId": 2,
      "quantity": 1
    }
  ]
}

=============================================
no String-argument constructor/factory method to deserialize from String value 


{
  "userId": 1,
  "fromAccountNumber": "100000",
  "fromAccountCurrecy": "RS",
  "orderItems": [
    {
      "vendorId": 1,
      "foodItemId": 1,
      "quantity": 2
    }
  ]
}

A/c 10000  750		200 * 2  = 400	/ 350
	10002  5000

{
  "userId": 1,
  "fromAccountNumber": "100000",
  "fromAccountCurrecy": "RS",
  "orderItems": [
    {
      "vendorId": 1,
      "foodItemId": 1,
      "quantity": 2
    }
  ]
}


=============================


//1
{
 
  "mobileNumber": "9677152375",
  "fromAccountNumber": "100000",
  "fromAccountCurrency": "RS",
  "toAccountNumber": "100001",
  "toAccountCurrency": "RS",
  "toCustomerName": "Siva",
  "transactionAmount": 200,
  "remarks": "Test"
}



================================


DEMO  Purpose
==============
Place Order


{
  "userId": 1,
  "fromAccountNumber": "100000",
  "fromAccountCurrecy": "RS",
  "orderItems": [
    {
      "vendorId": 1,
      "foodItemId": 1,
      "quantity": 1
    }
  ]
}


http://localhost:8761/

http://localhost:6001/banking/swagger-ui.html
http://BANKING-SERVICE/banking/swagger-ui.html


java -Dserver.port=6002 -jar .\target\banking-0.0.1-SNAPSHOT.jar
http://localhost:6002/banking/swagger-ui.html

http://localhost:6003/orders/swagger-ui.html



@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

DEMO
====

use banking

select b.first_name, a.* from customer_account  a  inner join customer b on a.country_code=b.country_code 
and a.relationship_no=b.relationship_no
where account_no in ('100000','100002')


http://localhost:8761/


Make Sure 2 Instance of Bank application is UP and running
===========================================================

http://localhost:6001/banking/swagger-ui.html
(java -Dserver.port=6002 -jar .\target\banking-0.0.1-SNAPSHOT.jar)
http://localhost:6002/banking/swagger-ui.html

Make Sure Order service is running
====================================

http://localhost:6003/orders/swagger-ui.html




