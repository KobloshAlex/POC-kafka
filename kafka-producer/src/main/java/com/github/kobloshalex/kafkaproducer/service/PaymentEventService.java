package com.github.kobloshalex.kafkaproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.github.kobloshalex.kafkaproducer.model.PaymentEvent;
import com.github.kobloshalex.kafkaproducer.utils.MessageProcessor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentEventService {

  private final MessageProcessor messageProcessor;

  public PaymentEventService(MessageProcessor messageProcessor) {
    this.messageProcessor = messageProcessor;
  }

  public void sendMessageTo(PaymentEvent paymentEvent) {
    try {
      if (paymentEvent.getPayment().getPaymentMethod().equalsIgnoreCase("CASH")) {
        messageProcessor.sendMessage("payments-cash", paymentEvent);
      } else if (paymentEvent.getPayment().getPaymentMethod().equalsIgnoreCase("DEBIT")) {
        messageProcessor.sendMessage("payments-debit", paymentEvent);
      } else if (paymentEvent.getPayment().getPaymentMethod().equalsIgnoreCase("CREDIT")) {
        messageProcessor.sendMessage("payments-credit", paymentEvent);
      } else {
        throw new IllegalArgumentException("Unable to identify payment method");
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
