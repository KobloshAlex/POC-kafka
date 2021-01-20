package com.github.kobloshalex.consumer.repository;

import com.github.kobloshalex.consumer.entity.PaymentEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentEventRepository extends MongoRepository<PaymentEvent, Integer> {
  Optional<PaymentEvent> findFirstByPaymentEventId(Integer id);
}
