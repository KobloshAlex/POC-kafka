package com.github.kobloshalex.kafkaproducer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
  private Integer paymentId;
  private String paymentOwner;
  private Double amount;
  private String paymentMethod;
}
