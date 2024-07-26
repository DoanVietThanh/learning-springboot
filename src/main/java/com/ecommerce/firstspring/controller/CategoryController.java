package com.ecommerce.firstspring.controller;

import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
  public ResponseEntity<List<Category>> getCategories() {
    return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
  }

  @PostMapping("/public/categories")
  public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
    categoryService.addCategory(category);
    return new ResponseEntity<>("Category added", HttpStatus.CREATED);
  }

  @DeleteMapping("/public/categories/{categoryId}")
  public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
    String result = categoryService.deleteCategory(categoryId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PutMapping("/public/categories/{categoryId}")
  public ResponseEntity<String> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody Category category) {
    String result = categoryService.updateCategory(categoryId, category);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
