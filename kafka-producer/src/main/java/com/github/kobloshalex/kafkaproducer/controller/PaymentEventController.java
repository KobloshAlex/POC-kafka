package com.github.kobloshalex.kafkaproducer.controller;

import com.github.kobloshalex.kafkaproducer.model.Payment;
import com.github.kobloshalex.kafkaproducer.model.PaymentEvent;
import com.github.kobloshalex.kafkaproducer.model.PaymentEventOperationType;
import com.github.kobloshalex.kafkaproducer.service.PaymentEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    paymentEvent.setEventOperationType(PaymentEventOperationType.POST);
    try {
      paymentEventService.sendMessageTo(paymentEvent);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(paymentEvent);
  }

  @PutMapping("v1/payments")
  public final ResponseEntity<?> updatePayment(final @RequestBody PaymentEvent paymentEvent) {

    if (paymentEvent.getPaymentEventId() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the payment ID");
    }

    paymentEvent.setEventOperationType(PaymentEventOperationType.UPDATE);
    paymentEventService.sendMessageTo(paymentEvent);
    return ResponseEntity.status(HttpStatus.OK).body(paymentEvent);
  }

  @PostMapping("v1/payments-hundred")
  public final void postHundredPayments() throws InterruptedException {
    for (int i = 0; i < 5; i++) {
      paymentEventService.sendMessageTo(
          PaymentEvent.builder()
              .paymentEventId(123)
              .eventOperationType(PaymentEventOperationType.POST)
              .payment(
                  Payment.builder()
                      .paymentId(1)
                      .paymentOwner("Alex")
                      .amount(123.12)
                      .paymentMethod("Credit")
                      .build())
              .build());

      Thread.sleep(1000);
    }
  }
}
