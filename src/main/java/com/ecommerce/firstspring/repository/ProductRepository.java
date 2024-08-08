package com.ecommerce.firstspring.repository;

import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findByCategoryOrderByPriceAsc(Category existingCategory, Pageable pageDetails);

  Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable pageDetails);

}
