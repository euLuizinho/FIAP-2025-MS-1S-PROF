package com.example.demo.model;

import com.example.demo.model.VoucherType;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Voucher {
  private Long id;

  @NotBlank(message = "Voucher code is required")
  private String code;

  @NotNull(message = "Discount percentage is required")
  @PositiveOrZero(message = "Discount percentage must be zero or positive")
  private BigDecimal discountPercent;

  @NotNull(message = "Expiration date is required")
  @FutureOrPresent(message = "Expiration date must be today or in the future")
  private LocalDate expirationDate;

  @NotNull(message = "Voucher type is required")
  private VoucherType type;

  @PositiveOrZero(message = "Minimum order value must be zero or positive")
  private BigDecimal minimumOrderValue;
}