package com.ecommerce.firstspring.payload;

import com.ecommerce.firstspring.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CategoryDTO {
  private Long categoryId;
  private String categoryName;
}
