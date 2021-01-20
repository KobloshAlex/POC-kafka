package com.github.kobloshalex.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kobloshalex.consumer.entity.PaymentEvent;
import com.github.kobloshalex.consumer.repository.PaymentEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PaymentEventPersistService {

  private final ObjectMapper objectMapper;
  private final PaymentEventRepository repository;

  @Autowired MongoTemplate mongoTemplate;

  public PaymentEventPersistService(ObjectMapper objectMapper, PaymentEventRepository repository) {
    this.objectMapper = objectMapper;
    this.repository = repository;
  }

  public void processPaymentEvent(final ConsumerRecord<Integer, String> consumerRecord)
      throws JsonProcessingException {
    final PaymentEvent paymentEvent =
        objectMapper.readValue(consumerRecord.value(), PaymentEvent.class);
    switch (paymentEvent.getEventOperationType()) {
      case POST:
        save(paymentEvent);
        break;
      case UPDATE:
        validateAndUpdate(paymentEvent);

        break;
      default:
        log.info("Invalid payment event");
    }
  }

  private void validateAndUpdate(PaymentEvent paymentEvent) {

    if (paymentEvent.getPaymentEventId() == null) {
      throw new IllegalArgumentException("Payment id is missing");
    }

    final Optional<PaymentEvent> optionalPaymentEvent =
        repository.findFirstByPaymentEventId(paymentEvent.getPaymentEventId());

    if (!optionalPaymentEvent.isPresent()) {
      throw new IllegalArgumentException("not a valid event id");
    }
    log.info("Successfully update payment event {}", optionalPaymentEvent.get());

    mongoTemplate.updateFirst(
        new Query(Criteria.where("paymentEventId").is(paymentEvent.getPaymentEventId())),
        new Update().set("paymentEventId", paymentEvent.getPaymentEventId()),
        PaymentEvent.class);
  }

  private void save(final PaymentEvent paymentEvent) {
    repository.save(paymentEvent);
    log.info(
        "Successfully persist event: {} with payment {}", paymentEvent, paymentEvent.getPayment());
  }
}
