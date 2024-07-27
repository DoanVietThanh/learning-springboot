package com.ecommerce.firstspring.controller;

import com.ecommerce.firstspring.config.AppConstants;
import com.ecommerce.firstspring.payload.CategoryDTO;
import com.ecommerce.firstspring.payload.CategoryResponse;
import com.ecommerce.firstspring.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
  public ResponseEntity<CategoryResponse> getCategories(
          @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
          @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
          @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
          @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder
          ) {
    CategoryResponse categoryResponse = categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder);
    return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
  }

  @PostMapping("/public/categories")
  public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category) {
    CategoryDTO categoryResponse = categoryService.addCategory(category);
    return new ResponseEntity<CategoryDTO>(categoryResponse, HttpStatus.CREATED);
  }

  @PutMapping("/public/categories/{categoryId}")
  public ResponseEntity<CategoryDTO> updateCategory(@Valid @PathVariable Long categoryId, @RequestBody CategoryDTO categoryDTO) {
    CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
    return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
  }

  @DeleteMapping("/public/categories/{categoryId}")
  public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
    CategoryDTO result = categoryService.deleteCategory(categoryId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
