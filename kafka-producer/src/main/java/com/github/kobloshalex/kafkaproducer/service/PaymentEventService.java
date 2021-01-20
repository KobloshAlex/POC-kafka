package com.github.kobloshalex.kafkaproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.github.kobloshalex.kafkaproducer.model.PaymentEvent;
import com.github.kobloshalex.kafkaproducer.utils.MessageProcessor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentEventService {

  private static final String CASH_TOPIC = "payments-cash";
  private static final String DEBIT_TOPIC = "payments-debit";
  private static final String CREDIT_TOPIC = "payments-credit";
  private static final String CASH_PAYMENT_TYPE = "CASH";
  private static final String DEBIT_PAYMENT_TYPE = "DEBIT";
  private static final String CREDIT_PAYMENT_TYPE = "CREDIT";

  private final MessageProcessor messageProcessor;

  public PaymentEventService(MessageProcessor messageProcessor) {
    this.messageProcessor = messageProcessor;
  }

  public void sendMessageTo(PaymentEvent paymentEvent) {
    try {
      if (paymentEvent.getPayment().getPaymentMethod().equalsIgnoreCase(CASH_PAYMENT_TYPE)) {
        messageProcessor.sendMessage(CASH_TOPIC, paymentEvent);
      } else if (paymentEvent
          .getPayment()
          .getPaymentMethod()
          .equalsIgnoreCase(DEBIT_PAYMENT_TYPE)) {
        messageProcessor.sendMessage(DEBIT_TOPIC, paymentEvent);
      } else if (paymentEvent
          .getPayment()
          .getPaymentMethod()
          .equalsIgnoreCase(CREDIT_PAYMENT_TYPE)) {
        messageProcessor.sendMessage(CREDIT_TOPIC, paymentEvent);
      } else {
        throw new IllegalArgumentException("Unable to identify payment method");
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
