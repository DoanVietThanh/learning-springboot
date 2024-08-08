package com.ecommerce.firstspring.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
  private String productName;
  private String description;
  private String image;
  private Integer quantity;
  private double price;

  @Min(value = 0, message = "Discount must be greater than or equal to 0")
  @Max(value = 100, message = "Discount must be less than or equal to 100")
  private double discount;

  private double specialPrice;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
}
