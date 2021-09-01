package com.prokarma.customer.publisher.controller;

import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokarma.customer.publisher.domain.CustomerRequest;
import com.prokarma.customer.publisher.domain.CustomerResponse;


@RestController
@RequestMapping(value="customer/v1/create")
public class CustomerPublisherController {

  private static final Logger log = LoggerFactory.getLogger(CustomerPublisherController.class);

  private final ObjectMapper objectMapper;


  @org.springframework.beans.factory.annotation.Autowired
  public CustomerPublisherController(ObjectMapper objectMapper, HttpServletRequest request) {
    this.objectMapper = objectMapper;
   
  }
  
  
  @PostMapping(path="/retail-customer",produces = MediaType.APPLICATION_JSON_VALUE, 
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CustomerResponse> publishCustomerDetails(
      @RequestHeader(value = "Authorization",required = true)String authorization, 
      @RequestHeader(value = "Transaction-Id",required = true) String transactionId,
      @RequestHeader(value = "Activity-Id",required = true) String activityId,
      @Valid @RequestBody CustomerRequest customer) {
   
   

    return new ResponseEntity<CustomerResponse>(HttpStatus.NOT_IMPLEMENTED);
  }
  
  public void producer(){ 
  String topicName = "SimpleProducerTopic";
  String key = "Key1";
  String value = "Value-1";
  
  Properties props = new Properties();
  props.put("bootstrap.servers", "localhost:9092,localhost:9093");
  props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");         
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
  Producer<String, String> producer = new KafkaProducer <>(props);

  ProducerRecord<String, String> record = new ProducerRecord<>(topicName,key,value);
  producer.send(record);           
  producer.close();
  
  System.out.println("SimpleProducer Completed.");}

}
