package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product", description = "Product management APIs")
public class ProductController {
  private static final String SUCCESS_CODE = "200";
  private static final String NOT_FOUND_CODE = "404";
  private static final String ID_PATH = "/{id}";
  private static final String NOT_FOUND_MESSAGE = "Product not found";

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  @Operation(
      summary = "List all products",
      description = "Returns a list of all products in the system")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved list")
  public List<Product> getAllProducts() {
    return productService.findAll();
  }

  @GetMapping(ID_PATH)
  @Operation(summary = "Get a product by ID", description = "Returns a single product by its ID")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully retrieved product")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Product> getProduct(@PathVariable Long id) {
    Product product = productService.findById(id);
    return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
  }

  @PostMapping
  @Operation(summary = "Create a new product", description = "Creates a new product in the system")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully created product")
  public Product createProduct(@Valid @RequestBody Product product) {
    return productService.create(product);
  }

  @PutMapping(ID_PATH)
  @Operation(
      summary = "Update a product",
      description = "Updates all fields of an existing product")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully updated product")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Product> updateProduct(
      @PathVariable Long id, @Valid @RequestBody Product product) {
    Product updated = productService.update(id, product);
    return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
  }

  @PatchMapping(ID_PATH)
  @Operation(
      summary = "Partially update a product",
      description = "Updates only the provided fields of an existing product")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully updated product")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Product> patchProduct(@PathVariable Long id, @RequestBody Product product) {
    Product updated = productService.patch(id, product);
    return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
  }

  @DeleteMapping(ID_PATH)
  @Operation(summary = "Delete a product", description = "Deletes a product from the system")
  @ApiResponse(responseCode = SUCCESS_CODE, description = "Successfully deleted product")
  @ApiResponse(responseCode = NOT_FOUND_CODE, description = NOT_FOUND_MESSAGE)
  public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    return productService.delete(id)
        ? ResponseEntity.ok().build()
        : ResponseEntity.notFound().build();
  }
}

