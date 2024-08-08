package com.ecommerce.firstspring.service.product;

import com.ecommerce.firstspring.exceptions.ResourceNotFoundException;
import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.model.Product;
import com.ecommerce.firstspring.payload.ProductDTO;
import com.ecommerce.firstspring.payload.ProductResponse;
import com.ecommerce.firstspring.repository.CategoryRepository;
import com.ecommerce.firstspring.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  ProductRepository productRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public ProductDTO addProduct(Long categoryId, Product product) {
    Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    product.setCategory(existingCategory);
    product.setImage("default.png");
    double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
    product.setSpecialPrice(specialPrice);
    Product savedProduct = productRepository.save(product);
    return modelMapper.map(savedProduct, ProductDTO.class);
  }

  @Override
  public ProductResponse getAllProducts() {
    List<Product> products = productRepository.findAll();
    List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setData(productDTOS);
    return productResponse;
  }

  @Override
  public ProductResponse getProductsByCategory(Long categoryId) {
    Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    List<Product> products = productRepository.findByCategoryOrderByPriceAsc(existingCategory);
    List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setData(productDTOS);
    return productResponse;
  }

  @Override
  public ProductResponse searchProductsByKeyword(String keyword) {
    List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
    List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setData(productDTOS);
    return productResponse;
  }

  @Override
  public ProductDTO updateProduct(Long productId, Product product) {
    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    existingProduct.setProductName(product.getProductName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setQuantity(product.getQuantity());
    existingProduct.setDiscount(product.getDiscount());
    existingProduct.setPrice(product.getPrice());
    existingProduct.setCategory(product.getCategory());
    double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
    existingProduct.setSpecialPrice(specialPrice);

    Product updatedProduct = productRepository.save(existingProduct);
    return modelMapper.map(updatedProduct, ProductDTO.class);
  }
}
