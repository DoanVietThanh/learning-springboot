package com.ecommerce.firstspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @NotBlank
  @Size(min = 5, message = "Name must be at least 5 characters long")
  private String productName;

  @NotBlank
  @Size(min = 10, message = "Description must be at least 10 characters long")
  private String description;

  private String image;

  @Min(value = 0, message = "Quantity must be greater than or equal to 0")
  private Integer quantity;

  @Min(value = 0, message = "Price must be greater than or equal to 0")
  private double price;

  @Min(value = 0, message = "Discount must be greater than or equal to 0")
  @Max(value = 100, message = "Discount must be less than or equal to 100")
  private double discount;

  private double specialPrice;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
