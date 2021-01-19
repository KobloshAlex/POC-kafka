package com.github.kobloshalex.kafkaproducer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kobloshalex.kafkaproducer.model.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Slf4j
public class MessageProcessor {

  private final KafkaTemplate<Integer, String> kafkaTemplate;
  private final ObjectMapper objectMapper;

  public MessageProcessor(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(String topic, PaymentEvent paymentEvent) throws JsonProcessingException {
    final Integer key = paymentEvent.getPaymentEventId();
    final String value = objectMapper.writeValueAsString(paymentEvent);
    kafkaTemplate
        .send(new ProducerRecord<>(topic, key, value))
        .addCallback(
            new ListenableFutureCallback<SendResult<Integer, String>>() {
              @Override
              public void onFailure(Throwable throwable) {
                log.error(
                    "Error Sending message {} with key {}.Exception {}",
                    value,
                    key,
                    throwable.getMessage());
              }

              @Override
              public void onSuccess(SendResult<Integer, String> result) {
                log.info(
                    "Message sent successfully for key {}, with value {}, to topic {} ",
                    key,
                    value,
                    result.getRecordMetadata().topic());
              }
            });
  }
}
