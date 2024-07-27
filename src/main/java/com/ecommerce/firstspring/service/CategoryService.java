package com.ecommerce.firstspring.service;

import com.ecommerce.firstspring.payload.CategoryDTO;
import com.ecommerce.firstspring.payload.CategoryResponse;

public interface CategoryService {

  CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

  CategoryDTO addCategory(CategoryDTO categoryDTO);

  CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

  CategoryDTO deleteCategory(Long categoryId);
}
