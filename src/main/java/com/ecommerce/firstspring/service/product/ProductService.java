package com.ecommerce.firstspring.service.product;

import com.ecommerce.firstspring.model.Product;
import com.ecommerce.firstspring.payload.ProductDTO;
import com.ecommerce.firstspring.payload.ProductResponse;

public interface ProductService {
  ProductDTO addProduct(Long categoryId, Product product);

  ProductResponse getAllProducts();

  ProductResponse getProductsByCategory(Long categoryId);

  ProductResponse searchProductsByKeyword(String keyword);

  ProductDTO updateProduct(Long productId, Product product);
}
