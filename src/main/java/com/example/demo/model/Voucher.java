package com.example.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class Voucher2 {
    @NotBlank(message="Código é requerido")
    @Pattern(regexp="^[A-Z0-9][4,10]$",message = "Código precisa ter entre 4 e 10 letras maiúsculas")

    private String code;
    
    @NotNull(message = "Tipo é requerido")
    private String type;

    @NotNull(message = "Valor é requerido")
    @Positive(message = "Precisa ser positivo")
    private BigDecimal value;

    @NotNull(message = "Valor é requerido")
    @Min(value = 0, message = "Não pode ser negativo")
    private BigDecimal minimumPurchase;
     
    @NotNull(message = "Valor requerido")
    @Future(message = "Expiração precisa  ser fatura")
    private LocalDateTime expirationDate;

    @NotNull(message = "Limite é requerido")
    @Min(value = 0, message = "Precisa ser ao menos 1")
    private Integer usageLimit;

    private Integer usageCount = 0;

}