package com.ecommerce.firstspring.repository;

import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByCategoryOrderByPriceAsc(Category existingCategory);

  List<Product> findByProductNameLikeIgnoreCase(String keyword);
}
