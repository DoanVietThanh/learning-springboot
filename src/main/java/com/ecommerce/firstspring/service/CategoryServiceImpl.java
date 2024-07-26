package com.ecommerce.firstspring.service;

import com.ecommerce.firstspring.exceptions.ApiException;
import com.ecommerce.firstspring.exceptions.ResourceNotFoundException;
import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public List<Category> getCategories() {
    List <Category> categories = categoryRepository.findAll();
    if (categories.isEmpty()) {
      throw new ApiException("No categories found");
    }
    return categories;
  }

  @Override
  public void addCategory(Category category) {
    Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
    if (existingCategory != null) {
      throw new ApiException("Category already exists");
    }
    categoryRepository.save(category);
  }

  @Override
  public String deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    categoryRepository.delete(category);
    return "Category deleted";
  }

  @Override
  public String updateCategory(Long categoryId, Category category) {

    Category savedCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    savedCategory.setCategoryName(category.getCategoryName());
    categoryRepository.save(savedCategory);
    return "Category updated";
  }
}
