package com.example.demo.controller;

import com.example.demo.model.Voucher;
import com.example.demo.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vouchers")
@Tag(name = "Voucher", description = "Voucher management APIs")
public class VoucherController {
  private static final String SUCCESS_CODE = "200";
  private static final String NOT_FOUND_CODE = "404";
  private static final String ID_PATH = "/{id}";
  private static final String NOT_FOUND_MESSAGE = "Voucher not found";

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping
  @Operation(summary = "List all vouchers", description = "Returns a list of all vouchers in the system")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved list")
  public List<Voucher> getAllVouchers() {
    return voucherService.findAll();
  }

  @GetMapping(ID_PATH)
  @Operation(summary = "Get a voucher by ID", description = "Returns a single voucher by its ID")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved voucher")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Voucher> getVoucher(@PathVariable Long id) {
    Voucher voucher = voucherService.findById(id);
    return voucher != null ? ResponseEntity.ok(voucher) : ResponseEntity.notFound().build();
  }

  @GetMapping("/code/{code}")
  @Operation(summary = "Get a voucher by code", description = "Validates if the voucher exists and is not expired, returning its information")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved voucher")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = "Voucher not found or expired")
  public ResponseEntity<Voucher> getVoucherByCode(@PathVariable String code) {
    Voucher voucher = voucherService.findByCode(code);
    if (voucher == null || voucher.getExpirationDate().isBefore(java.time.LocalDate.now())) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(voucher);
  }

  @PostMapping
  @Operation(summary = "Create a new voucher", description = "Creates a new voucher in the system")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully created voucher")
  public Voucher createVoucher(@Valid @RequestBody Voucher voucher) {
    return voucherService.create(voucher);
  }

  @PutMapping(ID_PATH)
  @Operation(summary = "Update a voucher", description = "Updates all fields of an existing voucher")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully updated voucher")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Voucher> updateVoucher(@PathVariable Long id, @Valid @RequestBody Voucher voucher) {
    Voucher updated = voucherService.update(id, voucher);
    return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
  }

  @PatchMapping(ID_PATH)
  @Operation(summary = "Partially update a voucher", description = "Updates only the provided fields of an existing voucher")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully updated voucher")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Voucher> patchVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {
    Voucher updated = voucherService.patch(id, voucher);
    return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
  }

  @DeleteMapping(ID_PATH)
  @Operation(summary = "Delete a voucher", description = "Deletes a voucher from the system")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully deleted voucher")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Void> deleteVoucher(@PathVariable Long id) {
    return voucherService.delete(id)
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }
}
