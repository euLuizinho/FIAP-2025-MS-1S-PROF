package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Voucher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher", description = "API de vouchers e descontos")
public class VoucherController {
    
    @PostMapping
    @Operation(summary = "Criar novo voucher", description = "Cria um novo voucher")
    @ApiResponse(responseCode = "200", description = "Criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados Inválidos")
    public ResponseEntity<Voucher> createVoucher(@Valid @RequestBody Voucher voucher){
        return ResponseEntity.ok(voucher);
    }
}
