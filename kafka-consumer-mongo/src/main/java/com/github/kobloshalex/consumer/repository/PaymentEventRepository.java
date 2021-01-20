package com.github.kobloshalex.consumer.repository;

import com.github.kobloshalex.consumer.entity.PaymentEvent;
import org.springframework.data.repository.CrudRepository;

public interface PaymentEventRepository extends CrudRepository<PaymentEvent, Integer> {}
