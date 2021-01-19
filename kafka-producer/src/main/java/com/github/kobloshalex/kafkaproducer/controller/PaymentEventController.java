package com.github.kobloshalex.kafkaproducer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kobloshalex.kafkaproducer.model.PaymentEvent;
import com.github.kobloshalex.kafkaproducer.service.PaymentEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentEventController {

  private final PaymentEventService paymentEventService;

  public PaymentEventController(PaymentEventService paymentEventService) {
    this.paymentEventService = paymentEventService;
  }

  @PostMapping("v1/payments")
  public final ResponseEntity<PaymentEvent> postPayment(
      final @RequestBody PaymentEvent paymentEvent) {
    try{
      paymentEventService.sendMessageTo(paymentEvent);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(paymentEvent);
  }
}
