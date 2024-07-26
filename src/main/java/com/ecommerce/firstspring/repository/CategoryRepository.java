package com.ecommerce.firstspring.repository;

import com.ecommerce.firstspring.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Category findByCategoryName(String categoryName);
}
