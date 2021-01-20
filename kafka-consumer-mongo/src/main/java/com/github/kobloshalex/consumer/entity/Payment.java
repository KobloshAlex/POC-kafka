package com.github.kobloshalex.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Payment {
  @Id private Integer paymentId;
  private String paymentOwner;
  private Double amount;
  private String paymentMethod;

  @OneToOne
  @JoinColumn(name = "paymentEventId")
  private PaymentEvent paymentEvent;
}
