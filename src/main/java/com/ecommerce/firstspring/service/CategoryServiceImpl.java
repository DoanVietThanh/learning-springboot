package com.ecommerce.firstspring.service;

import com.ecommerce.firstspring.exceptions.ApiException;
import com.ecommerce.firstspring.exceptions.ResourceNotFoundException;
import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.payload.CategoryDTO;
import com.ecommerce.firstspring.payload.CategoryResponse;
import com.ecommerce.firstspring.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper = new ModelMapper();

  @Override
  public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    // Sort
    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    // Pagination
    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
    List<Category> categories = categoryPage.getContent();
    if (categories.isEmpty()) {
      throw new ApiException("No categories found");
    }
    List<CategoryDTO> categoryDTOS = categories.stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
    CategoryResponse categoryResponse = new CategoryResponse();
    categoryResponse.setContent(categoryDTOS);
    categoryResponse.setPageNumber(categoryPage.getNumber());
    categoryResponse.setPageSize(categoryPage.getSize());
    categoryResponse.setTotalElements(categoryPage.getTotalElements());
    categoryResponse.setTotalPages(categoryPage.getTotalPages());
    categoryResponse.setLastPage(categoryPage.isLast());
    return categoryResponse;
  }

  @Override
  public CategoryDTO addCategory(CategoryDTO categoryDTO) {
    Category category = modelMapper.map(categoryDTO, Category.class);
    Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
    if (existingCategory != null) {
      throw new ApiException("Category already exists");
    }
    Category savedCategory = categoryRepository.save(category);
    return modelMapper.map(savedCategory, CategoryDTO.class);
  }

  @Override
  public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
    Category category = modelMapper.map(categoryDTO, Category.class);
    Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    if (!existingCategory.getCategoryName().equals(category.getCategoryName())) {
      Category existingCategoryName = categoryRepository.findByCategoryName(category.getCategoryName());
      if (existingCategoryName != null) {
        throw new ApiException("Category already exists");
      }
    }
    existingCategory.setCategoryName(category.getCategoryName());
    categoryRepository.save(existingCategory);
    return modelMapper.map(existingCategory, CategoryDTO.class);
  }

  @Override
  public CategoryDTO deleteCategory(Long categoryId) {
    Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    categoryRepository.delete(category);
    return modelMapper.map(category, CategoryDTO.class);
  }
}
