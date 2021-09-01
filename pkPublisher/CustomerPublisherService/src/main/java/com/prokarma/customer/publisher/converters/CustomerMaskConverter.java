package com.prokarma.customer.publisher.converters;

import org.springframework.stereotype.Component;
import com.prokarma.customer.publisher.domain.CustomerRequest;

@Component
public class CustomerMaskConverter {


  public CustomerRequest convert(CustomerRequest input) {
    CustomerRequest customer = new CustomerRequest();
    customer.setCustomerNumber(input.getCustomerNumber().replaceAll("", "*"));
    customer.setFirstName(input.getFirstName());
    customer.setLastName(input.getLastName());
    customer.setCountry(input.getCountry());
    customer.setCountryCode(input.getCountryCode());
    customer.setEmail(input.getEmail().replaceAll("", "*"));
    customer.setCustomerStatus(input.getCustomerStatus());
    customer.birthdate(input.getBirthdate());
    customer.setAddress(input.getAddress());
    return customer;
  }

}
