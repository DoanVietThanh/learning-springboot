package com.ecommerce.firstspring.service;

import com.ecommerce.firstspring.model.Category;

import java.util.List;

public interface CategoryService {

  public List<Category> getCategories();

  public void addCategory(Category category);

  public String deleteCategory(Long categoryId);

  public String updateCategory(Long categoryId, Category category);
}
