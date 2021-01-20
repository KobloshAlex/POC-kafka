package com.github.kobloshalex.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.kobloshalex.consumer.service.PaymentEventPersistService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventConsumer {

  private final PaymentEventPersistService paymentService;

  public PaymentEventConsumer(PaymentEventPersistService paymentService) {
    this.paymentService = paymentService;
  }

  @KafkaListener(topics = {"payments-cash", "payments-debit", "payments-credit"})
  public void onMessage(ConsumerRecord<Integer, String> consumerRecord)
      throws JsonProcessingException {
    paymentService.processPaymentEvent(consumerRecord);
    log.info("Consumer Record: {}", consumerRecord);
  }
}
