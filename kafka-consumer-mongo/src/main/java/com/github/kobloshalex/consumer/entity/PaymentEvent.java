package com.github.kobloshalex.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "payment-events")
public class PaymentEvent {
  @Id private String id;

  private Integer paymentEventId;

  private PaymentEventOperationType eventOperationType;

  @ToString.Exclude private Payment payment;
}
