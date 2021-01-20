package com.github.kobloshalex.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PaymentEvent {
  @Id private Integer paymentEventId;

  @Enumerated(EnumType.STRING)
  private PaymentEventOperationType eventOperationType;

  @OneToOne(mappedBy = "paymentEvent", cascade = CascadeType.ALL)
  @ToString.Exclude
  private Payment payment;
}
