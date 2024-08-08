package com.ecommerce.firstspring.controller;

import com.ecommerce.firstspring.model.Product;
import com.ecommerce.firstspring.payload.ProductDTO;
import com.ecommerce.firstspring.payload.ProductResponse;
import com.ecommerce.firstspring.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
  @Autowired
  private ProductService productService;

  @PostMapping("/admin/categories/{categoryId}/products")
  public ResponseEntity<ProductDTO> addProduct(@PathVariable Long categoryId, @Valid @RequestBody Product product) {
    ProductDTO productResponse = productService.addProduct(categoryId, product);
    return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
  }

  @GetMapping("/public/products")
  public ResponseEntity<ProductResponse> getAllProducts() {
    ProductResponse productResponse = productService.getAllProducts();
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
  }

  @GetMapping("/public/categories/{categoryId}/products")
  public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
    ProductResponse productResponse = productService.getProductsByCategory(categoryId);
    return new ResponseEntity<>(productResponse, HttpStatus.OK);
  }

  @GetMapping("/public/products/keywords/{keyword}")
  public ResponseEntity<ProductResponse> getProductsByKeyword(
          @PathVariable String keyword) {
    ProductResponse productResponse = productService.searchProductsByKeyword(keyword);
    return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
  }

  @PutMapping("/admin/products/{productId}")
  public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @Valid @RequestBody Product product) {
    ProductDTO updatedProductDTO = productService.updateProduct(productId, product);
    return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
  }
}
